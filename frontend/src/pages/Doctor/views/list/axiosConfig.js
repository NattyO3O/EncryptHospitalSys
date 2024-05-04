import axios from 'axios';

const instance = axios.create({
    baseURL: 'http://localhost:8080' // 确保这是你的后端服务器地址
});

export default instance;
