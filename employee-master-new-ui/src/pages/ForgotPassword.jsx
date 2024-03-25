import React, { useState } from 'react'
import { Link } from 'react-router-dom'
import axios from'axios'

export default function ForgotPassword() {

  const [ email, setEmail ] = useState('');
  const [ loading, setLoading ] = useState(false)

  const handleSubmit = async (e) => {
    setLoading(true)
        e.preventDefault();
       try{
        const response = await axios.post("http://localhost:8080/ems/controller/forgotPassword",{ email, role: 'admin' });
        if(response.data.status === 'success'){
            document.getElementById("message").innerHTML = "Reset Password Link send successfully";
        }
       }catch(error){
          console.log(error);
          if (error.response && error.response.data && error.response.data.message) {
            document.getElementById("message").innerHTML = error.response.data.message;
          } else {
            document.getElementById("message").innerHTML = "Unexpected Error has occurred!";
          }
       }finally{
        setLoading(false)
       }
  }

  return (
    <div className='login-container'>
      <form className='login-form' onSubmit={handleSubmit}>
          <h2>Forgot Password</h2>
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
                type="submit"
                disabled={loading}
                value={loading ? 'Sending...' : 'Send Link'}
                className='register-submit'
            />
            {loading && <div className="spinner"></div>}
          <p className="new-user-text">
              Back to <Link className='register-link' to={"/"}>Home page.</Link>
          </p>
      </form>
   </div>

  )
}
