import React from "react"
import {Layout} from "antd"
import SideMenu from "./sandBox/SideMenu"
import TopHeader from "./sandBox/TopHeader"
import DoctorList from "./views/list/DoctorList";
import DoctorDuty from "./views/duty/DoctorDuty";
import {Route, Switch} from "react-router-dom";
import './HospitalSandBox.css'
import DoctorHome from "./views/home/DoctorHome";
import FileEncrypt from "./views/encrypt/FileEncrypt";
import FileDecrypt from "./views/decrypt/FileDecrypt";
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
                        <Route path="/encrypt" component={FileEncrypt}/>
                        <Route path="/decrypt" component={FileDecrypt}/>
                        <Route path="/list" component={DoctorList}/>
                    </Switch>
                </Content>
            </Layout>
        </Layout>


    )

}