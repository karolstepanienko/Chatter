import React from 'react';
import axios from 'axios';

//check useEffect

// Internal imports:
import '../../../css/Pages/Account/Register.css';
import { link, validEmailRegex }from '../../../Constants/Constants';

const linkRegister = `${link}/account/register`;


export default class Register extends React.Component {
  constructor(props) {
    super(props);
    this.loginTaken = false;
    this.emailTaken = false;
    this.malformedLogin = false;
    this.user = {
      id: null,
      login: '',
      login: '',
      email: '',
    }
  }

  handleRegister(evt) {
    evt.preventDefault();
    if(this.loginTaken && this.emailTaken) alert("Login and email already taken.");
    else if(this.loginTaken) alert("Login is already taken.");
    else if(this.emailTaken) alert("Email is already taken.");
    else if(
      this.loginTaken==false
      && this.emailTaken==false
      && this.malformedLogin==false
      && this.malformedEmail==false) {
      axios.post(`${linkRegister}/add/user`, this.user);
    } 
  }

  checkLoginAvailable() {
    axios.post(`${linkRegister}/check/login`, this.user)
    .then(res => { this.loginTaken = res.data });
  }

  checkEmailAvailable() {
    axios.post(`${linkRegister}/check/email`, this.user)
    .then(res => { this.emailTaken = res.data });
  }

  checkLoginFormat() {
    if(this.user.login.length > 3 && this.user.login.length < 20) this.malformedLogin = false;
    else this.malformedLogin = true;
  }

  checkEmailFormat() {
    if(validEmailRegex.test(this.user.email)) this.malformedEmail = false;
    else this.malformedEmail = true;
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

  render() {
    return (
      <div className="register-page">
        <h2 className="register-title">Register to chatter.</h2>
        <form className="form">
          <label className="login" for="login">Login:</label>
          <input 
            className="login"
            type="text"
            name="login"
            placeholder="Your login"
            minLength="3"
            maxLength="20"
            onChange={evt => this.handleLoginChange(evt)}>
          </input>

          <label className="email" for="email">Email:</label>
          <input
            className="email"
            type="email"
            name="email"
            placeholder="Your email"
            minLength="3"
            onChange={evt => this.handleEmailChange(evt)}>
          </input>
          
          <input 
            className="button"
            type="submit"
            value="Register"
            onClick={evt => this.handleRegister(evt)}>
          </input>
        </form>
      </div>
    );
  }
};
