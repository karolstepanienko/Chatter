import { useState } from 'react';

const ChangePrivacy = (props) => {
  const [show, setShow] = useState(false);

  const handleChangePrivacyClick = () => {
    setShow(!show);
  }

  return (
    <div className={props.className}>
      <button
        className={props.className}
        onClick={handleChangePrivacyClick}
      >Change privacy</button>
      {
        show ? (
          <div>
            <button className={props.className} >Set account private</button>
            <button className={props.className} >Set account public</button>
          </div>
        ) : (null)
      }
    </div>
  )

}

export default ChangePrivacy;