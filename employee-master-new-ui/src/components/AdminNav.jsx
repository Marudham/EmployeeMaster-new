import React from 'react'
import { Link } from 'react-router-dom'

export default function AdminNav() {
  return (
    <div>
        <Link className='sanav' to={"/adminHome/viewEmp"}>Employees</Link>
        <Link className='sanav' to={"/adminHome/viewEmpReg"}>Registered Employees</Link>
        <Link className='sanav' to={"/adminHome/viewAct"}>Activities</Link>
        <Link className='sanav' to={"/adminHome/empReq"}>Employee Requestes</Link>
    </div>
  )
}
