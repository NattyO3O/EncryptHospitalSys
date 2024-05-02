import React, { useState } from 'react';
import { withRouter } from 'react-router-dom';
import {
    Avatar,
    Button,
    CssBaseline,
    TextField,
    Link,
    Grid,
    Box,
    Typography,
    Container,
    IconButton, InputAdornment, LinearProgress
} from '@mui/material';
import { Link as RouterLink } from 'react-router-dom';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { useDispatch } from 'react-redux';
import { setMessage } from '../../actions/flash';
import { registerUser } from "../../api/regist";

const defaultTheme = createTheme();

function SignUp({ history }) {
    const [userName, setUserName] = useState('');
    const [passWord, setPassWord] = useState('');
    const [passwordStrength, setPasswordStrength] = useState(0);
    const [showPassword, setShowPassword] = useState(false);

    const dispatch = useDispatch();

    const assessPasswordStrength = (password) => {
        let strength = 0;
        if (password.length > 5) strength += 20;
        if (/[a-z]/.test(password)) strength += 20;
        if (/[A-Z]/.test(password)) strength += 20;
        if (/[0-9]/.test(password)) strength += 20;
        if (/[^A-Za-z0-9]/.test(password)) strength += 20;
        return strength;
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        if (name === 'UserName') {
            setUserName(value);
        } else if (name === 'PassWord') {
            setPassWord(value);
            setPasswordStrength(assessPasswordStrength(value));
        }
    };

    const handleClickShowPassword = () => {
        setShowPassword(!showPassword);
    };

    const handleMouseDownPassword = (event) => {
        event.preventDefault();
    };

    const onSubmit = async (e) => {
        e.preventDefault();
        if (!userName || !passWord) {
            dispatch(setMessage('用户名或密码不能为空！', 'error'));
            return;
        }

        try {
            await registerUser({ userName, passWord });
            dispatch(setMessage('注册成功, 3秒后自动跳转至登录页面。', 'success'));
            setTimeout(() => {
                history.push('/signIn');
            }, 3000);
        } catch (error) {
            console.error('Registration error:', error.message);
            dispatch(setMessage(error.message || '网络错误，请重试。', 'error'));
        }
    };

    return (
        <ThemeProvider theme={defaultTheme}>
            <Link component={RouterLink} to="/" underline="hover" style={{ textDecoration: 'none', color: 'inherit', position: 'absolute', top: 10, left: 10 }}>
                <Button startIcon={<ArrowBackIosNewIcon />} color="inherit">
                    返回首页
                </Button>
            </Link>
            <Container component="main" maxWidth="xs">
                <CssBaseline />
                <Box
                    sx={{
                        marginTop: 8,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                    }}
                >
                    <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                        <LockOutlinedIcon />
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        注册
                    </Typography>
                    <Box component="form" noValidate onSubmit={onSubmit} sx={{ mt: 3 }}>
                        <Grid container spacing={2}>
                            <Grid item xs={12}>
                                <TextField
                                    required
                                    fullWidth
                                    id="UserName"
                                    label="用户名"
                                    name="UserName"
                                    autoComplete="UserName"
                                    value={userName}
                                    onChange={handleChange}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    required
                                    fullWidth
                                    name="PassWord"
                                    label="密码"
                                    type={showPassword ? 'text' : 'password'}
                                    id="PassWord"
                                    autoComplete="new-password"
                                    value={passWord}
                                    onChange={handleChange}
                                    InputProps={{
                                        endAdornment: (
                                            <InputAdornment position="end">
                                                <IconButton
                                                    aria-label="toggle password visibility"
                                                    onClick={handleClickShowPassword}
                                                    onMouseDown={handleMouseDownPassword}
                                                >
                                                    {showPassword ? <Visibility /> : <VisibilityOff />}
                                                </IconButton>
                                            </InputAdornment>
                                        ),
                                    }}
                                />
                                <Typography variant="caption" display="block" gutterBottom sx={{ mt: 1 }}>
                                    密码强度
                                </Typography>
                                <LinearProgress variant="determinate" value={passwordStrength} sx={{ mt: 1, width: '100%' }} />
                            </Grid>
                        </Grid>
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{ mt: 3, mb: 2 }}
                        >
                            注册
                        </Button>
                        <Grid container justifyContent="flex-end">
                            <Grid item>
                                <Link component={RouterLink} to="/signIn" underline="hover">
                                    已有账号？登录
                                </Link>
                            </Grid>
                        </Grid>
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    );
}

export default withRouter(SignUp);