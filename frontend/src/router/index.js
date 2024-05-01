import React, { Component } from 'react'
import { BrowserRouter as Router, Route, Switch} from "react-router-dom";
import App from "../pages/App"
import SignUp from "../pages/SignIn/SignUp"
import SignInSide from "../pages/SignIn/SignInSide"
import AdminSignIn from "../pages/SignIn/AdminSignIn"

export default class index extends Component{
    render(){
        return(
            <Router>
                <Switch>
                    <Route exact path="/" component={App} />
                    <Route path="/signIn" component={ SignInSide } />
                    <Route path="/signUp" component={ SignUp } />
                    <Route path="/adminSignIn" component={ AdminSignIn } />
                </Switch>
            </Router>
        )
    }
}