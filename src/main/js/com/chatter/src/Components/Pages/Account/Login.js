import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useHistory } from "react-router-dom";
import { useDispatch } from "react-redux";

import "../../../css/Pages/Account/Register.css";
import "../../../css/Expire/Expire.css";

import { link } from "../../../Constants/Constants";
import { login } from "../../../State/userSlice";
import Expire from "../../DisappearingComponent/Expire";


export const Login = () => {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [verifiedUser, setVerifiedUser] = useState("");
  const [tokenType, setTokenType] = useState("");
  const [accessToken, setAccessToken] = useState("");
  const [badUsernameOrPassword, setBadUsernameOrPassword] = useState(false);

  const dispatch = useDispatch();

  let history = useHistory();

  const handleLoginSubmit = (e) => {
    e.preventDefault();
    getLoggedInData();
  };

  const handleUserNameChange = (evt) => {
    evt.preventDefault();
    setUserName(evt.target.value);
  };

  const handlePasswordChange = (evt) => {
    evt.preventDefault();
    setPassword(evt.target.value);
  };

  const getUserDetails = () => {
    var userDetails = {
      userName: userName,
      password: password 
    };
    return userDetails;
  };

  const getLoggedInData = () => {
    axios.post(`${link}/account/login/signin`, getUserDetails()).then(
      res => {
        setTokenType(res.data.tokenType);
        setAccessToken(res.data.accessToken);
      }
    ).catch(err => {
      setBadUsernameOrPassword(true);
      console.log(err);
    });
  };

  const createConfig = () => {
    let config = {
      headers: {
        Authorization: tokenType + " " + accessToken
      }
    };
    return config;
  };

  const getVerifiedUser = () => {
    axios.get(`${link}/account/login/get?userName=${userName}&password=${password}`, createConfig()).then(
      res => {
        if (res.data != "") {
          setVerifiedUser(res.data);
        }
        else {
          setBadUsernameOrPassword(true);
        }
      }
    ).catch(err => {console.log(err);});
  };

  const getStateUserFromUser = () => {
    var stateUser = {
      id: verifiedUser.id,
      userName: verifiedUser.userName,
      login: verifiedUser.login,
      email: verifiedUser.email,
      accountPrivacy: verifiedUser.accountPrivacy,
      role: verifiedUser.role,
      postList: verifiedUser.postList,
      tokenType: tokenType,
      accessToken: accessToken
    };
    return stateUser;
  };

  const logUserIn = () => {
    dispatch(login(getStateUserFromUser()));
    history.push("/profile");
  };

  useEffect( () => {
    getVerifiedUser();
  }, [tokenType, accessToken]);

  useEffect( () => {
    if (verifiedUser != "") logUserIn();
  }, [verifiedUser]);

  return (
    <div className="login-page">
      <h2>Login page</h2>
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

        <ExpireWrapper
            className="failure"
            visible={badUsernameOrPassword}
            reset={setBadUsernameOrPassword}
          />

      <Link to={"/register"}>Do not have an account? Register here.</Link>
    </div>
  );
};

const ExpireWrapper = (props) => {
  return (
    <Expire
    visible={props.badUsernameOrPassword}
    delay="3000"
    reset={props.reset}
    children="Bad user name or password."
    {...props}/>
  );
};