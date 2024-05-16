import React, { useState } from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import {Link as RouterLink, useHistory} from 'react-router-dom';
import Link from '@mui/material/Link';
import Paper from '@mui/material/Paper';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import ArrowBackIosNewIcon from '@mui/icons-material/ArrowBackIosNew';
import {connect} from "react-redux";
import { asyncSetAdminObj } from "../../actions/auth";
import {IconButton, InputAdornment} from "@mui/material";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";

const defaultTheme = createTheme();

function AdminSignIn({ asyncSetAdminObj }) {
    const history = useHistory();
    const [userName, setUserName] = useState('');
    const [passWord, setPassWord] = useState('');
    const [certificate, setCertificate] = useState(null);
    const [p7b, setP7b] = useState(null);
    const [showPassword, setShowPassword] = useState(false);

    const handleClickShowPassword = () => {
        setShowPassword(!showPassword);
    };

    const handleMouseDownPassword = (event) => {
        event.preventDefault();
    };

    const handleCertificateChange = (event) => {
        // 安全检查，确保files数组存在且长度大于0
        if (event.target.files && event.target.files.length > 0) {
            const uploadedFile = event.target.files[0];
            setCertificate(uploadedFile);
        } else {
            console.log('No file selected.');
        }
    };

    const handleP7bChange = (event) => {
        // 安全检查，确保files数组存在且长度大于0
        if (event.target.files && event.target.files.length > 0) {
            const uploadedFile = event.target.files[0];
            setP7b(uploadedFile);
        } else {
            console.log('No file selected.');
        }
    }

    const handleSubmit = async (event) => {
        event.preventDefault();
        const formData = new FormData();
        formData.append('userName', userName);
        formData.append('passWord', passWord);
        formData.append('certificate', certificate);
        formData.append('p7b', p7b);

        try {
            const userData = await asyncSetAdminObj(formData);
            if (userData && userData.userType) {
                if (userData.userType === 'Admin') {
                    history.push('/adminSandBox/');
                } else if (userData.userType === 'Doctor') {
                    history.push('/docSandBox/docDuty');
                }
            } else {
                console.error('Unexpected user data structure:', userData);
            }
        } catch (error) {
            console.error('Admin Login failed:', error.message);
        }
    };

    return (
        <ThemeProvider theme={defaultTheme}>
            <Grid container component="main" sx={{ height: '100vh' }}>
                <CssBaseline />
                <Grid item xs={false} sm={4} md={7} sx={{
                    backgroundImage: 'url(loginbackg.jpg)', backgroundRepeat: 'no-repeat',
                    backgroundColor: (t) => t.palette.mode === 'light' ? t.palette.grey[50] : t.palette.grey[900],
                    backgroundSize: 'cover', backgroundPosition: 'center',
                }} />
                <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
                    <Link component={RouterLink} to="/" underline="hover">
                        <ArrowBackIosNewIcon />返回首页
                    </Link>
                    <Box sx={{ my: 8, mx: 4, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                        <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                            <LockOutlinedIcon />
                        </Avatar>
                        <Typography component="h1" variant="h5">请登录</Typography>
                        <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 1 }}>
                            <TextField
                                margin="normal"
                                required
                                fullWidth
                                id="userName"
                                label="用户名"
                                name="userName"
                                autoComplete="userName"
                                autoFocus
                                value={userName}
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
                            <TextField
                                margin="normal"
                                required
                                fullWidth
                                name="certificate"
                                label="个人数字证书 .cer文件"
                                type="file"
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                onChange={handleCertificateChange}
                            />
                            <TextField
                                margin="normal"
                                required
                                fullWidth
                                name="certificate"
                                label="中间CA和根CA证书 .p7b文件"
                                type="file"
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                onChange={handleP7bChange}
                            />
                            <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>登录</Button>
                            <Grid container>
                                <Grid item xs>
                                    <Link component={RouterLink} to="/signIn" underline="hover">用户登录</Link>
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
    asyncSetAdminObj
};

export default connect(null, mapDispatchToProps)(AdminSignIn);
