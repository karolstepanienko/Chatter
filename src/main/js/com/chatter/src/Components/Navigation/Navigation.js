import React from 'react';

import '../../../node_modules/bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter, Route, Switch } from "react-router-dom";

import '../../css/Navigation/Navigation.css'

import NavigationBar from '../NavBar/NavigationBar.js';

import { Provider } from 'react-redux';
import store from '../../State/Store';
import { Links } from './Links';

export default class Navigation extends React.Component {
  render() {
    return(
      <Provider store={store}>
        <React.Fragment>
          <BrowserRouter>
            <NavigationBar />
              <Links></Links>
          </BrowserRouter>
        </React.Fragment>
      </Provider>
    );
  }

}
