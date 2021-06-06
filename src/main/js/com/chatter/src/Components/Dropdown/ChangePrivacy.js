import axios from 'axios';
import { useEffect, useState } from 'react';

import { Privacies, link } from '../../Constants/Constants';
import Expire from '../DisappearingComponent/Expire';
import { updateAccountPrivacyStore } from '../../State/userSlice';


const ChangePrivacy = (props) => {
  const [show, setShow] = useState(false);
  const [newPrivacy, setNewPrivacy] = useState("");
  const [privacyUpdated, setPrivacyUpdated] = useState(false);

  const handleChangePrivacyClick = () => {
    setShow(!show);
  }

  const checkIfEmpty = () => {
    if (newPrivacy === "") return true;
    else return false;
  }

  const handleSetPublic = () => {
    setNewPrivacy(Privacies.publicAccess);
    setShow(!show);
  }

  const handleSetPrivate = () => {
    setNewPrivacy(Privacies.privateAccess);
    setShow(!show);
  }

  const updateAccountPrivacyDatabase = () => {
    if (!checkIfEmpty() && !privacyUpdated) {
      axios.post(`${link}/account/user/update/privacy`, getUserINFO(), props.createConfig()).then(
        res => setPrivacyUpdated(res.data)
      ).catch(
        err => console.log(err)
      )
    }
  }

  const getUserINFO = () => {
    var userINFO = {
      id: props.id,
      userName: props.userName,
      login: props.login,
      email: props.email,
      accountPrivacy: newPrivacy,
      role: props.role
    }
    return userINFO;
  }

  useEffect(() => {
    updateAccountPrivacyDatabase();
  }, [newPrivacy])

  useEffect(() => {
    if (privacyUpdated) props.dispatch(updateAccountPrivacyStore(newPrivacy));
  }, [newPrivacy, updateAccountPrivacyDatabase])

  return (
    <div>
      <button
        className={props.className}
        onClick={handleChangePrivacyClick}
      >Change privacy</button>
      {
        show ? (
          <div>
            <button 
              className={props.className}
              onClick={handleSetPublic}>
              Set account public
            </button>
            <button 
              className={props.className}
              onClick={handleSetPrivate}>
              Set account private
            </button>
          </div>
        ) : (null)
      }
      <Expire 
        visible={privacyUpdated}
        reset={setPrivacyUpdated}
      />
    </div>
  )

}

export default ChangePrivacy;