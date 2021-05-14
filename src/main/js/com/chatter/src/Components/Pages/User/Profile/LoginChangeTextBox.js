import axios from 'axios';

import { useState } from 'react';
import { updateLogin } from '../../../../State/userSlice';
import { link } from '../../../../Constants/Constants';
import ButtonActivatedTextBox from './ButtonActivatedTextBox';


axios.defaults.baseURL = `${link}/account/user`;


const LoginChangeTextBox = (props) => {
  const [newLogin, setNewLogin] = useState("");
  const [confirmation, setConfirmation] = useState({visible: false,
      value: "",
      className: ""
    });

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
        if(res.data) {
          props.dispatch(updateLogin(newLogin));
          setConfirmation({visible: true,
             value: "Login change successfull.",
             className: "success"
            });
        }

      }
    ).catch(
      err => console.log(err)
    )
  }

  const clean = (props) => {
    setNewLogin("");
    setConfirmation({visible: false,
      value: "",
      className: ""});
  }

  const handleCancel = () => {
    props.handleTextBoxVisibility();
    clean();
  }

  return (
    <ButtonActivatedTextBox
      textBoxVisible={props.isChanging}
      elementClassName="change-login"
      textBoxValue={newLogin}
      handleTextChange={handleLoginChange}
      handleCancel={handleCancel}
      confirmation={confirmation}
      resetConfirmation={clean}

      initMessage="Change login"
      submitMessage="Submit login change"
      handleSubmit={handleLoginSubmit}
      {...props}
    />
    
  )
}

export default LoginChangeTextBox;