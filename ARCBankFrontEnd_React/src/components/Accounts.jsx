import React, { useEffect, useState } from 'react';
import axios from "axios";
// Import CSS file
import './css/AccountsCss.css'
import { setAuthToken } from './js/setAuthToken';

const Accounts = (props) => {

  //const account = { type: '', balance: 500 };

  let token = localStorage.getItem("token");
  
  const [message, setMessage] = useState("");
  const [errors, setErrors] = useState([]);

  const [accounts, setAccounts] = useState([]);

  const [showNewAccountForm, setShowNewAccountForm] = useState(false);
  const [newAccType, setNewAccountType] = useState('');
  const [initialBalance, setInitialBalance] = useState(0);

  const getAllAccounts = async()=>{

    //console.log("GET ALL :",token);
    setAuthToken(token);
    
    axios.get(`http://localhost:8080/${props.customerId}/accounts`,{
      headers : {
        Authorization : `Bearer ${token}`
      }
    })
    .then((res)=>{
        //console.log("RES : "+res.data.accounts);
        setAccounts(res.data.accounts);
        setMessage("");
    })
    .catch((error)=>{
        console.log("Error : "+error.response);
        setMessage("No Accounts found,Please request for Account Opening");
    });
  };

  useEffect(()=>{
    //Get Account details
    //console.log(props.customerId)
    getAllAccounts();
    //set accounts
    //setAccounts(props.accounts);  
  },[]);


  //Handler Methods

  const handleNewAccountSubmit = (e) => {
    e.preventDefault();
    // Logic to send new account request
    console.log("New Account Type:", newAccType);
    console.log("Initial Balance:", initialBalance);
       
    axios.post(`http://localhost:8080/${props.customerId}/accounts`,
      {type : newAccType,balance:initialBalance}
    )
    .then((res)=>{
        console.log("RES : ",res.data);
        //setMessage(res.data);
        //setAccounts(res.data.accounts);
        getAllAccounts();
        // Reset form fields
        setNewAccountType('');
        setInitialBalance(0);
        setShowNewAccountForm(false);
    })
    .catch((error)=>{
        console.log("Error : ",error.response);
        setMessage("Request Rejected");
    });
  }

  const handleViewTransactions = (accountId) => {
    // Logic to view transactions for the selected account
    console.log("View transactions for account:", accountId);
  }

  return (
    <div className="accounts-container">
      <button className="new-account-btn" onClick={() => setShowNewAccountForm(true)}>Add Account</button>
      <h2>Your Accounts</h2>

      {message && (
        <p style={{ color: "green", textAlign: "center" }}>{message}</p>
      )}
      <ul>
      {errors && (
        errors.map( error => 
        <li
          style={{
            color: "red",
            textAlign: "center",
          }}
        > {error} </li>)
      )}
      </ul>

      <div className="account-list">
        {accounts.map(account => (
          <div key={account.accountNum} className="account-card">
            <button className='accounts'>
            <h3>{account.type} Account</h3>
            </button>
            <p>Balance: ${account.balance.toFixed(2)}</p>
            <button className="accBtn" onClick={() => handleViewTransactions(account.id)}>View Transactions</button>
          </div>
        ))}
      </div>

      {showNewAccountForm && (
        <form className="new-account-form" onSubmit={handleNewAccountSubmit}>
          <h2>Request for New Account Opening</h2>
          <label htmlFor="account-type">Account Type:</label>
          <select id="account-type" value={newAccType} onChange={(e) => setNewAccountType(e.target.value)} required>
            <option value="">Select Account Type</option>
            <option value="Savings">SAVINGS</option>
            <option value="Checking">CHECKING</option>
            <option value="Salary">SALARY</option>
          </select>
          <label htmlFor="initial-balance">Initial Balance:</label>
          <input type="number" id="initial-balance" min={500} value={initialBalance} onChange={(e) => setInitialBalance(parseFloat(e.target.value))} required />
          <button className="accBtn" type="submit">Request</button>
          <button className="accBtn" type="submit" style={{backgroundColor : "red"}} onClick={(e)=> setShowNewAccountForm(false)}>Cancel</button>
        </form>
      )}

    </div>
  );
}

export default Accounts