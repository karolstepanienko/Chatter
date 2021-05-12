import React, { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import axios from 'axios';

import '../../../css/Pages/User/User.css';
import { link, validEmailRegex }from '../../../Constants/Constants';
import { connect } from "react-redux";
import { updateLogin } from '../../../State/userSlice';

axios.defaults.baseURL = `${link}/account/user`;


export const LoggedInUserProfile = (props) => {
  const [isChangingLogin, setIsChangingLogin] = useState(false);
  const [isChangingEmail, setIsChangingEmail] = useState(false);


  const dispatch = useDispatch();
  useEffect( () => {}, [dispatch]);


  const handleLoginChangeTextBoxVisibility = () => {
    setIsChangingLogin(!isChangingLogin);
  }

  const handleEmailChangeTextBoxVisibility = () => {
    setIsChangingEmail(!isChangingEmail);
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
          isChanging={isChangingLogin}
          dispatch={dispatch}
          handleTextBoxVisibility={handleLoginChangeTextBoxVisibility}
         {...props}/>

         <EmailChangeTextBox
          isChanging={isChangingEmail}
          dispatch={dispatch}
          handleTextBoxVisibility={handleEmailChangeTextBoxVisibility}
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

  const handleLoginSubmit = () => {
    props.handleTextBoxVisibility();
    axios.post(`/update/login`, getUserDTO()).then(
      res => {
        if(res.data) props.dispatch(updateLogin(newLogin));
      }
    ).catch(
      err => console.log(err)
    )
  }

  return (
    <ButtonActivatedTextBox
      textBoxVisible={props.isChanging}
      elementClassName="change-login"
      textBoxValue={newLogin}
      handleTextChange={handleLoginChange}

      initMessage="Change login"
      submitMessage="Submit login change"
      handleSubmit={handleLoginSubmit}
      {...props}
    />
    
  )
}

const EmailChangeTextBox = (props) => {
  const [newEmail, setNewEmail] = useState("");
  const [submit, setSubmit] = useState(false);
  const [emailAvailable, setEmailAvailable] = useState(false);
  const [emailValid, setEmailValid] = useState(false);

  const handleEmailChange = (evt) => {
    evt.preventDefault();
    setNewEmail(evt.target.value);
    validateEmail();
  }

  const validateEmail = () => {
    if(emailAvailable && emailValid) return true;
    else return false;
  }

  const checkEmailFormat = () => {
    if(validEmailRegex.test(newEmail)) setEmailValid(true);
    else setEmailValid(false);
  }

  const checkEmailAvailable = async () => {
    axios.get(`/check/email?email=${newEmail}`)
    .then(res => { setEmailAvailable(res.data) })
    .catch(
      err => console.log(err)
    )
  }

  const getUserDTO = () => {
    var user = {
      id: props.id,
      userName: props.userName,
      login: props.login,
      email: newEmail,
      password: props.passwordHash,
      role: '',
    }
    return user;
  }
  const handleEmailSubmit = () => {
    props.handleTextBoxVisibility();
    checkEmailFormat();
    checkEmailAvailable();
    setSubmit(true);
  }

  useEffect( () => {
    // Here the Email is set
    if(validateEmail() && submit) {
      console.log("submitted:")
      console.log(validateEmail());
      console.log("this would be subbmitted");
      console.log(newEmail);
      setEmailValid(false);
      setEmailAvailable(false);
      setSubmit(false);
      setNewEmail("");
    }

  }, [checkEmailAvailable, handleEmailSubmit]);

  return (
    <ButtonActivatedTextBox
      textBoxVisible={props.isChanging}
      elementClassName="change-email"
      textBoxValue={newEmail}
      handleTextChange={handleEmailChange}

      initMessage="Change email"
      submitMessage="Submit email change"
      handleSubmit={handleEmailSubmit}
      {...props}
    />
)


}

export const ButtonActivatedTextBox = (props) => {
  const chooseRenderObject = () => {
    if (props.textBoxVisible) return textBoxAndSubmitButton();
    else return changeInitButton();
  }

  const textBoxAndSubmitButton = () => {
    return(
      <div className={props.elementClassName}>
        <input 
          type="text"
          value={props.textBoxValue}
          onChange={(evt) => props.handleTextChange(evt)}/>
        <input
          type="submit"
          value={props.submitMessage}
          onClick={props.handleSubmit}/>
      </div>
    )
  }

  const changeInitButton = () => {
    return(
      <div className={props.elementClassName}>
        <button 
          type="button"
          onClick={props.handleTextBoxVisibility}>
            {props.initMessage}
        </button>
      </div>
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