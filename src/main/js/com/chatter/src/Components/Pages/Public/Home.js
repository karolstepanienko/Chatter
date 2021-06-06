import React from "react";

import "../../../css/Pages/Public/Home.css";
import PostsDisplay from "../Post/PostsDisplay";
import LinkButton from "../../LinkButton/LinkButton";
import ProfileLink from "../../ProfileLink/ProfileLink";

export const Home = () => (
  <div className="home">
    <h2 className="home-title">Home page</h2>
    <div className="content-left">
      <LinkButton to='/addpost' className="button-home">Add post</LinkButton>
      <ProfileLink component={LinkButton} message="Profile" className="button-home"/>
    </div>
    <div className="content-middle">
    <PostsDisplay />
     
    </div>
    <div className="content-right">
    </div>
  </div>
);