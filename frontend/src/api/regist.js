const API_URL = 'https://localhost:8443/api';

export const registerUser = async (credentials) => {
    try {
        const response = await fetch(`${API_URL}/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(credentials)
        });
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || 'Unknown error');
        }
        return response.json();
    } catch (error) {
        console.error('Error during registration:', error);
        throw error;
    }
};
