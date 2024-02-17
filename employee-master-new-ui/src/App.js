import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import AdLogin from './pages/AdLogin';
import AdRegister from './pages/AdRegister';
import AdminHome from './pages/AdminHome';
import SuperAdmin from './pages/SuperAdmin';
import SAHome from './components/SAHome';
import EmpLogin from './pages/EmpLogin';
import ForgotPassword from './pages/ForgotPassword'
import EmpForgotPassword from './pages/EmpForgotPassword'
import VerifyEmployee from './components/VerifyEmployee';
import EmpRegister from './pages/EmpRegister';
import EmpHome from './pages/EmpHome';
import './styles/App.css'
// import './styles/LoginStyle.css'
// import './styles/Register.css'
// import './styles/Home.css'

function App() {

  return (
    <Router>
      <div>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/adlogin" element={<AdLogin />} />
          <Route path="/adregister" element={<AdRegister />} />
          <Route path="/adminHome/*" element={<AdminHome />} />
          <Route path="/superAdmin" element={<SuperAdmin />} />
          <Route path="/empLogin" element={<EmpLogin />} />
          <Route path="/empHome/*" element={<EmpHome />} />
          <Route path="/empRegister" element={<EmpRegister />} />
          <Route path="/forgotPassword" element={<ForgotPassword />} /> 
          <Route path="/empForgotPassword" element={<EmpForgotPassword />} /> 
          <Route path="/SAHome/*" element={<SAHome />} ></Route>
          <Route path="/verifyEmployee/:token/:id" element={<VerifyEmployee />}></Route>
        </Routes>
      </div>
    </Router>
  );
}

export default App;
