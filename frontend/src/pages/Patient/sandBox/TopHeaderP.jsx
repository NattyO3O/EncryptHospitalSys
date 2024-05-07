import React, {useState} from "react";
import {Dropdown, Layout,Menu,Avatar} from 'antd';
import {MenuFoldOutlined, MenuUnfoldOutlined, UserOutlined} from "@ant-design/icons";
import {useDispatch, useSelector} from "react-redux";
import {useHistory} from "react-router-dom";
const {Header} = Layout;

export default function TopHeaderP(){
    const dispatch = useDispatch();
    const history = useHistory(); // Use history for navigation
    const userName = useSelector(state => state.auth.userName);
    const [collapsed, setCollapsed] = useState(false);

    const changeCollapsed = () => {
        setCollapsed(!collapsed);
        // dispatch({ type: 'TOGGLE_SIDEBAR' });
    };

    const handleLogout = () => {
        localStorage.removeItem('token'); // Remove the token from localStorage
        dispatch({ type: 'LOGOUT' }); // Dispatch a logout action to update redux state
        history.push('/'); // Navigate to the homepage
    };

    const menu = (
        <Menu>
            <Menu.Item onClick={handleLogout} danger>退出登录</Menu.Item>
        </Menu>
    );

    return (
        <Header
            className="site-layout-background"
            style={{
                padding: '0 16px',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'space-between'
            }}
        >
            {
                collapsed ? (
                    <MenuUnfoldOutlined onClick={changeCollapsed}/>
                ) : (
                    <MenuFoldOutlined onClick={changeCollapsed}/>
                )
            }

            <div style={{float: "right"}}>
                <span>Welcome, {userName}</span>
                <Dropdown overlay={menu}>
                    <Avatar size={32} icon={<UserOutlined />} style={{marginLeft: 12}} />
                </Dropdown>
            </div>
        </Header>
    );
}