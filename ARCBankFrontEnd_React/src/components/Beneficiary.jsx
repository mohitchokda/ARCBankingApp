// BeneficiaryDetailsPage.js
import React, { useEffect, useState } from 'react';
import axios from "axios";
// Import CSS file
import './css/BeneficiaryCss.css'
import { setAuthToken } from './js/setAuthToken';

const BeneficiaryDetailsPage = (props) => {
  
  const [beneficiaries, setBeneficiaries] = useState([]);

  useEffect(()=>{

    let token = localStorage.getItem("token");

    if(token){
      setAuthToken(token);
      //get beneficiary details
      axios.get(`http://localhost:8080/user/${props.username}/beneficiary`)
      .then(res => {
        //console.log(res.data);
        setBeneficiaries(res.data);
        //console.log(res.data);
        //setToken(true);
    })
    .catch( error => {
        console.log(error);
        //setToken(false);
    });
}else{
    console.log("TOKEN :",token);
    //setToken(false);
}

  },[]);

  const [selectedBeneficiary, setSelectedBeneficiary] = useState(null);
  const [paymentType, setPaymentType] = useState('instant');
  const [amount,setAmount] = useState(100);

  const handleBeneficiarySelect = (beneficiary) => {
    setSelectedBeneficiary(beneficiary);
  };

  const handlePaymentTypeChange = (e) => {
    setPaymentType(e.target.value);
  };

  const handleCompletePayment = () => {
    if (selectedBeneficiary && amount>=100 && amount<=100000) {
      alert(`Payment completed to ${selectedBeneficiary.username} (${selectedBeneficiary.accounts}) via ${paymentType} value : ${amount}`);
      // Actual payment processing logic should go here (API call, etc.)
    } else if(selectedBeneficiary){
      alert('Please enter valid amount');
    }else{
      alert('Please select beneficiary');
    }
  };

  return (
    <div className="beneficiary-details-container">
      <form>
      <h2>Beneficiary Details</h2>

      <div className="beneficiary-list">
        <h3 style={{marginBottom:20}}>Select Beneficiary:</h3>
        <div className="beneficiary-cards">
          {beneficiaries.map((beneficiary) => (
            <div
              key={beneficiary.customerId}
              className={`beneficiary-card ${selectedBeneficiary && selectedBeneficiary.customerId === beneficiary.customerId ? 'selected' : ''}`}
              onClick={() => handleBeneficiarySelect(beneficiary)}
            >
              <div className="beneficiary-info">
                <h4>{beneficiary.username}</h4>
                <p>Accounts: {beneficiary.accounts.length}</p>
              </div>
            </div>
          ))}
        </div>
      </div>

      <div className="payment-type">
        <h4 style={{marginBottom:20}}>Amount : </h4>
        <input type='number' min={'100'} max={'100000'} maxLength={'6'} inputmode='numeric'
        onChange={(e)=>setAmount(e.target.value)} required></input>
      </div>

      <div className="payment-type">
        <h3 style={{marginBottom:20}}>Select Payment Type:</h3>
        <select value={paymentType} onChange={handlePaymentTypeChange}>
          <option value="instant">Instant Transfer</option>
          <option value="scheduled">Payment Gateway</option>
        </select>
      </div>

      <button type='submit' className={`complete-payment-btn ${selectedBeneficiary ? '' : 'disabled'}`} onClick={handleCompletePayment} disabled={!selectedBeneficiary}>
        Complete Payment
      </button>
      </form>
    </div>
  );
};

export default BeneficiaryDetailsPage;
