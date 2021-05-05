import axios from 'axios';
import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import { useDispatch } from 'react-redux';

import '../../../css/Pages/Account/Register.css';
import { passwordHash } from '../../../Constants/PasswordHash';
import { link } from '../../../Constants/Constants';
import { login } from '../../../State/userSlice';

axios.defaults.baseURL = `${link}/account/login`;

export const Login = () => {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [verifiedUser, setVerifiedUser] = useState("");

  const dispatch = useDispatch();

  let history = useHistory();

  // abortController = new AbortController();

  const handleLoginSubmit = (e) => {
    e.preventDefault();
    if (validatePassword()) {
      getVerifiedUser();
      if (verifiedUser != null) {
        if (verifyCredentials()) {
          dispatch(login(verifiedUser));
          history.push('/');
        }
        else alert("Password does not math.");
      } 
      else alert("User does not exist.");
    } 
    else alert("Malformed password.");
  }

  const verifyCredentials = () => {
    if (userName == verifiedUser.userName &&
      passwordHash(password) == verifiedUser.passwordHash)
       return true;
    else return false;
  }

  const validatePassword = () => {
    if(password.length >= 5) return true;
    else return false;
  }

  const getUserFromCredentials = () => {
    var user = {
      id: null,
      userName: userName,
      email: '',
      passwordHash: passwordHash(password),
      login: '',
      role: '',
    }
    return user;
  }

  const handleUserNameChange = (evt) => {
    evt.preventDefault();
    setUserName(evt.target.value);
    getVerifiedUser();
  }

  const getVerifiedUser = () => {
    axios.post('/user/get', getUserFromCredentials()).then(
      res => {
        if (res.data != "") {
          setVerifiedUser(res.data);
        }
        else {
          setVerifiedUser(null);
        };
      }
    ).catch(err => {console.log(err)});
  }

  return (
    <div className="login-page">
      <h2>Login page</h2>
      <form>
        <label className="UserName">Username:</label>
          <input 
            className="UserName"
            type="text"
            name="login"
            placeholder="Your login"
            minLength="3"
            maxLength="20"
            onChange={evt => handleUserNameChange(evt)}>
          </input>

        <label className="password">Password:</label>
          <input
            className="password"
            type="password"
            name="password"
            placeholder="Your password"
            minLength="5"
            onChange={evt => setPassword(evt.target.value)}>
          </input>

        <input
          className="button"
          type="submit"
          value="Login"
          onClick={evt => handleLoginSubmit(evt)}>
        </input>
      </form>

      <Link to={'/register'}>Do not have an account? Register here.</Link>
    </div>
  )
}