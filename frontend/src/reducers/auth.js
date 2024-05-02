const userState = {
    user:{},
    error: null
}

const auth = (state=userState, action) =>{
    switch (action.type){
        case "setUser":
            return{
                ...state,
                user: action.user,
                error: null
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