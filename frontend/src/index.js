import React from 'react';
import ReactDOM from 'react-dom';
import AppRouter from "./router";
import { Provider } from "react-redux";
import store from "./store";
import { setUser } from "./actions/auth";
import axios from "axios";

const loadUserFromToken = async () => {
    const token = localStorage.getItem('token');
    if (token) {
        try {
            const response = await axios.get('http://localhost:8080/api/validateToken', {
                headers: { 'Authorization': `Bearer ${token}` }
            });
            store.dispatch(setUser(response.data));
        } catch (error) {
            console.error("Token validation failed:", error);
            localStorage.removeItem('token');
            window.location.href = '/';
        }
    }
};

loadUserFromToken();

ReactDOM.render(
    <Provider store={store}>
        <AppRouter />
    </Provider>,
    document.getElementById('root')
);
