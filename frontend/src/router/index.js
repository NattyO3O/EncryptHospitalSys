import React, { Component } from 'react'
import { HashRouter as Router, Route, Switch} from "react-router-dom";
import App from "../pages/App"
import SignUp from "../pages/SignIn/SignUp"
import SignInSide from "../pages/SignIn/SignInSide"
import AdminSignIn from "../pages/SignIn/AdminSignIn"
import PopMessage from "../components/popMessage";
import HospitalSandBox from "../pages/Doctor/HospitalSandBox";
import AdminSandbox from "../pages/Admin/AdminSandBox";
import patientSandBox from "../pages/Patient/patientSandBox";

export default class index extends Component{
    render(){
        return(
            <Router>
                <PopMessage/>
                <Switch>
                    <Route exact path="/" component={App} />
                    <Route path="/signIn" component={ SignInSide } />
                    <Route path="/signUp" component={ SignUp } />
                    <Route path="/adminSignIn" component={ AdminSignIn } />
                    <Route path="/docSandBox" component={HospitalSandBox} />
                    <Route path="/adminSandBox" component={AdminSandbox} />
                    <Route path="/patSandBox" component={patientSandBox}/>
                </Switch>
            </Router>
        )
    }
}