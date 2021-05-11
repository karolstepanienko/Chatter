import React, { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import axios from 'axios';

import '../../../css/Pages/User/User.css';
import { link }from '../../../Constants/Constants';
import { connect } from "react-redux";
import { updateLogin } from '../../../State/userSlice';

axios.defaults.baseURL = `${link}/account/user`;
// axios.defaults.headers.common["Access-Control-Allow-Origin"] = 'true';


export const LoggedInUserProfile = (props) => {
  const [isChangingLogin, setIsChangingLogin] = useState(false);

  const dispatch = useDispatch();
  useEffect( () => {}, [dispatch]);


  const handleLoginChangeTextBoxVisibility = () => {
    setIsChangingLogin(!isChangingLogin);
  }


  return (
    <div className="user-page">
      <div className="user-userName">
        <span>Username:</span>
        <span>{props.userName}</span>
      </div>
      <div className="user-email">
        <span>Email:</span>
        <span>{props.email}</span>
      </div>
      <div className="user-login">
        <span>Login:</span>
        <span>{props.login}</span>    
      </div>
      <div className="change-login">
        <LoginChangeTextBox
          isChangingLogin={isChangingLogin}
          dispatch={dispatch}
          handleLoginChangeTextBoxVisibility={handleLoginChangeTextBoxVisibility}
         {...props}/>
      </div>
    </div>
  )
}

const LoginChangeTextBox = (props) => {
  const [newLogin, setNewLogin] = useState("");

  const handleLoginChange = (evt) => {
    evt.preventDefault();
    setNewLogin(evt.target.value);
  }

  const getUserDTO = () => {
    var user = {
      id: props.id,
      userName: props.userName,
      login: newLogin,
      email: props.email,
      password: props.passwordHash,
      role: '',
    }
    return user;
  }

  const handleUserLoginSubmit = () => {
    props.handleLoginChangeTextBoxVisibility();
    console.log(getUserDTO());
    axios.post(`/update/login`, getUserDTO()).then(
      res => {
        console.log(res.data);
        props.dispatch(updateLogin(newLogin));
      }
    ).catch(
      err => console.log(err)
    )
  }

  const chooseRenderObject = () => {
    if (props.isChangingLogin) return textBoxAndSubmitButton();
    else return changeLoginButton();
  }

  const textBoxAndSubmitButton = () => {
    return(
      <div className="text-box-submit-button">
        <input 
          type="text"
          value={newLogin}
          onChange={(evt) => handleLoginChange(evt)}/>
        <input
          type="submit"
          value="Submit login change"
          onClick={handleUserLoginSubmit}/>
      </div>
    )
  }

  const changeLoginButton = () => {
    return(
      <button 
        type="button"
        className="change-login-button"
        value="Change Login"
        onClick={props.handleLoginChangeTextBoxVisibility}>
          Change login
      </button>
    )
  }

  return (
    chooseRenderObject()
  )
}


const mapStateToProps = state => ({
  id: state.user.user.id,
  userName: state.user.user.userName,
  email: state.user.user.email,
  passwordHash: state.user.user.passwordHash,
  login: state.user.user.login,
  role: state.user.user.role,
  postList: state.user.user.postList,
});

export default connect(mapStateToProps)(LoggedInUserProfile);


// export default class Profile extends React.Component{

//   constructor(props) {
//     super(props);
//     this.state = {
//       id: 101,
//       login: [""],
//       logChange:0,
//       newlogin:"",
//       response:""
//     };
//   }

//   Change(){
//     this.setState({logChange:1});
//   }
//   handleLoginChange(evt) {
//     evt.preventDefault();
//     this.state.newlogin = evt.target.value;
//   }
//   handleChange(evt){
//     console.log(this.state.newlogin);
    
//         // Simple POST request with a JSON body using axios
//         const change = { id: this.state.id, login: this.state.newlogin };
//         axios.post(`${link}account/changelogin`, change)
//             .then(response => this.setState({ response: response.data }));
//     console.log(this.state.response);
//     alert("Nazwa zmieniona")
//   }


//   render() {
//     this.getLogin()
//     if (this.state.logChange){
//         return <div className="login-page">
//         <div >
//         <input 
//         className="login"
//         type="text"
//         name="login"
//         placeholder="Your new login"
//         minLength="3"
//         maxLength="20"
//         onChange={evt => this.handleLoginChange(evt)}>
//         </input>
//         <input 
//         className="button"
//         type="submit"
//         value="Change"
//         onClick={evt => this.handleChange(evt)}>
//         </input>
//         </div>
//       </div>
//     }
//     else{
//     return (
//     <div className="login-page">
//       <div className="profile_login" >
//         <div className ="left">Login: </div><div className ="right"> {(this.state.login[0])}</div>
//       </div>
//       <div className="profile_login" >
//         <div className ="left">Login: </div><div className ="right"> {(this.state.login[0])+"sdksajhdskjdhsjdhsjhd"}</div>
//       </div>
//       <input 
//         className="button"
//         type="submit"
//         value="Change Login"
//         onClick={evt => this.Change(evt)}>
//       </input>
      
//     </div>
//     );
    
//   }
// }