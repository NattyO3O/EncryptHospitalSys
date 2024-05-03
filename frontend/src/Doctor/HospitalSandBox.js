import React from "react"
import {Layout} from "antd"
import SideMenu from "./sandBox/SideMenu"
import TopHeader from "./sandBox/TopHeader"
import DoctorList from "./views/list/DoctorList";
import DoctorDuty from "./views/duty/DoctorDuty";
import DManagement from "./views/management/DManagement";
import {Route, Switch} from "react-router-dom";
import './HospitalSandBox.css'
import DoctorHome from "./views/home/DoctorHome";
import FileEncrypt from "./views/encrypt/FileEncrypt";
const {Content} = Layout


export default function HospitalSandBox(){
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
                        <Route path="/home" component={DoctorHome}/>
                        <Route path="/duty" component={DoctorDuty}/>
                        <Route path="/management" component={DManagement}/>
                        <Route path="/list" component={DoctorList}/>
                        <Route path="/encrypt" component={FileEncrypt}/>
                    </Switch>
                </Content>
            </Layout>
        </Layout>


    )

}