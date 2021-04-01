import React from 'react';

import '../../css/NavBar/NavBar.css';

import { Nav, Navbar, Form, FormControl } from 'react-bootstrap';

export default class NavigationBar extends React.Component {
  render() {
    return(
      <Navbar expand="lg">
      <Navbar.Brand className="navbar-brand" href="/">Chatter</Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav"/>
      <Form className="form-center">
        <FormControl type="text" placeholder="Search" className="searchBar" />
      </Form>
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="ml-auto">
          <Nav.Item><Nav.Link href="/about">About</Nav.Link></Nav.Item>
        </Nav>
      </Navbar.Collapse>
    </Navbar>  
    );
  }


}