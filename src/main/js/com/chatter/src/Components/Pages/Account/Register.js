import React from 'react';
import axios from 'axios';

// Internal imports:
import '../../../css/Pages/Account/Register.css';
import { link, validEmailRegex }from '../../../Constants/Constants';
import { passwordHash } from './PasswordHash';

const linkRegister = `${link}/account/register`;

export default class Register extends React.Component {
  constructor(props) {
    super(props);
    this.loginTaken = false;
    this.emailTaken = false;
    this.malformedLogin = false;
    this.passwordHash = '';
    this.passwordHashRepeat = '';
    this.user = {
      id: null,
      login: '',
      login: '',
      email: '',
      passwordHash: '',
    }
  }

  handleRegister(evt) {
    evt.preventDefault();
    if(this.checkLoginAvailable() == false && this.checkEmailAvailable() == false)
      alert("Login and email already taken.");
    else if(this.checkLoginAvailable() == false) alert("Login is already taken.");
    else if(this.checkEmailAvailable() == false) alert("Email is already taken.");
    else if(this.equalPasswordHashes() == false) alert("Passwords do not match.");
    else if(
      this.validateEmail() &&
      this.validateLogin() &&
      this.equalPasswordHashes()) {
        axios.post(`${linkRegister}/add/user`, this.user);
    } else alert("Unknown error.")
  }

  validateLogin() {
    if(this.checkLoginAvailable() && this.checkLoginFormat()) return true;
    else return false;
  }

  validateEmail(){
    if(this.checkEmailAvailable() && this.checkEmailFormat()) return true;
    else return false;
  }

  checkLoginAvailable() {
    axios.post(`${linkRegister}/check/login`, this.user)
    .then(res => { this.loginTaken = res.data });
    return this.loginTaken==false;
  }

  checkEmailAvailable() {
    axios.post(`${linkRegister}/check/email`, this.user)
    .then(res => { this.emailTaken = res.data });
    return this.emailTaken==false;
  }

  checkLoginFormat() {
    if(this.user.login.length > 3 && this.user.login.length < 20) this.malformedLogin = false;
    else this.malformedLogin = true;
    return this.malformedLogin==false;
  }

  checkEmailFormat() {
    if(validEmailRegex.test(this.user.email)) this.malformedEmail = false;
    else this.malformedEmail = true;
    return this.malformedEmail==false;
  }

  handleLoginChange(evt) {
    evt.preventDefault();
    this.user.login = evt.target.value;
    this.checkLoginFormat();
    if(this.malformedLogin==false) this.checkLoginAvailable();
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
        <h2 className="register-title">Register to chatter.</h2>
        <form className="form">

          <label className="login">Login:</label>
          <input 
            className="login"
            type="text"
            name="login"
            placeholder="Your login"
            minLength="3"
            maxLength="20"
            onChange={evt => this.handleLoginChange(evt)}>
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
        <a href="/login">Or login instead.</a>
      </div>
    );
  }
};
