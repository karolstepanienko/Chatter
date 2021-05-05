import { useSelector } from 'react-redux';
import { Redirect, Route, Switch } from "react-router-dom";

// Project imports:
import {selectUser} from '../../State/userSlice';
import { Home } from '../Pages/Public/Home';
import { About } from '../Pages/Public/About';
import { NoMatch } from '../Pages/Public/NoMatch';
import { Login } from '../Pages/Account/Login';
import { User } from '../Pages/User';
import Register from '../Pages/Account/Register';
import { useEffect, useState } from 'react';


export const Links = () => {
  const [isAuthenticated, userHasAuthenticated] = useState(false);
  const user = useSelector(selectUser);

  useEffect(() => {
    onLoad();
  }, []);

  async function onLoad() {
    checkAuthentication();
    console.log(isAuthenticated);
  }

  const checkAuthentication = () => {
    if (user.id == null) userHasAuthenticated(false);
    else userHasAuthenticated(true);
  }

  return (
    <Switch>
      <Route exact path="/" component={Home} />
      <AuthenticatedRoute path="/user"
       component={User} appProps={{isAuthenticated}}/>
      <UnAuthenticatedRoute path="/login"
       component={Login} appProps={{isAuthenticated}}/>
      <Route path="/register" component= {Register} />
      <Route path="/about" component={About} />
      <Route component={NoMatch} />
    </Switch>
  )
}

export function AuthenticatedRoute({component: C, appProps, ...rest}) {
  return (
    <Route
      {...rest}
      render={ props =>
        appProps.isAuthenticated
        ? <C {...props} {...appProps}/>
        : <Redirect to="/login"/>
      }
    />
  );
}

export function UnAuthenticatedRoute({component: C, appProps, ...rest}) {
  return (
    <Route
      {...rest}
      render={ props =>
        !appProps.isAuthenticated
        ? <C {...props} {...appProps}/>
        : <Redirect to="/"/>
      }
    />
  );
}
