import React from "react";
import {Layout, Menu} from 'antd';
import '../../Doctor/sandBox/index.css'
import {
    UserOutlined,
    CalendarOutlined,
    CloudUploadOutlined,
} from '@ant-design/icons';
import {withRouter} from "react-router-dom";
import SubMenu from "antd/es/menu/SubMenu";
const {Sider} = Layout;

const menuList = [
    {
        key:"/adminSandBox/",
        title:"首页",
        icon:<UserOutlined />
    },
    {
        key:"/adminSandBox/regAdmin",
        title:"管理员账号管理",
        icon:<CalendarOutlined />
    },
    {
        key:"/adminSandBox/regDoc",
        title:"医生账号管理",
        icon:<CloudUploadOutlined />
    },
]

function AdminSideMenu(props){
    const renderMenu = (menuList) => {
        return menuList.map(item=>{
            if (item.children){
                return <SubMenu></SubMenu>
            }
            return <Menu.Item style={{color:'white',fontSize:'16px',fontFamily:'Arial'}} key={item.key} icon={item.icon} onClick={()=>{
                props.history.push(item.key)
            }}>{item.title}</Menu.Item>
        })

    }
    return(
        <Sider trigger={null} collapsible style={{backgroundColor:'#2A5885'}}>
            <div className="logo">医疗信息系统</div>
            <Menu mode="inline" defaultSelectedKeys={['1']} style={{backgroundColor:'#2A5885'}} className="custom-menu">
                {renderMenu(menuList)}
            </Menu>
        </Sider>
    )
}

export default withRouter(AdminSideMenu)