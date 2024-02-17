import React, { useState, useEffect } from 'react'
import { useSelector } from 'react-redux';
import { selectUser } from '../authSlice';
import axios from 'axios';
import { useFormik } from 'formik';
import * as Yup from 'yup'
import { Link } from 'react-router-dom';
import RenderInputField from './RenderInputField';

export default function EmpDetails() {

    const user = useSelector(selectUser);
    const [ employee, setEmployee ] = useState('')
    const [ admin, setAdmin ] = useState('')
    const [ message, setMessage ] = useState('')
    const [ isToken, setIsToken ] = useState(false);
    const [ value, setValue ] = useState('')
    const [ field, setField ] = useState('')

    useEffect(() => {
        fetchEmployee(user.id);
    }, [user.id]);

    async function fetchEmployee(id) {
        try {
            const response = await axios.get("http://localhost:8080/ems/controller/fetchEmployee", {
                params: {
                    id: id
                },
            });
            if (response.data.status === 'success') {
                setEmployee(response.data.employee);
                setAdmin(response.data.employee.approvedByAdmin)
            } else {
                setMessage("Problem occurred while fetching Employee details");
            }
        } catch (error) {
            console.log(error);
            if (error.response && error.response.data && error.response.data.message) {
                setMessage(error.response.data.message);
            } else {
                setMessage("Unexpected Error has occurred!");
            }
        }
    }

    const isNotRepeatedCharacter = (value) => {
        return !/^([a-zA-Z])\1+$/.test(value);
      };
    
     const formik = useFormik({
        initialValues: {
          firstName: '',
          secondName: '',
          email: '',
          password: '',
          phoneNo: '',
          dateOfBirth: '',
          address: '',
          gender: '',
          education: '',
          percentage10: '',
          percentage12: '',
          percentageDeg: '',
          department: '',
          joinDate: '',
          position: '',
          salary: '',
          supervisor: '',
          project: '',
          status: '',
        },
        validationSchema: Yup.object({
            firstName: Yup.string().matches(/^[a-zA-Z ]{2,}$/, 'Invalid first name')
            .test('not-repeated-character', 'Name cannot consist of a single repeated character', isNotRepeatedCharacter)
            .required('First Name is required'),
            secondName: Yup.string().matches(/^[a-zA-Z ]{1,}$/, 'Invalid second name')
            .test('not-repeated-character', 'Name cannot consist of a single repeated character', isNotRepeatedCharacter)
            .required('Second Name is required'),
            phoneNo: Yup.string()
              .matches(/^[6789]\d{9}$/, 'Invalid phone number')
              .required('Phone Number is required'),
            dateOfBirth: Yup.date()
              .max(new Date(), 'Date of Birth cannot be in the future')
              .required('Date of Birth is required')
              .test('age-validation', 'Must be at least 18 years old', function (value) {
                const today = new Date();
                const birthDate = new Date(value);
                const age = today.getFullYear() - birthDate.getFullYear();
          
                return age >= 18;
              }),
            address: Yup.string().required('Address is required'),
            gender: Yup.string().required('Gender is required'),
            education: Yup.string().required('Education is required'),
            percentage10: Yup.number()
              .required('Percentage of 10th std is required')
              .max(100, 'Percentage cannot be more than 100'),
            percentage12: Yup.number()
              .required('Percentage of 12th std is required')
              .max(100, 'Percentage cannot be more than 100'),
            percentageDeg: Yup.number()
              .required('Percentage of College is required')
              .max(100, 'Percentage cannot be more than 100'),
            department: Yup.string().required('Department is required'),
            joinDate: Yup.date()
              .max(new Date(), 'Join Date cannot be in the future')
              .required('Join Date is required'),
            position: Yup.string().required('Position is required'),
            salary: Yup.string().matches(/^[0-9]/, 'Invalid Salary').required('Salary is required'),
            supervisor: Yup.string().required('Supervisor is required'),
            project: Yup.string().required('Project is required'),
            status: Yup.string().required('Status is required'),
          }), 
      });

      const handleSubmit = async (e) => {
        e.preventDefault();
        try{
            const response = await axios.post("http://localhost:8080/ems/controller/employeeRequest", {
                field: field,
                value: formik.values[field]
            }, {
                params: {
                    id: user.id
                }
            })
            if(response.data.status === 'success'){
                setMessage("Request Raised Successfully")
                setField('')
            }else{
                setMessage("Problem occured while Raising Request")
            }
        }catch(error){
            console.log(error);
            if (error.response && error.response.data && error.response.data.message) {
            setMessage(error.response.data.message);
            } else {
            setMessage("Unexpected Error has occurred!");
            }
        }
      }

  return (
    <div id="employee-details" className="ve-employee-details">
        <div className="ve-details-row">
            <div className="ve-details-label">ID:</div>
            <div className="ve-details-value">{employee.id}</div>
        </div>
        <div className="ve-details-row">
            <div className="ve-details-label">First Name:</div>
            <div className="ve-details-value">{employee.firstName}</div>
        </div>
        <div className="ve-details-row">
            <div className="ve-details-label">Last Name:</div>
            <div className="ve-details-value">{employee.secondName}</div>
        </div>
        <div className="ve-details-row">
            <div className="ve-details-label">Email:</div>
            <div className="ve-details-value">{employee.email}</div>
        </div>
        <div className="ve-details-row">
            <div className="ve-details-label">Phone No:</div>
            <div className="ve-details-value">{employee.phoneNo}</div>
        </div>
        <div className="ve-details-row">
            <div className="ve-details-label">Date of Birth:</div>
            <div className="ve-details-value">{employee.dateOfBirth}</div>
        </div>
        <div className="ve-details-row">
            <div className="ve-details-label">Address:</div>
            <div className="ve-details-value">{employee.address}</div>
        </div>
        <div className="ve-details-row">
            <div className="ve-details-label">Gender:</div>
            <div className="ve-details-value">{employee.gender}</div>
        </div>
        <div className="ve-details-row">
            <div className="ve-details-label">Education:</div>
            <div className="ve-details-value">{employee.education}</div>
        </div>
        <div className="ve-details-row">
            <div className="ve-details-label">Percentage 10th std:</div>
            <div className="ve-details-value">{employee.percentage10}</div>
        </div>
        <div className="ve-details-row">
            <div className="ve-details-label">Percentage 12th std:</div>
            <div className="ve-details-value">{employee.percentage12}</div>
        </div>
        <div className="ve-details-row">
            <div className="ve-details-label">Percentage Degree:</div>
            <div className="ve-details-value">{employee.percentageDeg}</div>
        </div>
        <div className="ve-details-row">
            <div className="ve-details-label">Join Date:</div>
            <div className="ve-details-value">{employee.joinDate}</div>
        </div>
        <div className="ve-details-row">
            <div className="ve-details-label">Department:</div>
            <div className="ve-details-value">{employee.department}</div>
        </div>
        <div className="ve-details-row">
            <div className="ve-details-label">Position:</div>
            <div className="ve-details-value">{employee.position}</div>
        </div>
        <div className="ve-details-row">
            <div className="ve-details-label">Salary:</div>
            <div className="ve-details-value">{employee.salary}</div>
        </div>
        <div className="ve-details-row">
            <div className="ve-details-label">Supervisor:</div>
            <div className="ve-details-value">{employee.supervisor}</div>
        </div>
        <div className="ve-details-row">
            <div className="ve-details-label">Project:</div>
            <div className="ve-details-value">{employee.project}</div>
        </div>
        <div className="ve-details-row">
            <div className="ve-details-label">Status:</div>
            <div className="ve-details-value">{employee.status}</div>
        </div>
        {/* <div className='empDetails-raiseReq-container'>
            <Link className='empDetails-raiseReq-link' to={'/EmpHome/raiseReq'}>Raise Request</Link>
        </div> */}
        <button onClick={()=> setIsToken(true)} style={{marginLeft:'60px'}} className='ve-tokens-btn'>Raise Request</button>
        <div className='filter-form'>
        { isToken && (<div style={{padding:'10px', marginLeft:'50px'}}>
            <form className='ve-token-form' onSubmit={handleSubmit}>
            <div className='form-row'>
            <div className='form-group col-md-3'>
            <label>Select Field</label>
            <select className='form-control' name='field' value={field} onChange={(e)=> setField(e.target.value)} required>
                <option className='ve-token-option' value="">-- select --</option>
                <option className='ve-token-option' value="firstName">First Name</option>
                <option className='ve-token-option' value="secondName">Second Name</option>
                <option className='ve-token-option' value="phoneNo">Phone No</option>
                <option className='ve-token-option' value="dateOfBirth">Date of Birth</option>
                <option className='ve-token-option' value="address">Address</option>
                <option className='ve-token-option' value="gender">Gender</option>
                <option className='ve-token-option' value="education">Education</option>
                <option className='ve-token-option' value="percentage10">Percentage 10</option>
                <option className='ve-token-option' value="percentage12">Percentage 12</option>
                <option className='ve-token-option' value="percentageDeg">Percentage Degree</option>
                <option className='ve-token-option' value="department">Department</option>
                <option className='ve-token-option' value="joinDate">Join Date</option>
                <option className='ve-token-option' value="position">Position</option>
                <option className='ve-token-option' value="salary">Salary</option>
                <option className='ve-token-option' value="supervisor">Supervisor</option>
                <option className='ve-token-option' value="project">Project</option>
                <option className='ve-token-option' value="status">Status</option>
            </select>
            </div>
            {field && (
                <RenderInputField field={field} formik={formik} />
            )}
            <div className='form-group col-md-2'>
            <input type='submit' className='btn-ev btn-primary' value="Submit"></input>
            </div>
            </div>
            </form>
            <button className='btn-ev btn-danger' onClick={()=> setIsToken(false)}>Close</button>
        </div>
            )}
        </div>

        {message && (
            <p id="message">
                {message}
                <button className="no-message" onClick={() => setMessage('')}>
                    X
                </button>
            </p>
         )}
        <div className="ve-admin-info">
            <h2>Admin Information</h2>
            <div className="ve-info-row">
                <div className="ve-info-label">Username:</div>
                <div className="ve-info-value">{admin.username}</div>
            </div>
            <div className="ve-info-row">
                <div className="ve-info-label">Email:</div>
                <div className="ve-info-value">{admin.email}</div>
            </div>
        </div>
    </div>
  )
}
