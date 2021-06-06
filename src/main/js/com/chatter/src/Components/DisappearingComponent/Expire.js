import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";

import "../../css/Expire/Expire.css";

const Expire = props => {
  const [visible, setVisible] = useState(true);

  useEffect(() => {
    setVisible(props.visible);
    setTimeout(() => {
      setVisible(false);
      props.reset(false);
    }, props.delay);
  }, [props.delay, props]);

  return visible ? <div 
    className={props.className}
  >
    {props.children}</div> : <div />;
};

Expire.propType = {
  badUsernameOrPassword: PropTypes.func,
  reset: PropTypes.func,
  delay: PropTypes.number,
  visible: PropTypes.number,
  className: PropTypes.string,
  children: PropTypes.object
};

export default Expire;