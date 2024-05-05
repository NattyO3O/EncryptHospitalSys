import React from "react"
import {Layout} from "antd"
import SideMenu from "./sandBox/SideMenu"
import TopHeader from "./sandBox/TopHeader"
import './HospitalSandBox.css'
import {Route, Switch} from "react-router-dom";
import DoctorHome from "./views/home/DoctorHome";
import DoctorDuty from "./views/duty/DoctorDuty";
import DoctorList from "./views/list/DoctorList";
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
                        <Route path="/docSandBox/docHome" component={DoctorHome} />
                        <Route path="/docSandBox/docDuty" component={DoctorDuty} />
                        <Route path="/docSandBox/docList" component={DoctorList} />
                        <Route path="/docSandBox/docEncrypt" component={FileEncrypt} />
                        <Route path="/docSandBox/docDecrypt" component={FileDecrypt}/>
                    </Switch>
                </Content>
            </Layout>
        </Layout>
    )
}