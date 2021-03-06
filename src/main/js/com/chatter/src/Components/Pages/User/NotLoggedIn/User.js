import React from "react";
import "../../../../css/Pages/User/NotLoggedIn/User.css";
import axios from "axios";
import { useEffect, useState } from "react";
import { connect } from "react-redux";
import { useParams, useHistory } from "react-router";

import { link, Privacies } from "../../../../Constants/Constants";
import DisplayUserPosts from "../../Post/DisplayUserPosts";
import DisplayNotLoggedInUserPost from "../../Post/DisplayNotLoggedInUserPost";
import DisplayLogin from "../Profile/DisplayLogin";


const User = (props) => {
  const [user, setUser] = useState("");
  const [userExists, setUserExists] = useState(false);
  const [isLoaded, setIsLoaded] = useState(false);

  let params = useParams();
  let history = useHistory();

  const checkLoggedInUserAccount = () => {
    if (props.userName === params.userName) {
      history.push("/profile");
    }
  };

  const checkUserLoggedIn = () => {
    return props.id != null;
  };

  const checkInputData = () => {
    if (params.userName === undefined) {
      setUserExists(false);
      return false;
    }
    else return true;
  };

  const createConfig = () => {
    let config = {
      headers: {
        Authorization: props.tokenType + " " + props.accessToken
      }
    };
    return config;
  };

  const getUserWithUserName = async () => {
    if (user === "") {
      axios.get(`${link}/account/user/get/user/by/userName?userName=${params.userName}`, createConfig())
      .then(
        res =>{
          if(res.data !== "") {
            setUser(res.data);
            setUserExists(true);      
          }
          else setUserExists(false);
        }
      ).catch(
        err => console.log(err)
      );
    }
  };

  const main = () => {
    checkLoggedInUserAccount();
    if(checkInputData()) {
      getUserWithUserName();
    }
  };

  const getUserInfo = () => {
    return (
      <div className="user-page">
        <h2 className="title-user-page">User page</h2>
        <div className="key">Username:</div>
        <div className = "value">{user.userName}</div>
        <div className="key">Login:</div>
        <div className = "value">
          <DisplayLogin login={user.login}/>
        </div>
        <div className="key">Email: </div>
        <div className = "value">{user.email}</div>
        <DisplayUserPosts 
          display={DisplayNotLoggedInUserPost}
          createConfig={createConfig}
          id={user.id}
          userName={user.userName}/>
      </div>
    );
  };

  const getNotLoggedInUserScreen = () => {
    return (
      <div className="user-page">
        User pages can be seen only by logged in users.
      </div>
    );
  };

  const getUserDoesNotExist = () => {
    return (
      <div className="user-page">
        User does not exist :/
      </div>
    );
  };

  const getLoading = () => {
    return (
      <div className="user-page">
        Loading...
      </div>
    );
  };

  const getAccountPrivate = () => {
    return (
      <div className="user-page">
        <div className="account-private">
          This account is private.
        </div>
      </div>
    );
  };

  const display = () => {
    if (checkUserLoggedIn()) {
      if (userExists) {
        if (isLoaded) {
          if (user.accountPrivacy === Privacies.publicAccess) return getUserInfo();
          else return getAccountPrivate();
        } else return getLoading();
      } else return getUserDoesNotExist();
    } else return getNotLoggedInUserScreen();
  };

  // Runs the given method on page load
  useEffect(() => {
    main();
  }, []);

  // Waits for user load
  useEffect(() => {
    setIsLoaded(true);
  }, [getUserWithUserName]);

  return (display());
};

const mapStateToProps = state => ({
  id: state.user.user.id,
  userName: state.user.user.userName,
  tokenType: state.user.user.tokenType,
  accessToken: state.user.user.accessToken
});

export default connect(mapStateToProps)(User);