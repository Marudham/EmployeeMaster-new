import React, { useState, useEffect } from 'react'
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom'
import { selectUser } from '../authSlice';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

export default function ViewEmpReg({ commonMessage, setCommonMessage }) {

  const [ employees, setEmployees ] = useState([]);
  const [ message, setMessage ] = useState('');
  const user = useSelector(selectUser);
  const navigate = useNavigate();

  useEffect( () => {
    fetchEmp();
  }, [] );

  async function fetchEmp(){
    try{
      if(user === null){
        navigate('/login');
        return;
      }
      const response = await axios.get("http://localhost:8080/ems/controller/fetchRequestEmployees");
      if(response.data.status === 'success'){
        setEmployees(response.data.employeeList);
      }else{
        setMessage("Cannot retrieve employee details...")
      }
    }catch(error){
      handleCommonError(error);
    }
  }

  const handleApprove = async (id) => {
    try {
      const response = await axios.get("http://localhost:8080/ems/controller/approveEmployee", {
        params: {
          id: id,
          adminId: user.id
        },
      })
      if(response.data.status === 'success'){
        setMessage("Employee Approved successfully")
      }else{
        setMessage("Problem in Updating the employeee");
      }
    } catch (error) {
      console.log(error)
      if (error.response && error.response.data && error.response.data.message) {
        setMessage(error.response.data.message);
      } else {
        setMessage("Unexpected Error has occurred!");
      }
    }finally{
        fetchEmp();
    }
  }

  function handleCommonError(error) {
    console.log(error);
    if (error.response && error.response.data && error.response.data.message) {
      setMessage(error.response.data.message);
    } else {
      setMessage("Unexpected Error has occurred!");
    }
  }  

  return (
    <div className="eview-container">
      <div className="eview-row">
        <h1 className='eview-header'>Registered Employees List</h1>
      </div>
      {/* <Link className='eview-btn eview-btn-n' to={'/adminHome/addEmp'}>Add Employee</Link> */}
    
      {message && (
        <p id="message">
          {message}
          <button className="no-message" onClick={() => setMessage('')}>
            X
          </button>
        </p>
      )}
       {commonMessage && (
        <p id="message">
          {commonMessage}
          <button className="no-message" onClick={() => setCommonMessage('')}>
            X
          </button>
        </p>
      )}
        <table className="eview-table">
        <thead className="eview-thead">
          <tr className='eview-tr'>
            <th>Employee First Name</th>
            <th>Employee Second Name</th>
            <th>Employee Email</th>
            <th>All Details</th>
            <th>Actions</th>
          </tr>
        </thead>
        {employees.length === 0 ? (
        <p className='eview-act-nomsg'>No result found</p>
      ) :(
        <tbody className="eview-tbody">
          {employees.map((employee) => (
            <tr key={employee.id}>
              <td>{employee.firstName}</td>
              <td>{employee.secondName}</td>
              <td>{employee.email}</td>
              <td>
                <Link className="eview-link" to={`/adminHome/viewRegEmp/${employee.id}`}>
                  View All Details
                </Link>
              </td>
              <td>
                <button className="eview-btn-update" onClick={ () => handleApprove(employee.id)}>
                  Approve
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      )}
      </table>
    </div>
  )
}
