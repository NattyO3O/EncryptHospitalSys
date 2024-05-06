import React from "react"
import {Layout} from "antd"
import {Route, Switch} from "react-router-dom";
import './HospitalSandBox.css'
import PatientRegister from "./views/PatientRegister/PatientRegister";
const {Content} = Layout
import SideMenu from "./sandBox/SideMenuP";
import TopHeader from "./sandBox/TopHeaderP";


export default function patientSandBox(){
    return(
        <Layout>
            <SideMenu></SideMenu>
            <Layout className="site-layout">
                <TopHeader></TopHeader>

                <Content
                    className="site-layout-background"
                    style={{
                        margin: '24px 16px',
                        padding: 24,
                        minHeight: 280,
                    }}>
                    <Switch>
                        <Route path="/home" component={PatientRegister}/>

                    </Switch>
                </Content>
            </Layout>
        </Layout>


    )

}