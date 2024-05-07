import React from "react"
import {Layout} from "antd"
import {Route, Switch} from "react-router-dom";
import SideMenuP from "./sandBox/SideMenuP";
import TopHeaderP from "./sandBox/TopHeaderP";
import PatientHome from "./views/home/PatientHome";
import PatientList from "./views/list/PatientList";
import './PatientSandBox.css'
import PatientAppointment from "./views/PatientAppointment/PatientAppointment";

const {Content} = Layout


export default function patientSandBox(){
    return(
        <Layout>
            <SideMenuP></SideMenuP>
            <Layout className="site-layout">
                <TopHeaderP></TopHeaderP>

                <Content
                    className="site-layout-background"
                    style={{
                        margin: '24px 16px',
                        padding: 24,
                        minHeight: 280,
                    }}>
                    <Switch>
                        <Route exact path="/patSandBox/" component={PatientHome} />
                        <Route path="/patSandBox/patRegister" component={PatientAppointment}/>
                        <Route path="/patSandBox/patList" component={PatientList}/>
                    </Switch>
                </Content>
            </Layout>
        </Layout>


    )

}
