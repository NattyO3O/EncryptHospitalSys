const patientState = {
    pat:{}
}

const patient = (state=patientState, action) =>{
    switch (action.type){
        case "setPatient":
            return{
                ...state,
                pat: action.pat
            }
        default:
            return state;
    }
}

export default patient