import React from "react";
import { Redirect } from "react-router";
import { connect } from 'react-redux';


const ReRoute = (props) => {
  return ( props.id === null ? (
      <Redirect
        to={{pathname: props.redirectPath}}
      />
    ) : (
      <props.component />
    )
  )
}

const mapStateToProps = state => ({
  id: state.user.user.id
});

export default connect(mapStateToProps)(ReRoute);