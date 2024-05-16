import React, { useState } from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import { Link as RouterLink } from 'react-router-dom';
import Link from '@mui/material/Link';
import Paper from '@mui/material/Paper';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import ArrowBackIosNewIcon from '@mui/icons-material/ArrowBackIosNew';
import { connect } from "react-redux";
import { asyncSetUserObj } from "../../actions/auth";
import {IconButton, InputAdornment} from "@mui/material";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";

const defaultTheme = createTheme();

function SignInSide({ asyncSetUserObj, history }) {
    const [userName, setUserName] = useState('');
    const [passWord, setPassWord] = useState('');
    const [showPassword, setShowPassword] = useState(false);

    const handleClickShowPassword = () => {
        setShowPassword(!showPassword);
    };

    const handleMouseDownPassword = (event) => {
        event.preventDefault();
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        asyncSetUserObj({
            userName: userName,
            passWord: passWord
        }).then(res => {
            console.log('User logged:', res);
            history.push('/patSandBox/patRegister'); // 跳转到管理页面
        }).catch((error) => {
            console.error('Login failed:', error.message);
        });
    };

    return (
        <ThemeProvider theme={defaultTheme}>
            <Grid container component="main" sx={{ height: '100vh' }}>
                <CssBaseline />
                <Grid item xs={false} sm={4} md={7} sx={{ backgroundImage: 'url(loginbackg.jpg)', backgroundRepeat: 'no-repeat', backgroundColor: (t) => t.palette.mode === 'light' ? t.palette.grey[50] : t.palette.grey[900], backgroundSize: 'cover', backgroundPosition: 'center', }} />
                <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
                    <Link component={RouterLink} to="/" underline="hover">
                        <ArrowBackIosNewIcon />返回首页
                    </Link>
                    <Box sx={{ my: 8, mx: 4, display: 'flex', flexDirection: 'column', alignItems: 'center', }}>
                        <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                            <LockOutlinedIcon />
                        </Avatar>
                        <Typography component="h1" variant="h5">请登录</Typography>
                        <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 1 }}>
                            <TextField
                                margin="normal"
                                required fullWidth
                                id="userName"
                                label="用户名"
                                name="userName"
                                autoComplete="userName"
                                autoFocus value={userName}
                                onChange={e => setUserName(e.target.value)}
                            />
                            <TextField
                                margin="normal"
                                required
                                fullWidth
                                name="passWord"
                                label="密码"
                                type={showPassword ? 'text' : 'password'}
                                id="passWord"
                                autoComplete="current-password"
                                value={passWord}
                                onChange={e => setPassWord(e.target.value)}
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
                            <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>登录</Button>
                            <Grid container>
                                <Grid item xs>
                                    <Link component={RouterLink} to="/adminSignIn" underline="hover">管理员或医生登录</Link>
                                </Grid>
                                <Grid item>
                                    <Link component={RouterLink} to="/signUp" underline="hover">注册</Link>
                                </Grid>
                            </Grid>
                        </Box>
                    </Box>
                </Grid>
            </Grid>
        </ThemeProvider>
    );
}

const mapDispatchToProps = {
    asyncSetUserObj
};

export default connect(null, mapDispatchToProps)(SignInSide);
