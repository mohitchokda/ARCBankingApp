import React, { useState } from "react";
import axios, { HttpStatusCode } from "axios";
import { Outlet, Link, useNavigate } from "react-router-dom";
import {jwtDecode} from "jwt-decode";
import { setAuthToken } from "./js/setAuthToken";

const Login = () => {
  
  const data = {
    username: "",
    password: "",
  };

  const [user, setUser] = useState(data);
  const [message, setMessage] = useState("");
  const [error, setErrors] = useState("");

  const navigate = useNavigate("/dashboard");

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
    axios.post("http://localhost:8080/user/login", user)
                    .then( (res) => {
                      //console.log("RESPONSE : "+res.data)
                      //setMessage(res.data);
                      localStorage.setItem("token",res.data);
                      setAuthToken(res.data);
                      navigate("/dashboard");
                    })
                    .catch( (error)=>{ 
                      console.log("ERROR From API : "+error.response.data)
                        setErrors(error.response.data);
                        //console.log(error.toJSON());
                    });

  };
  return (
    <>
      <div className="title" style={{ marginTop: "2rem" }}>
        <h2>Login</h2>
        <div className="title-underline"></div>
      </div>
      {message && (
        <p style={{ color: "green", textAlign: "center" }}>{message}</p>
      )}
      <ul>
      {error && (
        <li
          style={{
            color: "red",
            textAlign: "center",
          }}
        > {error} </li>
      )}
      </ul>
      <section>
        <form className="form" onSubmit={handleSubmit}>
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
            Login
            </button>
          </div>
        </form>
      </section>
    </>
  );
};
export default Login;