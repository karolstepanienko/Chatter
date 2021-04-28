import React from 'react';

import { Link } from 'react-router-dom';

import LoginLogic from './LoginLogic.js';
import '../../../css/Pages/Account/Register.css';

export function Login() {

  // Imported code objects
  var loginLogic = new LoginLogic();

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
            onChange={evt => loginLogic.handleLoginChange(evt)}>
          </input>

        <label className="password">Password:</label>
          <input
            className="password"
            type="password"
            name="password"
            placeholder="Your password"
            minLength="5"
            onChange={evt => loginLogic.handlePasswordChange(evt)}>
          </input>

        <input
          className="button"
          type="submit"
          value="Login"
          onClick={evt => loginLogic.handleLoginSubmit(evt)}>
        </input>
      </form>

      <Link to={'/register'}>Do not have an account? Register here.</Link>
    </div>
  );
}