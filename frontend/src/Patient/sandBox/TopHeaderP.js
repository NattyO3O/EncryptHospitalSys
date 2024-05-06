import React, {useState} from "react";
import {Dropdown, Layout,Menu,Avatar} from 'antd';
import {MenuFoldOutlined, MenuUnfoldOutlined, UserOutlined} from "@ant-design/icons";
const {Header} = Layout;

export default function TopHeaderP(){
    const [collapsed,setCollapsed] = useState(false)
    const changeCollapsed = () => {
        setCollapsed(!collapsed)
    }

    const menu = (
        <Menu>
            <Menu.Item>
                患者
            </Menu.Item>
            <Menu.Item danger>退出登录</Menu.Item>
        </Menu>
    );

    return(
        <Header
            className="site-layout-background"
            style={{
                padding: '0 16px',
            }}
        >
            {
                collapsed ? <MenuUnfoldOutlined onClick={changeCollapsed}/>:<MenuFoldOutlined onClick={changeCollapsed}/>
            }

            <div style={{float:"right"}}>
                <span>welcome</span>
                <Dropdown overlay={menu}>
                    <Avatar size={32} icon={<UserOutlined />} />
                </Dropdown>
            </div>
        </Header>
    )
}