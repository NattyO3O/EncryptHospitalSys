import axios from 'axios';

const API_BASE_URL = 'https://localhost:8443/api';

export const fetchDoctors = () => {
    return axios.get(`${API_BASE_URL}/doctor/all`);
};

export const addDoctor = (userData, doctorData) => {
    return axios.post(`${API_BASE_URL}/doctor/add`, { userData, doctorData });
};

export const updateDoctor = (doctorId, doctorData) => {
    return axios.put(`${API_BASE_URL}/doctor/update/${doctorId}`, doctorData);
};

export const deleteDoctor = (doctorId) => {
    return axios.delete(`${API_BASE_URL}/doctor/delete/${doctorId}`);
};

export const fetchAdmins = () => {
    return axios.get(`${API_BASE_URL}/admin/all`);
};

export const addAdmin = (username, password) => {
    return axios.post(`${API_BASE_URL}/admin/add`, { username, password });
};

export const deleteAdmin = (userID) => {
    return axios.delete(`${API_BASE_URL}/admin/delete/${userID}`);
};
