const flashState = {
    message: '',
    severity: 'info',
    open: false
}

const flash = (state = flashState, action) => {
    switch (action.type){
        case "addmsg":
            return {
                ...state,
                message: action.payload.message,
                severity: action.payload.severity,
                open: true
            };
        case "delmsg":
            return {
                ...state,
                message: '',
                severity: 'info',
                open: false
            };
        default:
            return state;
    }
}

export default flash;