import React from "react";
import {Layout, Menu} from 'antd';
import './index.css'
import Icon, {
    UploadOutlined,
    UserOutlined,
    CalendarOutlined,
    ContactsOutlined,
    MedicineBoxOutlined

} from '@ant-design/icons';
import {withRouter} from "react-router-dom";
import SubMenu from "antd/es/menu/SubMenu";
const {Sider} = Layout;

const menuList = [
    {
        key:"/home",
        title:"首页",
        icon:<UserOutlined />
    },
    {
        key:"/duty",
        title:"我的值班表",
        icon:<CalendarOutlined />
    },
    {
        key:"/management",
        title:"诊断管理",
        icon:<MedicineBoxOutlined />
    },
    {
        key:"/list",
        title:"个人信息管理",
        icon:<ContactsOutlined />
    },
    {
        key:"/encrypt",
        title:"病历共享",
        icon:<file />
    }
]

function SideMenu(props){
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

export default withRouter(SideMenu)