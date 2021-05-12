import '../../../../css/Pages/User/NotLoggedIn/User.css';
import axios from 'axios';
import { useEffect, useState } from "react";
import { link, roles } from '../../../../Constants/Constants';
import { useParams } from 'react-router';

axios.defaults.baseURL = `${link}/account/user`;


const User = () => {
  const [user, setUser] = useState("");
  const [userExists, setUserExists] = useState(false);
  const [isLoaded, setIsLoaded] = useState(false);

  let params = useParams();

  const checkInputData = () => {
    if (params.userName === undefined) {
      setUserExists(false);
      return false;
    }
    else return true;
  }

  const getUserWithUserName = async () => {
    if (user === "") {
      axios.get(`/get/user/by/userName?userName=${params.userName}`)
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
      )
    }
  }

  const main = () => {
    if(checkInputData()) {
      getUserWithUserName();
    }
  }

  const getUserInfo = () => {
    return (
      <div className="user-page">
        <div className="user-userName">
          <span>Username:</span>
          <span>{user.userName}</span>
        </div>
        <div className="user-login">
          <span>Login:</span>
          <span>{user.login}</span>
        </div>
        <div className="user-email">
          <span>Email:</span>
          <span>{user.email}</span>
        </div>
      </div>
    )
  }

  const getUserDoesNotExist = () => {
    return (
      <div className="user-page">
        User does not exist :/
      </div>
    )
  }

  const getLoading = () => {
    return (
      <div className="user-page">
        Loading...
      </div>
    )
  }

  const display = () => {
    if (userExists) {
      if (isLoaded) return getUserInfo();
      else return getLoading();
    } else return getUserDoesNotExist();
  }

  // Runs the given method on page load
  useEffect(() => {
    main();
  }, [])

  // Waits for user load
  useEffect(() => {
    setIsLoaded(true);
  }, [getUserWithUserName])

  return (display())
}

export default User;