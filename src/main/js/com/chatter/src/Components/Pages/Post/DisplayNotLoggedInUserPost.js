import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import { Privacies } from "../../../Constants/Constants";


const DisplayNotLoggedInUserPost = (props) => {
  const [isPublic, setIsPublic] = useState(false);

  const getLink = () => {
    var link_to_user = `/user/${props.userName}`;
    return link_to_user;
  };

  const checkPrivacy = () => {
    if (props.post.privacy === Privacies.publicAccess) {
      setIsPublic(true);
    } else setIsPublic(false);
  };

  useEffect(() => checkPrivacy(), []);

  return (
    isPublic ? (
      <div className="post">
      <span className="post-title">Post:</span>
      <div className="creator">
        <span>Autor: </span>
        <Link to={getLink()} userName={props.userName}>{props.userName}</Link>
      </div>
      <div className="post-text">
        {(props.post.text)}
      </div>
    </div>
    ) : ( null )
  );
};

export default DisplayNotLoggedInUserPost;