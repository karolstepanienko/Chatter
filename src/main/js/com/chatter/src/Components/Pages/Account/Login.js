import React from 'react';

import '../../../css/Pages/Account/Register.css';

export function Login(props) {
  return (
    <div className="login-page">
      <h2>Login page</h2>
      <form>

      <label className="login">Login:</label>
        <input 
          className="login"
          type="text"
          name="login"
          placeholder="Your login"
          minLength="3"
          maxLength="20"
          onChange={evt => this.handleLoginChange(evt)}>
        </input>

      <label className="password">Password:</label>
        <input
          className="password"
          type="password"
          name="password"
          placeholder="Your password"
          minLength="5"
          onChange={evt => this.handlePasswordChange(evt)}>
        </input>
      </form>

      <a href="/register">Do not have an account? Register here.</a>
    </div>
  );
}