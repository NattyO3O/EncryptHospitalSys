import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [apiResponse, setApiResponse] = useState('');

  useEffect(() => {
    fetch('/test')
        .then(response => response.text())
        .then(message => {
          setApiResponse(message);
        });
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <p>API Response: {apiResponse}
        </p>
      </header>
    </div>
  );
}

export default App;
