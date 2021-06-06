import axios from 'axios';
import { Link } from 'react-router-dom';

import { link } from '../../../Constants/Constants';


const DisplayLoggedInUserPost = (props) => {

  const getLink = () => {
    var link_to_user = `/user/${props.userName}`
    return link_to_user;
  }

  const handlePostDelete = () => {
    if (window.confirm("Are you sure, you want to delete this post?")) {
      // Deletes by path variable
      axios.post(`${link}/post/delete/by/Id/${props.post.id}`, "", props.createConfig()).then(
        res => props.handlePostRemove(props.post.id)
      ).catch( err => console.log(err))  
    }
  }

  const handlePrivacyChange = () => {
    axios.post(`${link}/post/update/privacy/${props.post.id}`, "", props.createConfig()).then(
      res => alert("Post privacy setting changed.")
    ).catch(err => console.log(err))
  }

  return (
    <div className="post">
      <span className="post-title">Post:</span>
      <div className="creator">
        <span>Autor: </span>
        <Link to={getLink()} userName={props.userName}>{props.userName}</Link>
      </div>
      <div className="post-text">
        {(props.post.text)}
      </div>
      <button className="button-changePrivacy"
        onClick={handlePrivacyChange}>
          Change post privacy
      </button>
      <button className="button-delete"
        onClick={handlePostDelete}>
        Delete post
      </button>
    </div>
  )
}

export default DisplayLoggedInUserPost;