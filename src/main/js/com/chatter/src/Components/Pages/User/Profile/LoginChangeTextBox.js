import React from "react";
import axios from "axios";
import PropTypes from "prop-types";

import { useState } from "react";
import { updateLogin } from "../../../../State/userSlice";
import { link } from "../../../../Constants/Constants";
import ButtonActivatedTextBox from "./ButtonActivatedTextBox";


const LoginChangeTextBox = (props) => {
  const [newLogin, setNewLogin] = useState("");
  const [confirmation, setConfirmation] = useState({visible: false,
      value: "",
      className: ""
    });

  const handleLoginChange = (evt) => {
    evt.preventDefault();
    setNewLogin(evt.target.value);
  };

  const getUserDTO = () => {
    var userDTO = {
      id: props.id,
      userName: props.userName,
      login: newLogin,
      email: props.email,
      password: props.passwordHash,
      role: "",
    };
    return userDTO;
  };

  const handleLoginSubmit = () => {
    props.handleTextBoxVisibility();
    axios.post(`${link}/account/user/update/login`, getUserDTO(), props.createConfig()).then(
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
    );
  };

  const clean = () => {
    setNewLogin("");
    setConfirmation({visible: false,
      value: "",
      className: ""});
  };

  const handleCancel = () => {
    props.handleTextBoxVisibility();
    clean();
  };

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
    
  );
};

LoginChangeTextBox.propTypes = {
  id: PropTypes.number,
  userName: PropTypes.string,
  login: PropTypes.string,
  passwordHash: PropTypes.string,
  handleTextBoxVisibility: PropTypes.func,
  email: PropTypes.string,
  accountPrivacy: PropTypes.string,
  tokenType: PropTypes.string,
  accessToken: PropTypes.string,
  createConfig: PropTypes.func,
  dispatch: PropTypes.func,
  isChanging: PropTypes.func
};


export default LoginChangeTextBox;