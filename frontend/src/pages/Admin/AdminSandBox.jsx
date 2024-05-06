import React from "react"
import {Layout} from "antd"
import AdminSideMenu from "./sandBox/AdminSideMenu"
import AdminTopHeader from "./sandBox/AdminTopHeader"
import '../Doctor/HospitalSandBox.css'
import {Route, Switch} from "react-router-dom";
import AdminHome from "./views/AdminHome/AdminHome";
import regAdmin from "./views/regAdmin/regAdmin";
import regDoc from "./views/regDoc/regDoc";
const {Content} = Layout

export default function AdminSandBox(){
    return(
        <Layout>
            <AdminSideMenu></AdminSideMenu>
            <Layout className="site-layout">
                <AdminTopHeader></AdminTopHeader>

                <Content
                    className="site-layout-background"
                    style={{
                        margin: '24px 16px',
                        padding: 24,
                        minHeight: 280,
                    }}>
                    <Switch>
                        <Route exact path="/adminSandBox/" component={AdminHome} />
                        <Route path="/adminSandBox/regAdmin" component={regAdmin} />
                        <Route path="/adminSandBox/regDoc" component={regDoc} />
                    </Switch>
                </Content>
            </Layout>
        </Layout>
    )
}