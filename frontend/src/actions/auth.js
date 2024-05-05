import {loginAdmin, loginUser} from '../api/auth';
import {setMessage} from "./flash";

export const setUser = (user) => ({
    type: "setUser",
    user
});

export const loginError = (error) => ({
    type: "loginError",
    error
});

// redux-thunk异步处理
export const asyncSetUserObj = (fromData) => {
    return (dispatch) => {
        return loginUser(fromData)
            .then(response => {
                if (!response.token) {
                    throw new Error("Token not provided");
                }
                localStorage.setItem('token', response.token);
                dispatch(setUser({
                    userName: response.userName,
                    userId: response.userId,
                    token: response.token
                }));
                dispatch(setMessage('登陆成功，欢迎。', 'success'));
                return response;
            })
            .catch(error => {
                const errorMessage = error.response ? error.response.data : error.message;
                console.error('Login failed:', errorMessage);
                dispatch(setMessage( errorMessage, 'error'));
                throw error;
            });
    }
}

export const asyncSetAdminObj = (formData) => {
    return (dispatch) => {
        return loginAdmin(formData)
            .then(response => {
                if (!response.token) {
                    throw new Error("Token not provided");
                }
                localStorage.setItem('token', response.token);
                dispatch(setUser({
                    userName: response.userName,
                    userId: response.userId,
                    token: response.token
                }));
                dispatch(setMessage('登录成功，欢迎。', 'success'));
                return response;
            })
            .catch(error => {
                const errorMessage = error.response ? error.response.data : error.message;
                console.error('Login failed:', errorMessage);
                dispatch(setMessage(errorMessage, 'error'));
                throw error;
            });
    }
}