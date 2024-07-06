import React, { useEffect } from 'react';
import { useState } from 'react';
import {useLocation} from 'react-router-dom'
import axios from "axios";
import { setAuthToken } from './js/setAuthToken';

const Profile = (props) => {

  const loc = useLocation();
  const token  = localStorage.getItem("token");

  const data = {
    customerId: props.parentUser.customerId,
    firstname: props.parentUser.firstname,
    lastname: props.parentUser.lastname,
    mobileNum: props.parentUser.mobileNum,
    email: props.parentUser.email,
  };
  
  //console.log("PROFILE : "+this.state);

  const [user, setUser] = useState(data);
  const [message, setMessage] = useState("");
  const [errors, setErrors] = useState([]);

  // useEffect(() =>{
  //   user.customerId = props.customerId;
  //   user.firstname = props.firstname;
  //   user.lastname = props.lastname;
  //   user.mobileNum = props.mobileNum;
  //   user.email = props.email;
  // });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUser({
      ...user,
      [name]: value,
    });
  };

  const handleUpdate = async (e) => {
    e.preventDefault();
    setMessage("");
    setErrors("");

    setAuthToken(token);
    
    axios.put("http://localhost:8080/user/update", user)
                    .then( (res) => {
                      console.log("RESPONSE : "+res.data);
                      //Updating PROPS
                      user.mobileNum = res.data.mobileNum;
                      user.email = res.data.email;
                      setUser(user);
                      //Update props (Parent value as well to sync)
                      props.parentUser.mobileNum = res.data.mobileNum;
                      props.parentUser.email = res.data.email;
                      //console.log(user);
                      console.log(props.parentUser);
                      //console.log("Updated User"+loc.state);
                      setMessage("User Details updated successfully");
                    })
                    .catch( (error)=>{ 
                        console.log("ERROR From API : ",error)
                        //setErrors(error.response.data.errorMsg);
                        //console.log(error.toJSON());
                    });

  };

  return (
  <>
    <div className="title" style={{ marginTop: "2rem" }}>
        <h2>Profile Details</h2>
        <div className="title-underline"></div>
      </div>
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
      <section>
        <form name="form" className="form" onSubmit={handleUpdate}>  
          <label className="form-label">
            First name:
            <input
              type="text"
              className="form-input"
              name="firstname"
              value={user.firstname}
              disabled
            />
          </label>
          <label className="form-label">
            Last name:
            <input
              type="text"
              className="form-input"
              name="lastname"
              value={user.lastname}
              disabled
            />
          </label>
          <label className="form-label">
            Customer ID:
            <input
              type="text"
              className="form-input"
              name="customerId"
              value={user.customerId}
              disabled
            />
          </label>
          <label className="form-label">
            Mobile:
            <input
              type="number"
              className="form-input"
              name="mobileNum"
              value={user.mobileNum}
              onChange={handleChange}
            />
          </label>
          <label className="form-label">
            Email:
            <input
              type="email"
              className="form-input"
              name="email"
              value={user.email}
              onChange={handleChange}
            />
          </label>
          <div className="btn-block">
            <button className="btn" type="submit">
              Update
            </button>
            {/* <button className="btn" onClick={handleCancel} >
              Cancel
            </button> */}
          </div>
        </form>
      </section>
  </>
)};

export default Profile;
