import React from 'react'
import { Link } from 'react-router-dom'

export default function EmpNav() {
  return (
    <div>
        <Link className='sanav' to={"/empHome/viewDetails"}>View Details</Link>
        <Link className='sanav' to={"/empHome/viewEmpReq"}>View Requests</Link>
        {/* <Link className='sanav' to={"/empHome/raiseReq"}>Raise Request</Link> */}
    </div>
  )
}
