import React from 'react';
import ReactDOM from 'react-dom';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";

import './css/index.css'

import NavigationBar from './Components/NavBar/NavigationBar.js';
import { Home } from './Components/Pages/Home';
import { About } from './Components/Pages/About';
import { NoMatch } from './Components/Pages/NoMatch';
import { Login } from './Components/Pages/Account/Login';
import Register from './Components/Pages/Account/Register';

ReactDOM.render(
  <React.Fragment>
    <Router>
      <NavigationBar />
      <Switch>
        <Route exact path="/" component={Home} />
        <Route path="/login" component= {Login} />
        <Route path="/register" component= {Register} />
        <Route path="/about" component={About} />
        <Route component={NoMatch} />
      </Switch>
    </Router>
  </React.Fragment>,
  document.getElementById('root'),
);
