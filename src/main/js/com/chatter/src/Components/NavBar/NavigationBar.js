import React from 'react';

import '../../css/NavBar/NavBar.css';

import { Link } from 'react-router-dom';
import { Links } from '../../Components/Navigation/Links';
import { Nav, Navbar, Form, FormControl } from 'react-bootstrap';

export default class NavigationBar extends React.Component {
  render() {
    return(
      <Navbar expand="lg">
      <Link className="navbar-brand" to={'/'} >Chatter</Link>
      <Navbar.Toggle aria-controls="basic-navbar-nav"/>
      <Form className="form-center">
        <FormControl type="text" placeholder="Search" className="searchBar" />
      </Form>
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="ml-auto">
          {/* <Links /> */}
          <Link to={'/user'} className="login-link">User</Link>
          <Link to={'/login'} className="login-link">Login</Link>
          <Link to={'/about'} className="about-link">About</Link>
        </Nav>
      </Navbar.Collapse>
    </Navbar>  
    );
  }


}