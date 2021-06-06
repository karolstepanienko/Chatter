import React from "react";
import PropTypes from "prop-types";
import { withRouter } from "react-router";

const LinkButton = (props) => {
  const {
    history,
    to,
    onClick,
    ...rest
  } = props;
  return (
    <button
      {...rest}
      onClick={(event) => {
        onClick && onClick(event);
        history.push(to);
      }}
    ></button>
  );
};

LinkButton.propTypes = {
  to: PropTypes.string.isRequired,
  children: PropTypes.node.isRequired,
  history: PropTypes.object,
  onClick: PropTypes.func
};

export default withRouter(LinkButton);