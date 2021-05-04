import React from 'react';
import axios from 'axios';
import {Link} from 'react-router-dom';

// Internal imports:
import '../../../css/Pages/Account/Register.css';
import { link, validEmailRegex } from '../../../Constants/Constants';
import { passwordHash } from './PasswordHash';

const linkRegister = `${link}/account/register`;

export default class Register extends React.Component {
  constructor(props) {
    super(props);
    this.userNameFree = true;
    this.emailFree = true;
    this.malformedUserName = false;
    this.passwordHash = '';
    this.passwordHashRepeat = '';
    this.user = {
      id: null,
      userName: '',
      email: '',
      passwordHash: '',
      login: '',
    }
  }

  handleRegister(evt) {
    evt.preventDefault();
    if(this.checkUserNameAvailable() == false && this.checkEmailAvailable() == false)
      alert("Username and email already taken.");
    else if(this.checkUserNameAvailable() == false) alert("Username is already taken.");
    else if(this.checkEmailAvailable() == false) alert("Email is already taken.");
    else if(this.equalPasswordHashes() == false) alert("Insufficient or unequal passwords.");
    else if(
      this.validateEmail() &&
      this.validateUserName() &&
      this.equalPasswordHashes()) {
        axios.post(`${linkRegister}/add/user`, this.user);
    } else alert("Unknown error.");
  }

  validateUserName() {
    if(this.checkUserNameAvailable() && this.checkUserNameFormat()) return true;
    else return false;
  }

  validateEmail(){
    if(this.checkEmailAvailable() && this.checkEmailFormat()) return true;
    else return false;
  }

  checkUserNameAvailable() {
    axios.post(`${linkRegister}/check/username`, this.user)
    .then(res => { this.userNameFree = res.data });
    return this.userNameFree;
  }

  checkEmailAvailable() {
    axios.post(`${linkRegister}/check/email`, this.user)
    .then(res => { this.emailFree = res.data });
    return this.emailFree;
  }

  checkUserNameFormat() {
    if(this.user.userName.length > 3 && this.user.userName.length < 20) this.malformedUserName = false;
    else this.malformedUserName = true;
    return this.malformedUserName==false;
  }

  checkEmailFormat() {
    if(validEmailRegex.test(this.user.email)) this.malformedEmail = false;
    else this.malformedEmail = true;
    return this.malformedEmail==false;
  }

  handleUserNameChange(evt) {
    evt.preventDefault();
    this.user.userName = evt.target.value;
    this.checkUserNameFormat();
    if(this.malformedUserName==false) this.checkUserNameAvailable();
  }

  handleEmailChange(evt) {
    evt.preventDefault();
    this.user.email = evt.target.value;
    this.checkEmailFormat();
    if(this.malformedEmail==false) this.checkEmailAvailable();
  }

  handlePasswordChange(evt) {
    this.passwordHash = passwordHash(evt.target.value);
  }

  handlePasswordRepeatChange(evt) {
    this.passwordHashRepeat = passwordHash(evt.target.value);
  }

  equalPasswordHashes() {
    if(this.passwordHash == this.passwordHashRepeat &&
      this.passwordHash != '' &&
      this.passwordHashRepeat != ''
      ) {
        this.user.passwordHash = this.passwordHash;
        return true;
      }
    else return false;
  }

  render() {
    return (
      <div className="register-page">
        <h2 className="register-title">Register to chatter</h2>
        <form className="form">
          <label className="UserName">Username:</label>
          <input 
            className="UserName"
            type="text"
            name="UserName"
            placeholder="Your Username"
            minLength="3"
            maxLength="20"
            onChange={evt => this.handleUserNameChange(evt)}>
          </input>

          <label className="email">Email:</label>
          <input
            className="email"
            type="email"
            name="email"
            placeholder="Your email"
            minLength="3"
            onChange={evt => this.handleEmailChange(evt)}>
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

          <label className="password-repeat">Repeat password:</label>
          <input
            className="password-repeat"
            type="password"
            name="password-repeat"
            placeholder="Your password"
            minLength="5"
            onChange={evt => this.handlePasswordRepeatChange(evt)}>
          </input>
          
          <input 
            className="button"
            type="submit"
            value="Register"
            onClick={evt => this.handleRegister(evt)}>
          </input>
        </form>
        
        <Link to={'/login'}>Or login instead.</Link>
      </div>
    );
  }
};
