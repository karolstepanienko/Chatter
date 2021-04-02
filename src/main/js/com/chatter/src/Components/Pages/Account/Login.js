import React from 'react';

const backend = React.createContext('http://localhost:8080/api/');


export function Login(props) {
  return (
    <div className="login-page">
      <h2>Login page</h2>
      <a href="/register">Do not have an account? Register here.</a>
    </div>
  );
}