import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { Snackbar, Alert } from '@mui/material';
import { clearMessage } from '../actions/flash';

const PopMessage = () => {
    const dispatch = useDispatch();
    const { message, severity, open } = useSelector(state => state.flash);

    const handleClose = () => {
        dispatch(clearMessage());
    };

    return (
        <Snackbar open={open} autoHideDuration={6000} onClose={handleClose} anchorOrigin={{ vertical: 'top', horizontal: 'center' }}>
            <Alert onClose={handleClose} severity={severity} sx={{ width: '100%' }}>
                {message}
            </Alert>
        </Snackbar>
    );
};

export default PopMessage;
