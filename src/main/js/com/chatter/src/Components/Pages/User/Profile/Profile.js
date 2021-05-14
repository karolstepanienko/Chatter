import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { useDispatch } from 'react-redux';

import '../../../../css/Pages/User/Profile.css';
import { connect } from "react-redux";
import { logout } from '../../../../State/userSlice';
import ChangePrivacy from '../../../Dropdown/ChangePrivacy';
import EmailChangeTextBox from './EmailChangeTextBox';
import LoginChangeTextBox from './LoginChangeTextBox';
import DisplayLogin from './DisplayLogin';


export const LoggedInUserProfile = (props) => {
  const [isChangingLogin, setIsChangingLogin] = useState(false);
  const [isChangingEmail, setIsChangingEmail] = useState(false);

  const dispatch = useDispatch();
  let history = useHistory();

  const handleLoginChangeTextBoxVisibility = () => {
    setIsChangingLogin(!isChangingLogin);
    console.log(props.accountPrivacy)
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
            className="change-login"
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
            className="change-email"
            isChanging={isChangingEmail}
            dispatch={dispatch}
            handleTextBoxVisibility={handleEmailChangeTextBoxVisibility}
            {...props}/>
        </div>
        
        <div className="user-accountPrivacy">
          <div className="key">Privacy setting:</div>
          <div className = "value">{props.accountPrivacy}</div>
        </div>
        <div className="change-privacy">
          <ChangePrivacy
            className="change-privacy"
            dispatch={dispatch}
            {...props}/>
        </div>

        <button className="logout"
          onClick={handleLogout}>
        Logout</button>

      </div>
    </div>
  )
}

const UserNameChangeTextBox = (props) => {

}

const mapStateToProps = state => ({
  id: state.user.user.id,
  userName: state.user.user.userName,
  login: state.user.user.login,
  email: state.user.user.email,
  accountPrivacy: state.user.user.accountPrivacy,
  role: state.user.user.role,
  postList: state.user.user.postList,
});

export default connect(mapStateToProps)(LoggedInUserProfile);
