import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";

const DisplayLogin = (props) => {
  const [loginEmpty, setLoginEmpty] = useState(true);

  const checkLoginEmpty = () => {
    if (props.login === "") setLoginEmpty(true);
    else setLoginEmpty(false);
  };

  useEffect( () => {checkLoginEmpty();}, []);

  useEffect( () => checkLoginEmpty(), [props]);

  return (loginEmpty ? <div>No login set.</div> : <div>{props.login}</div>);
};

DisplayLogin.propTypes = {
  login: PropTypes.string
};

export default DisplayLogin;
