import { combineReducers } from "redux";
import flash from "./flash"
import auth from "./auth";
import doctor from "./doctor";
import patient from "./patient";

const rootReducer = combineReducers({
    flash,
    auth,
    doctor,
    patient
})

export default rootReducer