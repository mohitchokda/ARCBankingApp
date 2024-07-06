import React from 'react';
import { Outlet, Link } from "react-router-dom";

const Home = () => { 
  return (
    <div className="App">
      <header className="App-header">
      <h1>Welcome to ARC Bank</h1>
      
      <nav>
        <ul>
          <li><Link to="/">Home</Link></li>
          <li><Link to="/register">Register</Link></li>
          <li><Link to="/login">Login</Link></li>
        </ul>
      </nav>

      <Outlet/>
      </header>
    </div>
  );
}

export default Home