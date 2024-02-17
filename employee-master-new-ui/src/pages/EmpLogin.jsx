import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useDispatch } from 'react-redux';
import { login } from '../authSlice';
import axios from'axios'

export default function EmpLogin() {

  const [ email, setEmail ] = useState('');
  const [ password, setPassword ] = useState('');
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const handleSubmit = async (e) => {
        e.preventDefault();
       try{
        const response = await axios.post("http://localhost:8080/ems/controller/empLogin", {email, password});
        if(response.data.status === 'success'){
          const employee = response.data.employee;
          dispatch(login({ user: { email: email, id: employee.id ,role: 'employee'} }));
          navigate("/empHome");
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
          <h2>Employee Login</h2>
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
              type="password"
              name="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
          />
          <Link className='forgot-password-link' to={"/forgotPassword"}>
              Forgot Password ?
          </Link>
          <input type="submit" value="Login" className='login-submit' />
          <p className="new-user-text">
              New User? <Link className='register-link' to={"/empregister"}>Register</Link>
          </p>
      </form>
   </div>

  )
}
