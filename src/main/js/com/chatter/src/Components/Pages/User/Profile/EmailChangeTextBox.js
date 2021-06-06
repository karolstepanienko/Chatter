import React from "react";
import axios from "axios";
import PropTypes from "prop-types";


import { useState, useEffect } from "react";
import { updateEmail } from "../../../../State/userSlice";
import { validEmailRegex } from "../../../../Constants/Constants";
import { link } from "../../../../Constants/Constants";
import ButtonActivatedTextBox from "./ButtonActivatedTextBox";


const EmailChangeTextBox = (props) => {
  const [newEmail, setNewEmail] = useState("");
  const [submit, setSubmit] = useState(false);
  const [emailAvailable, setEmailAvailable] = useState(false);
  const [emailValid, setEmailValid] = useState(false);
  const [confirmation, setConfirmation] = useState({visible: false,
     value: "",
     className: ""
    });


  const handleEmailChange = (evt) => {
    evt.preventDefault();
    setNewEmail(evt.target.value);
    validateEmail();
  };

  const validateEmail = () => {
    if(emailAvailable && emailValid) return true;
    else return false;
  };

  const checkEmailFormat = () => {
    if(validEmailRegex.test(newEmail)) setEmailValid(true);
    else setEmailValid(false);
  };

  const checkEmailAvailable = async () => {
    axios.get(`${link}/account/user/check/email?email=${newEmail}`, props.createConfig())
    .then(res => {setEmailAvailable(res.data); })
    .catch(
      err => console.log(err)
    );
  };

  const getUserDTO = () => {
    var user = {
      id: props.id,
      userName: props.userName,
      login: props.login,
      email: newEmail,
      password: props.passwordHash,
      role: "",
    };
    return user;
  };
  const handleEmailSubmit = () => {
    props.handleTextBoxVisibility();
    checkEmailFormat();
    checkEmailAvailable();
    setSubmit(true);
    // Failure will be overwritten by success
    showFailure();
  };

  const clean = () => {
    setNewEmail("");
    setSubmit(false);
    setEmailAvailable(false);
    setEmailValid(false);
    setConfirmation({visible: false,
      value: "",
      className: ""
    });
  };

  const handleCancel = () => {
    props.handleTextBoxVisibility();
    clean();
  };

  const showFailure = () => {
    setConfirmation({visible: true,
      value: "Email change not successfull.",
      className: "failure"
     });
  };

  const submitNewEmail = async () => {
    axios.post(`${link}/account/user/update/email`, getUserDTO(), props.createConfig()).then(
      res => {
        if(res.data) {
          updateEmailInState();
          setConfirmation({visible: true,
             value: "Email change successfull.",
             className: "success"
            });
        } else showFailure();
      }
    ).catch(
      err => console.log(err)
    );
  };

  const updateEmailInState = () => {
    props.dispatch(updateEmail(newEmail));
  };

  useEffect( () => {
    if(validateEmail() && submit) {
      submitNewEmail();
      clean();
    }

  }, [checkEmailAvailable, handleEmailSubmit]);

  return (
    <div>
      <ButtonActivatedTextBox
        textBoxVisible={props.isChanging}
        elementClassName={props.className}
        textBoxValue={newEmail}
        handleTextChange={handleEmailChange}
        handleCancel={handleCancel}
        confirmation={confirmation}
        resetConfirmation={clean}

        initMessage="Change email"
        submitMessage="Submit email change"
        handleSubmit={handleEmailSubmit}
        {...props}
      />
    </div>
  );
};

EmailChangeTextBox.propTypes = {
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
  isChanging: PropTypes.func,
  className: PropTypes.string
};

export default EmailChangeTextBox;