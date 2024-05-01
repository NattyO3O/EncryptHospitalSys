import React, { Component } from 'react'
import { Link } from "react-router-dom"

export default class HeaderNav extends Component {
    render() {
        return(
            <nav className="navbar navbar-expand-lg navbar-light bg-light">
                <div className="container-fluid">
                    <Link className="navbar-brand" to="/">医疗信息系统</Link>
                    <div className="navbar-collapse">
                        <ul className="navbar-nav ml-auto">
                            <li>
                                <Link className="nav-link" to="/signIn">登录</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/signUp">注册</Link>
                            </li>
                        </ul>
                    </div>
                    </div>
            </nav>
        )
    }
}