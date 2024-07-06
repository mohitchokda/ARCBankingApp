import React, { useEffect, useState } from 'react'
import {Link,useLocation,useNavigate} from 'react-router-dom'
import Profile from './Profile';
import Accounts from './Accounts';
import LoanDetails from './LoanDetails'
import Transactions from './Transactions'
import Beneficiary from './Beneficiary'
import Header from './Header';
import {jwtDecode} from "jwt-decode";
import axios from 'axios';
import { setAuthToken } from './js/setAuthToken';

const Dashboard = () => {

    let token = "";

    const data = {
        customerId: "",
        firstname: "",
        lastname: "",
        mobileNum: "",
        email: "",
        role: "",
        username : "",
        accounts : []
    };

    const[user,setUser] = useState(data);
    const[tokenFound,setToken] = useState(false);

    useEffect( ()=> {
        
        token = localStorage.getItem("token");

        if(token){
        
        setAuthToken(token);
        
        const uname = jwtDecode(token).sub;

        //get user details
        axios.get(`http://localhost:8080/user/${uname}`, {
            // headers : {
            //     Authorization : `Bearer ${token}`
            // }
        })
        .then(res => {
            //console.log(res.data);
            user.customerId = res.data.customerId;
            user.firstname = res.data.firstname;
            user.lastname = res.data.lastname;
            user.mobileNum = res.data.mobileNum;
            user.email = res.data.email;
            user.role = res.data.role;
            user.username = res.data.username;
            user.accounts = res.data.accounts;

            //console.log(user);
            setUser(user);
            setToken(true);
        })
        .catch( error => {
            //console.log(tokenFound);
            //navigate("/login");
            console.log(error);
            setToken(false);
        });
    }else{
        //console.log("TOKEN :",token);
        setToken(false);
    }
    },[]);

  //console.log(user);

  const [selectedComponent, setSelectedComponent] = useState('Accounts');
    
  const renderComponent = () => {
    switch (selectedComponent) {
        case 'Accounts':
            return <Accounts customerId = {user.customerId}/>;
        case 'Transactions':
            return <Transactions />;
        case 'LoanDetails':
            return <LoanDetails />;
        case 'BeneficiaryDetails':
            return <Beneficiary username = {user.username}/>;
        case 'Profile':
            return <Profile parentUser = {user}/>;    
        default:
            return <Accounts customerId = {user.customerId}/>;
        }
    };

  return (
    <>
    {!tokenFound && (
        <div className="container">Unable to access Dashboard, Please <Link to="/login">Login</Link></div>
    )}

    {tokenFound && user.role === "user" && (
        <div className="dashboard">
        <nav className="sidebar">
            <h2>Bank Dashboard</h2>
            <ul>
                <li><button onClick={() => setSelectedComponent('Accounts')}>Accounts</button></li>
                <li><button onClick={() => setSelectedComponent('Transactions')}>Transactions</button></li>
                <li><button onClick={() => setSelectedComponent('LoanDetails')}>Loan Details</button></li>
                <li><button onClick={() => setSelectedComponent('BeneficiaryDetails')}>Beneficiary Details</button></li>
            </ul>
        </nav>
        <div className="main-content">
            <Header name = {user.firstname} setSelectedComponent={setSelectedComponent}/>
            <div className="content">
                {renderComponent()}
            </div>
        </div>
        </div>
    )}
    
    {tokenFound  && user && user.role === "admin" && (
        <div className="dashboard">
        <nav className="sidebar">
            <h2>Bank Dashboard</h2>
            <ul>
                <li><button onClick={() => setSelectedComponent('LoanApprocals')}>Loan Approvals</button></li>
                <li><button onClick={() => setSelectedComponent('UserQueries')}>User Queries</button></li>
            </ul>
        </nav>
        <div className="main-content">
            <Header name = {user.firstname} setSelectedComponent={setSelectedComponent}/>
            <div className="content">
                {renderComponent()}
            </div>
        </div>
        </div>
    )}
    </>
    )
}

export default Dashboard