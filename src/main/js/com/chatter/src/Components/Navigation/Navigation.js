import React from 'react';

import '../../../node_modules/bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter, Route, Switch } from "react-router-dom";

import '../../css/Navigation/Navigation.css'

import NavigationBar from './NavigationBar.js';

import { Provider } from 'react-redux';
import store from '../../State/Store';
import { Home } from '../Pages/Public/Home';
import { About } from '../Pages/Public/About';
import { NoMatch } from '../Pages/Public/NoMatch';
import { Login } from '../Pages/Account/Login';
import Register from '../Pages/Account/Register';
import UserRoute from '../Pages/User/UserRoute';

const Navigation = () => {
  return(
    <Provider store={store}>
      <React.Fragment>
        <BrowserRouter>
          <NavigationBar />
          <Switch>
            <Route exact path="/" component={Home} />
            <Route path="/user" component={UserRoute} />
            <Route path="/login" component={Login} />
            <Route path="/register" component= {Register} />
            <Route path="/about" component={About} />
            <Route component={NoMatch} />
          </Switch>
        </BrowserRouter>
      </React.Fragment>
    </Provider>
  )
}

export default Navigation;
