import React from "react";
import { Layout, Menu } from 'antd';
import { useHistory } from "react-router-dom";
import {
    UserOutlined,
    CalendarOutlined,
    ContactsOutlined,
    MedicineBoxOutlined
} from '@ant-design/icons';
import './index.css';

const { Sider } = Layout;

const menuList = [
    {
        key:"/docSandBox/docHome",
        title:"首页",
        icon:<UserOutlined />
    },
    {
        key:"/docSandBox/docDuty",
        title:"我的值班表",
        icon:<CalendarOutlined />
    },
    {
        key:"/docSandBox/docList",
        title:"个人信息管理",
        icon:<ContactsOutlined />
    },
    {
        key:"/docSandBox/docEncrypt",
        title:"病历共享",
        icon:<file />
    }
];

function SideMenu() {
    const history = useHistory();

    const navigate = (path) => {
        history.push(path);
    };

    const renderMenu = (menuList) => {
        return menuList.map(item => (
            <Menu.Item key={item.key} icon={item.icon} onClick={() => navigate(item.key)}>
                {item.title}
            </Menu.Item>
        ));
    };

    return (
        <Sider trigger={null} collapsible style={{ backgroundColor: '#2A5885' }}>
            <div className="logo">医疗信息系统</div>
            <Menu mode="inline" theme="dark" defaultSelectedKeys={['1']} style={{ backgroundColor: '#2A5885' }}>
                {renderMenu(menuList)}
            </Menu>
        </Sider>
    );
}

export default SideMenu;