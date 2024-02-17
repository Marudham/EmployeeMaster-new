import React, { useState } from 'react'
import { Link } from 'react-router-dom';

export default function Home() {

  const [ showAdmin, setShowAdmin ] = useState(false)
  const [ showEmp, setShowEmp ] = useState(false)

  const handleClosePopup = () => {
    setShowAdmin(false);
    setShowEmp(false)
  };

  return (
    <div className='home-container'>
      <h3 className='home-title'>EmployeeMaster</h3>
      <div className='home-links'>
          <Link className='home-link home-link-admin' onClick={ () => setShowAdmin(true)}>Admin</Link>
          {showAdmin && (
          <div className='popup-background' onClick={handleClosePopup}>
            <div className='popup-admin'>
              <Link className='home-link' to={"/adlogin"}>Login</Link>
              <Link className='home-link' to={"/adregister"}>Register</Link>
            </div>
          </div>
        )}
          <Link className='home-link' onClick={ () => setShowEmp(true)}>Employee</Link>
          {showEmp && (
          <div className='popup-background' onClick={handleClosePopup}>
            <div className='popup-admin'>
              <Link className='home-link' to={"/emplogin"}>Login</Link>
              <Link className='home-link' to={"/empregister"}>Register</Link>
            </div>
          </div>
        )}
      </div>
    </div>
  )
}
