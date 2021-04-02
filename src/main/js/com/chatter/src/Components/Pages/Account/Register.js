import React from 'react';
import axios from 'axios';

// Internal imports:
import '../../../css/Pages/Account/Register.css';

const backend = React.createContext('http://localhost:8080/api/account');

export default class Register extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      id: null,
      name: '',
      login: '',
      email: '',
    }
  }

  handleRegister = event => {
    event.preventDefault();

    const user = {
      name: this.state.login,
      email: this.state.email
    };

    axios.post(backend + '/register', user)
      .then(res => {
        console.log(res);
      });
  }

  handleLoginChange = event => {
    this.setState({ login: event.target.value });
    console.log(this.state.login);
    const user = {
      id: this.state.id,
      name: this.state.login,
      login: this.state.login,
      email: this.state.email,
    }
    axios.post('http://localhost:8080/api/account/register/check/login', user)
      .then(res => {
        if(res.data) alert("Login taken.");
    });
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
            onChange={this.handleLoginChange}></input>

          <label className="email">Email:</label>
          <input
            className="email"
            type="text"
            name="email"
            placeholder="Your email"
            onChange={this.handleEmailChange}></input>
          <input className="button" type="submit" value="Register"></input>
        </form>
      </div>
    );
  }
};