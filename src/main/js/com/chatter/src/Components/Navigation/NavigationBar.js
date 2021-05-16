import React from 'react';

import '../../css/Navigation/NavBar.css';

import { Link } from 'react-router-dom';
import { Nav, Navbar, Form, FormControl } from 'react-bootstrap';

import ProfileLink from '../ProfileLink/ProfileLink';

const NavigationBar = () => {
  return(
    <Navbar expand="lg">
    <Link className="navbar-brand" to={'/'} >Chatter</Link>
    <Navbar.Toggle aria-controls="basic-navbar-nav"/>
    <Form className="form-center">
      {/* <FormControl type="text" placeholder="Search" className="searchBar" /> */}
    </Form>
    <Navbar.Collapse id="basic-navbar-nav">
      <Nav className="ml-auto">
        <ProfileLink component={Link} message="Profile" className="link"/>
        <Link to={'/login'} className="login-link">Login</Link>
        <Link to={'/about'} className="about-link">About</Link>
      </Nav>
    </Navbar.Collapse>
  </Navbar>  
  );
}

export default NavigationBar;