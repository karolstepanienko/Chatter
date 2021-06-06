import axios from 'axios';
import React, { useEffect, useState } from "react";
import { link } from '../../../Constants/Constants';
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
    console.log(props);
    if (userExists){
      axios.get(`${link}/post/get/posts/with/creatorId?creatorId=${props.id}`, props.createConfig()).then(
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
      rows.push(<props.display
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

export default DisplayUserPosts;