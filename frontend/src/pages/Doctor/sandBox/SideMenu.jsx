import React from "react";
import {Layout, Menu} from 'antd';
import './index.css'
import {
    CalendarOutlined,
    ContactsOutlined,
    CloudUploadOutlined,
    CloudDownloadOutlined
} from '@ant-design/icons';
import {withRouter} from "react-router-dom";
import SubMenu from "antd/es/menu/SubMenu";
const {Sider} = Layout;

const menuList = [
    {
        key:"/docSandBox/docDuty",
        title:"我的值班表",
        icon:<CalendarOutlined />
    },
    {
        key:"/docSandBox/docEncrypt",
        title:"病历上传",
        icon:<CloudUploadOutlined />
    },
    {
        key:"/docSandBox/docDecrypt",
        title:"病历下载",
        icon:<CloudDownloadOutlined />
    },
    {
        key:"/docSandBox/docList" ,
        title:"个人信息管理",
        icon:<ContactsOutlined />
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