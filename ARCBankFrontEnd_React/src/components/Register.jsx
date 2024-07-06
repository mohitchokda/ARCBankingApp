import React, { useState } from "react";
import axios, { HttpStatusCode } from "axios";
import { Outlet, Link } from "react-router-dom";
import Home from "./Home";
import Login from "./Login";

const Register = () => {
  
  const data = {
    firstname: "",
    lastname: "",
    role: "",
    mobileNum: "",
    email: "",
    username: "",
    password: "",
  };

  const [user, setUser] = useState(data);
  const [message, setMessage] = useState("");
  const [errors, setErrors] = useState([]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUser({
      ...user,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");
    setErrors("");
    /* ----------AXIOS---------------------
    Looking at the response object 

When we send a request to server and it is successful, our then() callback will receive a response object which can have the following properties: 

    data: It is the payload returned from the server. By default, Axios expects JSON and will parse this back into a JavaScript object for you. 
    status: It is basically the HTTP code returned from the server. 
    statusText: it refers to the HTTP status message returned by the server. 
    headers: It contains all the headers sent back by the server. 
    config: It has the original request configuration. 
    request: It is the actual XMLHttpRequest object (when it is running in a browser)

Looking at the error object 

And if there’s a problem with the request, the promise will be rejected with an error object containing at least one or few of the following properties: 

    message: the error message text. 
    response: the response object (if received) as described in the previous section. 
    request: the actual XMLHttpRequest object (when running in a browser). 
    config: the original request configuration. 
    */
    axios.post("http://localhost:8080/user/register", user)
                    .then( (res) => {console.log("RESPONSE : "+res.data)
                      setMessage(res.data);
                    })
                    .catch( (error)=>{ console.log("ERROR From API : "+error.response.data.errorMsg)
                        setErrors(error.response.data.errorMsg);
                        //console.log(error.toJSON());
                    });

  };
  return (
    <>
      <div className="title" style={{ marginTop: "2rem" }}>
        <Link to="/register"><h2>Register</h2></Link> 
        <Link to="/login"><h2>Login</h2></Link>
        <div className="title-underline"></div>
      </div>
      {message && (
        <p style={{ color: "green", textAlign: "center" }}>{message}</p>
      )}
      <ul>
      {errors && (
        errors.map( error => <li
          style={{
            color: "red",
            textAlign: "center",
          }}
        > {error} </li>)
      )}
      </ul>
      <section>
        <form className="form" onSubmit={handleSubmit}>
          <label className="form-label">
            First name:
            <input
              type="text"
              className="form-input"
              name="firstname"
              value={user.firstname}
              onChange={handleChange}
            />
          </label>
          <label className="form-label">
            Last name:
            <input
              type="text"
              className="form-input"
              name="lastname"
              value={user.lastname}
              onChange={handleChange}
            />
          </label>
          <label className="form-label">
            Role:
            <input
              type="text"
              className="form-input"
              name="role"
              value={user.role}
              onChange={handleChange}
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
          <label className="form-label">
            Username:
            <input
              type="text"
              className="form-input"
              name="username"
              value={user.username}
              onChange={handleChange}
            />
          </label>
          <label className="form-label">
            Password:
            <input
              type="password"
              className="form-input"
              name="password"
              value={user.password}
              onChange={handleChange}
            />
          </label>
          <div className="btn-block">
            <button className="btn" style={{ marginTop: "2rem" }} type="submit">
              Submit
            </button>
          </div>
        </form>
      </section>
    </>
  );
};
export default Register;