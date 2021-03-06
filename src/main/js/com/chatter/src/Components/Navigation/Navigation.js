import React from "react";

import "../../../node_modules/bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter, Route, Switch } from "react-router-dom";

import "../../css/Navigation/Navigation.css";

import NavigationBar from "./NavigationBar.js";

import { Provider } from "react-redux";
import store from "../../State/Store";
import { Home } from "../Pages/Public/Home";
import { About } from "../Pages/Public/About";
import { NoMatch } from "../Pages/Public/NoMatch";
import { Login } from "../Pages/Account/Login";
import Register from "../Pages/Account/Register";
import ReRoute from "../Navigation/ReRoute";

import User from "../Pages/User/NotLoggedIn/User";
import  Post  from "../Post/Post";
import LoggedInUserProfile from "../Pages/User/Profile/Profile";


const Navigation = () => {
  return(
    <Provider store={store}>
      <React.Fragment>
        <BrowserRouter>
          <NavigationBar />
          <Switch>
            {/* Public routes. */}
            <Route exact path='/' component={Home} />
            <Route path='/login' component={Login} />
            <Route path='/register' component= {Register} />
            <Route path='/about' component={About} />
            <Route path='/user/:userName' children={<User />}/>
            

            {/* User registered routes. */}
            <Route path='/profile' render={ () => 
              <ReRoute component={LoggedInUserProfile} redirectPath='/login' />}/>

            {/* with params it will lead to sites of other users */}
            <Route path='/user' render={ () => 
              <ReRoute component={User} redirectPath='/login' />}/>
            <Route path='/addpost' render={ () => 
              <ReRoute component={Post} redirectPath='/login' />}/>

            {/* Last, default route. */}
            <Route component={NoMatch} />
          </Switch>
        </BrowserRouter>
      </React.Fragment>
    </Provider>
  );
};

export default Navigation;
