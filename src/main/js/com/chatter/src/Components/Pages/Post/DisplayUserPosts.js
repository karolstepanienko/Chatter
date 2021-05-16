import axios from 'axios';
import React, { useEffect, useState } from "react";
import { Link } from 'react-router-dom';
import { link, accountPrivacies } from '../../../Constants/Constants';

import '../../../css/Pages/Post/DisplayUserPosts.css';


const DisplayUserPosts = (props) => {
  const [userExists, setUserExists] = useState(false);
  const [userPosts, setUserPosts] = useState([]);
  const [postDeleted, setPostDeleted] = useState(false);

  const checkUserId = () => {
    if (props.id === undefined) setUserExists(false);
    else setUserExists(true);
  }

  const fetchPosts = () => {
    if (userExists){
      axios.get(`${link}/post/get/posts/with/creatorId?creatorId=${props.id}`).then(
        res => setUserPosts(res.data)
      ).catch( err => console.log(err) )
    }
  }

  const getUserNotExists = () => {
    return (
      <div className="user-not-exists">
        This user does not exist.
      </div>
    )
  }

  const getPostLocationById = (id) => {
    for (var i = 0; i< userPosts.length; i++ ) {
      if (userPosts[i].id === id) return i;
    }
  }

  const removePostFromDisplay = (i) => {
    userPosts.splice(i, 1);
    setPostDeleted(true);
  }

  const handlePostRemove = (id) => {
    removePostFromDisplay(getPostLocationById(id));
  }

  const getPosts = () => {
    var rows = [];
    for (var i = 0; i < userPosts.length; i++) {
      rows.push(<DisplayPost
        post = {userPosts[i]}
        handlePostRemove={handlePostRemove}
        {...props}/>);
    }
    return rows;
  }
 
  useEffect(() => {
    checkUserId();
  }, [])

  useEffect(() =>{
    fetchPosts();
  }, [userExists])

  useEffect(() => {
    if (postDeleted) setPostDeleted(false);
  }, [userPosts, postDeleted])

  const display = () => {
    if (userExists) return getPosts();
    else return getUserNotExists();
  }

  return (display())
}

const DisplayPost = (props) => {

  const getLink = () => {
    var link_to_user = `/user/${props.userName}`
    return link_to_user;
  }

  const handlePostDelete = () => {
    if (window.confirm("Are you sure, you want to delete this post?")) {
      // Deletes by path variable
      axios.post(`${link}/post/delete/by/Id/${props.post.id}`).then(
        res => props.handlePostRemove(props.post.id)
      ).catch( err => console.log(err))  
    }
  }

  const handlePrivacyChange = () => {
    axios.post(`${link}/post/update/privacy/${props.post.id}`).then(
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

export default DisplayUserPosts;