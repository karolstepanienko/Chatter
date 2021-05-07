import React from "react";
import { Redirect } from "react-router";
import { connect } from 'react-redux';

import User from "./User";

const UserRoute = (props) => {
  return ( props.id === null ? (
      <Redirect
        push
        to={{pathname:'/login'}}
      />
    ) : (
      <User/>
    )
  )
}

const mapStateToProps = state => ({
  id: state.user.user.id
});

export default connect(mapStateToProps)(UserRoute);