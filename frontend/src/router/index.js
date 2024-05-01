import React, { Component } from 'react'
import { BrowserRouter as Router, Route, Routes} from "react-router-dom";
import App from "../pages/App"
import SignUp from "../pages/SignIn/SignUp"
import SignInSide from "../pages/SignIn/SignInSide"
import AdminSignIn from "../pages/SignIn/AdminSignIn"

import HeaderNav from "../components/HeaderNav"

export default class index extends Component{
    render(){
        return(
            <Router>
                <HeaderNav />
                <Routes>
                    <Route path="/" element={<App />} />
                    <Route path="/signIn" element={ <SignInSide /> } />
                    <Route path="/signUp" element={ <SignUp /> } />
                    <Route path="/adminSignIn" element={ <AdminSignIn /> } />
                </Routes>
            </Router>
        )
    }
}