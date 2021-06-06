import React from "react";
import axios from "axios";
import { connect } from "react-redux";
import PropTypes from "prop-types";

import "../../css/Pages/Post/Postadding.css";
import { link, Privacies }from "../../Constants/Constants";


const Post = (props)=> {
    var post = {
    id: null,
    creatorId: props.id,
    privacy: Privacies.publicAccess,
    text: "",
    likes: 0
  };

  const createConfig = () => {
    let config = {
      headers: {
        Authorization: props.tokenType + " " + props.accessToken
      }
    };
    return config;
  };

  function handleAddingPost(evt) {
    evt.preventDefault();
    axios.post(`${link}/post/addpost`, post, createConfig());
    alert("Post have been added");
    document.forms["post_texti"].reset();
    post.text="";
    post.privacy=Privacies.publicAccess;
  }

  function handlePostChange(evt) {
    post.text = evt.target.value;
  }

  function handlePrivacyChange(evt){
    if(evt.target.checked){
      post.privacy = Privacies.privateAccess;
    }
    else{
      post.privacy = Privacies.publicAccess;
    }
  }

  return(
  <div className="add_post_page">
    <h2>Add post</h2>
    <form id ='post_texti'>
      <textarea 
        id="post_text"
        type="text"
        cols="40"
        rows="5"
        minLength="1"
        maxLength="500"
        name="post"
        onChange={evt => handlePostChange(evt)}>
      </textarea>
      <input type="checkbox" id="checkbox" name="privacy"  
        onChange={evt =>handlePrivacyChange(evt)}></input>
      <label htmlFor="scales"> The post is private</label>
    </form>
    
    <form>
      <input 
          id="button_post"
          type="submit"
          value="Post"
          onClick={evt => handleAddingPost(evt)}>
      </input>
    </form>
  </div>
  );
};

Post.propTypes = {
  id: PropTypes.number,
  tokenType: PropTypes.string,
  accessToken: PropTypes.string
};

const mapStateToProps = state => ({
  id: state.user.user.id,
  userName: state.user.user.userName,
  email: state.user.user.email,
  passwordHash: state.user.user.passwordHash,
  login: state.user.user.login,
  role: state.user.user.role,
  tokenType: state.user.user.tokenType,
  accessToken: state.user.user.accessToken
});

export default connect(mapStateToProps)(Post);
