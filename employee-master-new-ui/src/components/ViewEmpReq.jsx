import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import { selectUser } from '../authSlice'
import axios from 'axios'

export default function ViewEmpReq() {

    const user = useSelector(selectUser)
    const [ requests, setRequests ] = useState('')
    const [ message, setMessage ] = useState('')

    useEffect( () => {
        async function fetchRequests(id){
            try {
                const response = await axios.get("http://localhost:8080/ems/controller/fetchRequestByEmp", {
                params: {
                    id: id
                }
            })
            if(response.data.status === 'success'){
                setRequests(response.data.employeeRequests);
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

        fetchRequests(user.id);
    },[user.id])

  return (
    <div id="requests" className='ve-tokens-list-container'>
        <h2 className='ve-tokens-list-header'>Request List</h2>
        <div className='ve-tokens-list-req'>
            <div className='ve-tokens-list-titles'>
                <ul>
                    <li>SI.NO</li>
                    <li>Field</li>
                    <li>Value</li>
                    <li>Is Approved</li>
                    <li>Is Executed</li>
                </ul>
            </div>
        { requests.length === 0 ? (
            <p className='eview-act-nomsg'>No Result Found</p>
        ) : (
            <div className='ve-tokens-list-req'>
                {requests.map( (request, index) => (
                <ul key={index}>
                    <li>{index + 1}</li>
                    <li>{request.field}</li>
                    <li>{request.value}</li>
                    <li>{request.approved ? 'Approved' : 'Not Approved'}</li>
                    <li>{request.executed ? 'Executed' : 'Not Executed'}</li>
                </ul>
            ))}
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
    </div>
  )
}
