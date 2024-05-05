const docState = {
    doc: {}
}

const doctor = (state = docState, action) => {
    switch (action.type) {
        case "setDoc":
            return {
                ...state,
                doc: action.doc
            }
        default:
            return state;
    }
}

export default doctor;