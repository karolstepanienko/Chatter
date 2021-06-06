import React from "react";
import { Redirect } from "react-router";
import { connect } from "react-redux";
import PropTypes from "prop-types";


const ReRoute = (props) => {
  return ( props.id === null ? (
      <Redirect
        to={{pathname: props.redirectPath}}
      />
    ) : (
      <props.component />
    )
  );
};

ReRoute.PropTypes = {
  id: PropTypes.number,
  redirectPath: PropTypes.string
};

const mapStateToProps = state => ({
  id: state.user.user.id
});

export default connect(mapStateToProps)(ReRoute);