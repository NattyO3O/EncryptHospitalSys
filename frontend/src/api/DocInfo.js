import axios from 'axios';

const API_URL = 'https://localhost:8443/api/doctor';

export const fetchDoctorDetails = (userId) => {
    return axios.get(`${API_URL}/details/${userId}`);
};

export const updateDoctorDetails = (doctorData) => {
    return axios.post(`${API_URL}/update`, doctorData);
};