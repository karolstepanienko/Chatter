import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { useDispatch } from 'react-redux';

import '../../../../css/Pages/User/Profile.css';
import { connect } from "react-redux";
import { logout } from '../../../../State/userSlice';
import ChangePrivacy from '../../../Dropdown/ChangePrivacy';
import EmailChangeTextBox from './EmailChangeTextBox';
import LoginChangeTextBox from './LoginChangeTextBox';


export const LoggedInUserProfile = (props) => {
  const [isChangingLogin, setIsChangingLogin] = useState(false);
  const [isChangingEmail, setIsChangingEmail] = useState(false);

  const dispatch = useDispatch();
  let history = useHistory();

  const handleLoginChangeTextBoxVisibility = () => {
    setIsChangingLogin(!isChangingLogin);
  }

  const handleEmailChangeTextBoxVisibility = () => {
    setIsChangingEmail(!isChangingEmail);
  }

  const handleLogout = () => {
    dispatch(logout());
    history.push('/');
  }

  useEffect( () => {}, [dispatch]);

  return (
    <div className="user-page">
      <span className="title"> Your profile page </span>
      <div className="flex-container">

        <div className="user-userName">
          <div className="key">Username:</div>
          <div className = "value">{props.userName}</div>
        </div>
        <div className="change-userName"></div>

        <div className="user-login">
          <div className="key">Login:</div>
          <div className="value"><DisplayLogin login={props.login} /></div>   
        </div>
        <div className="change-login">
          <LoginChangeTextBox
            isChanging={isChangingLogin}
            dispatch={dispatch}
            handleTextBoxVisibility={handleLoginChangeTextBoxVisibility}
          {...props}/>
        </div>

        <div className="user-userEmail">
          <div className="key">Email:</div>
          <div className = "value">{props.email}</div>
        </div>
        <div className="change-email">
          <EmailChangeTextBox
            isChanging={isChangingEmail}
            dispatch={dispatch}
            handleTextBoxVisibility={handleEmailChangeTextBoxVisibility}
            {...props}/>
        </div>

        <ChangePrivacy className="changePrivacy"/>

        <button className="logout"
          onClick={handleLogout}>
        Logout</button>

      </div>
    </div>
  )
}

const UserNameChangeTextBox = (props) => {

}

const DisplayLogin = (props) => {
  const [loginEmpty, setLoginEmpty] = useState(true);

  const checkLoginEmpty = () => {
    if (props.login === "") setLoginEmpty(true);
    else setLoginEmpty(false);
  }

  useEffect( () => checkLoginEmpty(), [props]);

  return (loginEmpty ? <div>No login set.</div> : <div>{props.login}</div>)
}

const mapStateToProps = state => ({
  id: state.user.user.id,
  userName: state.user.user.userName,
  email: state.user.user.email,
  passwordHash: state.user.user.passwordHash,
  login: state.user.user.login,
  role: state.user.user.role,
  postList: state.user.user.postList,
});

export default connect(mapStateToProps)(LoggedInUserProfile);
