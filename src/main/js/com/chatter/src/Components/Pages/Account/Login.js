import axios from 'axios';
import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import { useDispatch } from 'react-redux';

import '../../../css/Pages/Account/Register.css';
import { link } from '../../../Constants/Constants';
import { login } from '../../../State/userSlice';

axios.defaults.baseURL = `${link}/account/login`;

export const Login = () => {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [passwordVerified, setPasswordVerified] = useState(false);
  const [verifiedUser, setVerifiedUser] = useState("");

  const dispatch = useDispatch();

  let history = useHistory();

  const handleLoginSubmit = (e) => {
    e.preventDefault();
    if (validatePassword()) {
      checkPassword();
      if (passwordVerified) {
        dispatch(login(verifiedUser));
        history.push('/user');
      } else alert("Password does not math.");
    } 
    else alert("Malformed password.");
  }

  const checkPassword = () => {
    axios.post('/user/check/password', getUserFromCredentials()).then(
      res => {
        setPasswordVerified(res.data);
      }
    );
  }

  const validatePassword = () => {
    if(password.length >= 5) return true;
    else return false;
  }

  const getUserFromCredentials = () => {
    var user = {
      id: null,
      userName: userName,
      login: '',
      email: '',
      password: password,
      role: '',
    }
    return user;
  }

  const handleUserNameChange = (evt) => {
    evt.preventDefault();
    setUserName(evt.target.value);
    getVerifiedUser();
  }

  const handlePasswordChange = (evt) => {
    evt.preventDefault();
    setPassword(evt.target.value);
    getVerifiedUser();
    checkPassword();
  }

  const getVerifiedUser = () => {
    axios.post('/user/get', getUserFromCredentials()).then(
      res => {
        if (res.data != "") {
          setVerifiedUser(res.data);
        }
        else {
          console.log("empty response");
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
            onChange={evt => handlePasswordChange(evt)}>
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