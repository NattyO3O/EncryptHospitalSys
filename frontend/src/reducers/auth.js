const userState = {
    user:{},
    error: null
}

const auth = (state=userState, action) =>{
    switch (action.type){
        case "setUser":
            return{
                ...state,
                userName: action.user.userName,
                userId: action.user.userId,
                userType: action.user.userType,
                token: action.user.token,
                isAuthenticated: true
            }
        case "loginError":
            return {
                ...state,
                error: action.error
            }
        default:
            return state;
    }
}

export default auth