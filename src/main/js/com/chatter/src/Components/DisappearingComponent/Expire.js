import React, { useEffect, useState } from "react";

import '../../css/Expire/Expire.css'

const Expire = props => {
  const [visible, setVisible] = useState(true);

  useEffect(() => {
    setVisible(props.visible);
    setTimeout(() => {
      setVisible(false);
      props.reset();
    }, props.delay);
  }, [props.delay]);

  return visible ? <div 
    className={props.className}
  >
    {props.children}</div> : <div />;
};


export default Expire;