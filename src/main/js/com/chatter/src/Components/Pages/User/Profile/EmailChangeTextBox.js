import axios from 'axios';

import { useState, useEffect } from 'react';
import { updateEmail } from '../../../../State/userSlice';
import { validEmailRegex } from '../../../../Constants/Constants';
import { link } from '../../../../Constants/Constants';
import ButtonActivatedTextBox from './ButtonActivatedTextBox';

axios.defaults.baseURL = `${link}/account/user`;


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
    .then(res => {setEmailAvailable(res.data) })
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
    // Failure will be overwritten by success
    showFailure();
  }

  const clean = (props) => {
    setNewEmail("");
    setSubmit(false);
    setEmailAvailable(false);
    setEmailValid(false);
    setConfirmation({visible: false,
      value: "",
      className: ""
    });
  }

  const handleCancel = () => {
    props.handleTextBoxVisibility();
    clean();
  }

  const showFailure = () => {
    setConfirmation({visible: true,
      value: "Email change not successfull.",
      className: "failure"
     });
  }

  const submitNewEmail = async () => {
    axios.post('/update/email', getUserDTO()).then(
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
  }

  const updateEmailInState = () => {
    props.dispatch(updateEmail(newEmail));
  }

  useEffect( () => {
    // Here the Email is set
    if(validateEmail() && submit) {
      console.log("submitted:")
      console.log(validateEmail());
      console.log("this would be subbmitted");
      console.log(newEmail);
      submitNewEmail();
      clean();
    }

  }, [checkEmailAvailable, handleEmailSubmit]);

  return (
    <div>
      <ButtonActivatedTextBox
        textBoxVisible={props.isChanging}
        elementClassName="change-email"
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
  )
}

export default EmailChangeTextBox;