export const setMessage = (message, severity) => ({
    type: "addmsg",
    payload: { message, severity }
});

export const clearMessage = () => ({
    type: "delmsg"
});