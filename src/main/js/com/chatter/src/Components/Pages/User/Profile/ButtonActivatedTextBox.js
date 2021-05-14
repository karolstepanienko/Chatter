import axios from 'axios';
import Expire from '../../../DisappearingComponent/Expire';
import {CSSTransition} from 'react-transition-group';

import { link } from '../../../../Constants/Constants';


axios.defaults.baseURL = `${link}/account/user`;


const ButtonActivatedTextBox = (props) => {
  const chooseRenderObject = () => {
    if (props.textBoxVisible) return textBoxAndSubmitButton();
    else return changeInitButton();
  }

  const chooseConfirmation = () => {
    return (
      <CSSTransition
        in={props.confirmation.visible}
        // How long the element is appearing
        timeout={300}
        classNames="display"
        unmountOnExit>
          <Expire
          reset={props.resetConfirmation}
          visible={props.confirmation.visible}
          className={props.confirmation.className} 
          delay="3000" 
          children={props.confirmation.value}/>
      </CSSTransition>
    )
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
        <input
          type="submit"
          value="Cancel"
          onClick={props.handleCancel}/>
      </div>
    )
  }

  const changeInitButton = () => {
    return(
      <button className={props.elementClassName}
        type="button"
        onClick={props.handleTextBoxVisibility}>
          {props.initMessage}
      </button>
    )
  }

  return (
    <div>
      {chooseRenderObject()}
      {chooseConfirmation()}
    </div>
  )
}

export default ButtonActivatedTextBox;
