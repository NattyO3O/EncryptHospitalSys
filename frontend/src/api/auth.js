import axios from 'axios';

export const loginUser = async (credentials) => {
    try {
        const response = await axios.post('http://localhost:8080/api/login', credentials);
        if (response.status === 200) {
            return response.data;  // 仅当验证成功时
        } else {
            throw new Error('Authentication failed');
        }
    } catch (error) {
        console.error('Login error:', error);
        throw error;
    }
};

