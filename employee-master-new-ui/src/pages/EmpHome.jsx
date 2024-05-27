import React, { useState } from 'react';
import { Link, Routes, Route } from 'react-router-dom';
import EmpNav from '../components/EmpNav'
import EmpDetails from '../components/EmpDetails';
import ViewEmpReq  from '../components/ViewEmpReq'

export default function EmpHome() {

    const [ commonMessage, setCommonMessage] = useState('');

    return (
        <div className='ve-container'>
            <nav className='sa-nav'>
                <h1 className='sa-nav-header'>EmployeeMaster</h1>
                <ul className='sa-nav-ul-ve'>
                <li className='sa-nav-li'>
                    <Link className='sa-nav-link' to={'/empHome'}>Employee Details</Link>
                </li>
                <li>
                    <Link style={{marginLeft:'230px'}} className='sa-nav-link' to={'/'}>Log Out</Link>
                </li>
                </ul>
            </nav>
            {commonMessage && (
                <p id="message">
                    {commonMessage}
                    <button className="no-message" onClick={() => setCommonMessage('')}>
                        X
                    </button>
                </p>
            )}
            <Routes>
              <Route path='/' element={<EmpNav />} />
              <Route path='/viewDetails' element={<EmpDetails />} />
              <Route path='/viewEmpReq' element={<ViewEmpReq commonMessage={commonMessage} setCommonMessage={setCommonMessage} />} />
              {/* <Route path='/raiseReq' element={<RaiseReq commonMessage={commonMessage} setCommonMessage={setCommonMessage} />} /> */}
            </Routes>
           
        </div>
    );
}
