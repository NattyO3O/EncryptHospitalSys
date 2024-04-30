import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
    // 使用 useState Hook 来管理状态
    const [users, setUsers] = useState([]);

    useEffect(() => {
        // 发起 GET 请求到后端 API
        fetch('http://localhost:8080/test')
            .then(response => {
                // 检查响应状态
                if (response.ok) {
                    return response.json();  // 解析 JSON 数据
                }
                throw new Error('Network response was not ok.');  // 抛出异常
            })
            .then(data => {
                setUsers(data);  // 更新状态
            })
            .catch(error => {
                console.error('Error fetching data:', error);
            });
    }, []);  // 空依赖数组表示这个 effect 只在组件首次渲染时运行

    return (
        <div>
            <h1>User List</h1>
            <ul>
                {users.map(user => (
                    <li key={user.userID}>
                        {user.userID} - {user.userName} - {user.type}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default App;