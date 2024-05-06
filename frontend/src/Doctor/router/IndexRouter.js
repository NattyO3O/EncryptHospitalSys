import React from "react";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import HospitalSandBox from "../HospitalSandBox";
import patientSandBox from "../../Patient/patientSandBox"


export default function IndexRouter(){
    return(
        <Router>
            <Switch>
                <Route path="/" component={patientSandBox}/>
            </Switch>
        </Router>
    )
}