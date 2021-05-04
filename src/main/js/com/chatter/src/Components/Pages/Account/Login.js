import axios from 'axios';
import React, { useEffect, useState } from 'react';
import {Link} from 'react-router-dom';
import { Provider } from 'react-redux';

// Project imports:
import { link } from '../../../Constants/Constants';
import { passwordHash } from './PasswordHash';
import '../../../css/Pages/Account/Register.css';

axios.defaults.baseURL = `${link}/account/login`;

export default class Login extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      verifiedUser: null,
    }
    this.user = {
      id: null,
      userName: '',
      email: '',
      passwordHash: '',
      login: '',
    };
  }

  handleUserNameChange(evt) {
    this.user.userName = evt.target.value;
    console.log(this.user.userName);
  }

  handlePasswordChange(evt) {
    if(this.validatePassword(evt.target.value)) this.user.passwordHash = passwordHash(evt.target.value);
    else this.user.passwordHash = '';
  }

  validatePassword(password){
    if(password.length >= 5) return true;
    else return false;
  }

  async getVerifiedUser() {
    await axios.post('/user/get', this.user).then(
      res => {
        if (res.data != "") {
          localStorage.setItem('verifiedUser', res.data);
          // This was executed after the change
          this.setState({verifiedUser: res.data}, () => {this.afterGetUser()});
        }
        else alert("User does not exist.");
      }
    );
  }

  checkAndSetCredentials() {
    if(this.state.verifiedUser.userName == this.user.userName && 
      this.state.verifiedUser.passwordHash == this.user.passwordHash) {
        this.user.id = this.state.verifiedUser.id;
        this.user.email = this.state.verifiedUser.email;  
      }
    else alert("Bad login credentials.")
  }

  afterGetUser() {
    this.checkAndSetCredentials();
    // Redirect
    this.props.history.push({
      pathname: '',
      user: this.user
    });
  }

  handleLoginSubmit(evt) {
    evt.preventDefault();
    this.getVerifiedUser();
    // Here user will not be updated
  }

  render() {
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
              onChange={evt => this.handleUserNameChange(evt)}>
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
  
          <input
            className="button"
            type="submit"
            value="Login"
            onClick={evt => this.handleLoginSubmit(evt)}>
          </input>
        </form>
  
        <Link to={'/register'}>Do not have an account? Register here.</Link>
      </div>
    );
  }
}
