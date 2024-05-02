import { loginUser } from '../api/auth';
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
export const asyncSetUserObj = (credentials) => {
    return (dispatch) => {
        return loginUser(credentials)
            .then(response => {
                dispatch(setUser(response.data));
                dispatch(setMessage('登陆成功，欢迎。', 'success'));
                return response;
            })
            .catch(error => {
                if (error.response && error.response.data) {
                    dispatch(setMessage(error.response.data, 'error'));
                } else {
                    dispatch(setMessage('网络错误，请重试。', 'error'));
                }
                throw error;
            });
    }
}