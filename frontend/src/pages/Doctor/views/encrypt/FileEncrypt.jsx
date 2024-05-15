import React, { useState } from 'react';
import axios from 'axios';
import './FileEncrypt.css';  // 引入样式文件

const FileEncrypt = () => {
    const [file, setFile] = useState(null);
    const [algorithm, setAlgorithm] = useState('3DES');
    const [encryptedData, setEncryptedData] = useState('');

    const handleFileChange = (event) => {
        setFile(event.target.files[0]);
    };

    const handleAlgorithmChange = (event) => {
        setAlgorithm(event.target.value);
    };

    const handleEncrypt = async () => {
        if (!file) {
            alert('请先选择文件！');
            return;
        }
        const formData = new FormData();
        formData.append('file', file);
        formData.append('algorithm', algorithm);

        try {
            const response = await axios.post('https://localhost:8443/api/encrypt', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            setEncryptedData(response.data);
            alert('文件加密成功！');
        } catch (error) {
            console.error('加密错误:', error);
        }
    };

    const handleUpload = async () => {
        if (!encryptedData) {
            alert('请先加密文件！');
            return;
        }
        const formData = new FormData();
        formData.append('fileName', file.name);
        formData.append('algorithm', algorithm);
        formData.append('encryptedData', encryptedData);

        try {
            await axios.post('https://localhost:8443/api/upload', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            alert('文件上传成功！');
        } catch (error) {
            console.error('上传错误:', error);
        }
    };


    return (
        <div className="container mt-5">
            <table className="table-encrypt">
                <tbody>
                <tr>
                    <th>选择文件</th>
                    <td>
                        <input style={{width:'300px'}} type="text" value={file ? file.name : ''} readOnly />
                        <button style={{marginLeft:'10px'}} onClick={() => document.getElementById('fileInput').click()}>选择文件</button>
                        <input id="fileInput" type="file" onChange={handleFileChange} style={{ display: 'none' }} />
                    </td>
                </tr>
                <tr>
                    <th>选择加密算法</th>
                    <td>
                        <select value={algorithm} onChange={handleAlgorithmChange}>
                            <option value="AES">AES</option>
                            <option value="SM4">SM4</option>
                        </select>
                        <button style={{marginLeft:'10px'}} onClick={handleEncrypt}>加密</button>
                    </td>
                </tr>
                </tbody>
            </table>
            <button onClick={handleUpload} style={{ float: 'right', backgroundColor: '#3A77A8', color: 'white', marginTop: '10px', fontSize:'17px', borderRadius:'4px',borderColor:'#3A77A8'}}>上传</button>
        </div>
    );
};

export default FileEncrypt;