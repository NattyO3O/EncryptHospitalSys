import axios from 'axios';

export const loginUser = async (credentials) => {
    try {
        const response = await axios.post('http://localhost:8080/api/login', credentials);
        if (response.status === 200) {
            return response.data;  // 仅当验证成功时
        } else {
            throw new Error('身份验证失败');
        }
    } catch (error) {
        console.error('登陆错误:', error);
        throw error;
    }
};

export const loginAdmin = async (formData) => {
    try {
        const response = await axios.post('http://localhost:8080/api/adminLogin', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
        if (response.status === 200) {
            return response.data;
        } else {
            throw new Error('登陆失败');
        }
    } catch (error) {
        console.error('登录出错:', error);
        throw error;
    }
};