import axios from 'axios';

export const checkIntegrity = async (tableName) => {
    try {
        const response = await axios.get(`https://localhost:8443/api/checkIntegrity/${tableName}`);
        if (response.status === 200) {
            return response.data;  // 返回成功获取的完整性检查结果
        } else {
            throw new Error('完整性检查失败');
        }
    } catch (error) {
        console.error('完整性检查错误:', error);
        throw error;
    }
};

export const generateDigest = async (tableName) => {
    try {
        const response = await axios.post(`https://localhost:8443/api/generateDigest/${tableName}`);
        if (response.status === 200) {
            return response.data;
        } else {
            throw new Error('生成摘要请求失败');
        }
    } catch (error) {
        console.error('生成摘要错误:', error);
        throw error;
    }
};
