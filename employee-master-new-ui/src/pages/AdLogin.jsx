import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useDispatch } from 'react-redux';
import { login } from '../authSlice';
import axios from'axios'

export default function AdLogin() {

  const [ email, setEmail ] = useState('');
  const [ password, setPassword ] = useState('');
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [ show, setShow ] = useState(false);

  const handleSubmit = async (e) => {
        e.preventDefault();
       try{
        const response = await axios.post("http://localhost:8080/ems/controller/login", {email, password});
        if(response.data.status === 'success'){
          const admin = response.data.admin;
            dispatch(login({ user: { email: email, id: admin.id ,role: 'admin'} }));
            navigate("/adminHome");
        }
       }catch(error){
          console.log(error);
          if (error.response && error.response.data && error.response.data.message) {
            // setMessage(error.response.data.message);
            document.getElementById("message").innerHTML = error.response.data.message;
          } else {
            // setMessage("Unexpected Error has occurred!");
            document.getElementById("message").innerHTML = "Unexpected Error has occurred!";
          }
       }
  }

  return (
    <div className='login-container'>
      <form className='login-form' onSubmit={handleSubmit}>
          <h2>Admin Login</h2>
          <p id='message'></p>
          <input
              type="email"
              name="email"
              placeholder="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
          />
          <input
              type={show ? "text" : "password"}
              name="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
          />
          {/* <Link onClick={() => {show ? setShow(false) : setShow(true)}}>{show ? 'hide' : 'show'}</Link>  */}
          <Link className='forgot-password-link' to={"/forgotPassword"}>
              Forgot Password ?
          </Link>
          <input type="submit" value="Login" className='login-submit' />
          <p className="new-user-text">
              New User? <Link className='register-link' to={"/adregister"}>Register</Link>
          </p>
      </form>
   </div>

  )
}
