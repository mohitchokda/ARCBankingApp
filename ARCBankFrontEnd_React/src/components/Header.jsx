import React from 'react';
import { useNavigate } from 'react-router-dom';

const Header = ({name,setSelectedComponent}) => {

    const navigate = useNavigate();

    const logout = (e)=>{
        e.preventDefault();
        //loc.state = null;
        navigate("/");
    }  

  return (  
    <header className="header">
    <h1>Welcome, {name}</h1>
    <div className="header-right">
      <button className="profile-button" onClick={() => setSelectedComponent('Profile')}>Profile</button>
      <button className="logout-button" onClick={logout}>Log Out</button>
    </div>
  </header>
  );
};

export default Header;
