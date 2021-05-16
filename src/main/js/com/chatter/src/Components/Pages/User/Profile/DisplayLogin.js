import { useEffect, useState } from 'react';

const DisplayLogin = (props) => {
  const [loginEmpty, setLoginEmpty] = useState(true);

  const checkLoginEmpty = () => {
    if (props.login === "") setLoginEmpty(true);
    else setLoginEmpty(false);
  }

  useEffect( () => {checkLoginEmpty()}, [])

  useEffect( () => checkLoginEmpty(), [props])

  return (loginEmpty ? <div>No login set.</div> : <div>{props.login}</div>)
}

export default DisplayLogin;
