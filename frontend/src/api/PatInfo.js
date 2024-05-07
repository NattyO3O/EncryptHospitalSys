import axios from 'axios';

const API_URL = 'http://localhost:8080/api/patient';

export const fetchPatientDetails = (userId) => {
    return axios.get(`${API_URL}/details/${userId}`);
};

export const updatePatientDetails = (patientData) => {
    return axios.post(`${API_URL}/update`, patientData);
};