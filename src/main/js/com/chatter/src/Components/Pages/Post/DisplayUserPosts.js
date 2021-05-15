import axios from 'axios';
import React, { useEffect, useState } from "react";
import { Link } from 'react-router-dom';
import { link, accountPrivacies } from '../../../Constants/Constants';

import '../../../css/Pages/Post/PostsDisplay.css';


const DisplayUserPosts = (props) => {
  const [userExists, setUserExists] = useState(false);
  const [userPosts, setUserPosts] = useState([]);

  const checkUserId = () => {
    console.log(props)
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

  const getPosts = () => {
    var rows = [];
    for (var i = 0; i < userPosts.length; i++) {
      rows.push(DisplayPost(props, userPosts[i]));
    }
    return rows;
  }


  useEffect(() => {
    checkUserId();
  }, [])

  useEffect(() =>{
    console.log(userExists);
    fetchPosts();
  }, [userExists])

  useEffect(() => {
    console.log(userPosts);
  }, [userPosts])

  const display = () => {
    if (userExists) return getPosts();
    else getUserNotExists();
  }

  return (
    <div className="user-posts">
      {display()}
    </div>
  )
}

const DisplayPost = (props, post) => {

  const getLink = () => {
    var link_to_user = `/user/${props.userName}`
    return link_to_user;
  }

  return (
    <div className="post">
      <div className="Creator">
        <span>Autor: </span>
        <Link to={getLink()} userName={props.userName}>{props.userName}</Link>
      </div>
      <div className="postText">
        {(post.text)}
      </div>
      <butoon className="delete-button">
        Delete post.
      </butoon>
    </div>
  )
}

export default DisplayUserPosts;