import React from 'react';
import axios from 'axios';

// Internal imports:
import '../../../css/Pages/Account/Register.css';
import { link }from '../../../Constants/Constants';

const linkRegister = `${link}/account/register`;

export default class Register extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      id: null,
      name: '',
      login: '',
      email: '',
    },
    // Methods:
    this.handleLoginChange = this.handleLoginChange.bind(this);
    this.handleEmailChange = this.handleEmailChange.bind(this);
    this.handleRegister = this.handleRegister.bind(this);

    this.loginTaken = false;
    this.emailTaken = false;
  }

  handleRegister = event => {
    event.preventDefault();

    this.checkLogin();
    this.checkEmail();
    
    console.log(this.loginTaken);
    console.log(this.state.login);
    console.log(this.emailTaken);
    console.log(this.state.email);

    if(this.loginTaken==false && this.emailTaken==false) {
      axios.post(`${linkRegister}/add/user`, this.state);
    } 
    if(this.loginTaken) alert("Login is already taken.");
    if(this.emailTaken) alert("Email is already taken.");
  }

  checkLogin() {
    axios.post(`${linkRegister}/check/login`, this.state)
    .then(res => {
      if(res.data) {
        this.loginTaken = true;
      } else this.loginTaken = false;
    });
  }

  checkEmail() {
    axios.post(`${linkRegister}/check/email`, this.state)
    .then(res => {
      if(res.data) {
        this.emailTaken = true;
      } else this.emailTaken = false;
    });
  }

  handleLoginChange = event => {
    event.preventDefault();
    this.setState({login: event.target.value});
    this.checkLogin();
  }

  handleEmailChange = event => {
    event.preventDefault();
    this.setState({email: event.target.value});
    this.checkEmail();
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
            value={this.state.login}
            onChange={this.handleLoginChange}></input>

          <label className="email">Email:</label>
          <input
            className="email"
            type="text"
            name="email"
            placeholder="Your email"
            value={this.state.email}
            onChange={this.handleEmailChange}></input>
          
          <input 
          className="button"
          type="submit"
          value="Register"
          onClick={this.handleRegister}></input>
        </form>
      </div>
    );
  }
};