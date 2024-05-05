const patientState = {
    pat:{}
}

const patient = (state=patientState, action) =>{
    switch (action.type){
        case "setPatient":
            return{
                ...state,
                patientId: action.pat.patientId
            }
        default:
            return state;
    }
}

export default patient