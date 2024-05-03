import React from "react";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import HospitalSandBox from "../HospitalSandBox";


export default function IndexRouter(){
    return(
        <Router>
            <Switch>
                <Route path="/" component={HospitalSandBox}/>
            </Switch>
        </Router>
    )
}