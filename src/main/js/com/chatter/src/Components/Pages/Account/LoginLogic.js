import React from 'react';

// Project imports:
import Login from './Login';
import { passwordHash } from './PasswordHash';

export default class LoginLogic {
  constructor() {
    this.user = {
      id: null,
      login: '',
      email: '',
      passwordHash: '',
    };
  }

  handleLoginChange(evt) {
    this.user.login = evt.target.value;
    console.log(this.user.login);
  }

  handlePasswordChange(evt) {
    if(this.checkPassword(evt.target.value)) this.user.passwordHash = passwordHash(evt.target.value);
    console.log(this.user.passwordHash);
  }

  checkPassword(password){
    if(password.length >= 5) return true;
    else return false;
  }

  handleLoginSubmit(evt) {
    console.log("Check login.");
  }

}