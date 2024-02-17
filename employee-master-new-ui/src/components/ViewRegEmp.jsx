import React, { useState, useEffect } from 'react';
import { Link, useParams, useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { selectUser } from '../authSlice';
import axios from 'axios';

export default function ViewRegEmp({ commonMessage, setCommonMessage }) {

  const user = useSelector(selectUser);
  const { empId } = useParams();
  const [employee, setEmployee] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchEmployee = async () => {
      try {
        const response = await axios.get("http://localhost:8080/ems/controller/employeeDetails",
                {
                    params: {
                        id: empId
                },
                });
        setEmployee(response.data.employee);
      } catch (error) {
        console.log(error.response);
      }
    };

    fetchEmployee(); 
  }, []);

  const handleApprove = async (id) => {
    try {
      const response = await axios.get("http://localhost:8080/ems/controller/approveEmployee", {
        params: {
          id: id,
          adminId: user.id
        },
      })
      if(response.data.status === 'success'){
        setCommonMessage("Employee Approved successfully")
        navigate("/adminHome/viewEmpReg")
      }else{
        setCommonMessage("Problem in Updating the employeee");
        navigate("/adminHome/viewEmpReg")
      }
    } catch (error) {
      console.log(error)
      if (error.response && error.response.data && error.response.data.message) {
        setCommonMessage(error.response.data.message);
      } else {
        setCommonMessage("Unexpected Error has occurred!");
        navigate("/adminHome/viewEmpReg")
      }
    }
  }
  
  return (
    <div className="employee-details">
      <h2 className="employee-details-header">Employee Details</h2>
      {commonMessage && (
        <p id="message">
          {commonMessage}
          <button className="no-message" onClick={() => setCommonMessage('')}>
            X
          </button>
        </p>
      )}
      {employee && (
        <div className="employee-details-form">
          <label>ID:</label> <span>{employee.id}</span>
          <label>First Name:</label> <span>{employee.firstName}</span>
          <label>Last Name:</label> <span>{employee.secondName}</span>
          <label>Email:</label> <span>{employee.email}</span>
          <label>Phone Number:</label> <span>{employee.phoneNo}</span>
          <label>Date Of Birth:</label> <span>{employee.dateOfBirth}</span>
          <label>Address:</label> <span>{employee.address}</span>
          <label>Gender:</label> <span>{employee.gender}</span>
          <label>Education:</label> <span>{employee.education}</span>
          <label>Percentage 10th std:</label> <span>{employee.percentage10}</span>
          <label>Percentage 12th std:</label> <span>{employee.percentage12}</span>
          <label>Percentage Degree:</label> <span>{employee.percentageDeg}</span>
          <label>Join Date:</label> <span>{employee.joinDate}</span>
          <label>Department:</label> <span>{employee.department}</span>
          <label>Position:</label> <span>{employee.position}</span>
          <label>Salary:</label> <span>{employee.salary}</span>
          <label>Supervisor:</label> <span>{employee.supervisor}</span>
          <label>Project:</label> <span>{employee.project}</span>
          <label>Status:</label> <span>{employee.status}</span>
        </div>
      )}
      <div className="employee-details-button-container">
        <button onClick={ () => handleApprove(employee.id)} className="delete" style={{backgroundColor:'green'}}>Approve</button>
        <Link to={'/adminHome/viewEmpReg'} className="back">Go Back</Link>
      </div>
    </div>
  );
}
