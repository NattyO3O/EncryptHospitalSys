import React, { useState } from 'react';
import axios from 'axios';
import './FileDecrypt.css'; // 确保CSS样式文件存在，用于美化界面

const FileDecrypt = () => {
    const [fileName, setFileName] = useState('');
    const [files, setFiles] = useState([]);

    const handleSearch = async () => {
        const encodedFileName = encodeURIComponent(fileName);  // 编码文件名以确保URL的正确性
        try {
            const response = await axios.get(`https://localhost:8443/api/decrypt/files?fileName=${encodedFileName}`);
            setFiles(response.data);
            console.log(response.data);
        } catch (error) {
            console.error('Error fetching files', error);
            alert('搜索文件时出错: ' + error.message);
        }
    };


    const handleDownload = async (fileId) => {
        try {
            const response = await axios.get(`https://localhost:8443/api/decrypt/download/${fileId}`, { responseType: 'blob' });
            if (response.data.size > 0) {
                const contentDisposition = response.headers['content-disposition'];
                if (contentDisposition) {
                    const filenameMatch = contentDisposition.match(/filename="?(.+?)"?$/);
                    if (filenameMatch && filenameMatch[1]) {
                        const filename = filenameMatch[1];
                        const url = window.URL.createObjectURL(new Blob([response.data]));
                        const link = document.createElement('a');
                        link.href = url;
                        link.setAttribute('download', filename.replace(/"/g, ''));
                        document.body.appendChild(link);
                        link.click();
                        document.body.removeChild(link);  // 清除DOM中的链接
                        alert('文件下载成功！');
                    } else {
                        alert('无法解析文件名！');
                    }
                } else {
                    console.error('响应中缺少content-disposition头部');
                    alert('下载失败：无法获取文件名');
                }
            } else {
                alert('下载的文件为空！');
            }
        } catch (error) {
            console.error('Error downloading file:', error);
            alert('下载文件过程中发生错误，请检查控制台获取更多信息。');
        }
    };

    return (
        <div className="file-decrypt-container">
            <input
                type="text"
                value={fileName}
                onChange={(e) => setFileName(e.target.value)}
                placeholder="输入文件名称搜索"
            />
            <button onClick={handleSearch}>搜索</button>
            <table>
                <thead>
                <tr>
                    <th>文件名称</th>
                    <th>加密算法</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                {files.map(file => (
                    <tr key={file.fileId}>
                        <td>{file.fileName}</td>
                        <td>{file.algorithm}</td>
                        <td>
                            <button onClick={() => handleDownload(file.fileId)}>下载</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default FileDecrypt;
