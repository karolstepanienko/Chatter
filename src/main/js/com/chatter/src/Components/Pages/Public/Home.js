import React from 'react';

import '../../../css/Pages/Public/Home.css';
import PostsDisplay from '../Post/PostsDisplay';
import LinkButton from '../../LinkButton/LinkButton';

export const Home = (props) => (
  <div className="home">
    <h2 className="home-title">Home page</h2>
    <div className="content-left">
      <LinkButton to='/addpost' className="button-home">Add post</LinkButton>
      <LinkButton to='/friends' className="button-home">Friends</LinkButton>
      <LinkButton to='/user' className="button-home">User profile</LinkButton>
      <p>Content 1</p>
      <p>Content 1</p>
      <p>Content 1</p>
      <p>Content 1</p>
      <p>Content 1</p>
    </div>
    <div className="content-middle">
    <PostsDisplay />
     
    </div>
    <div className="content-right">
      <p>Content 3</p>
      <p>Content 3</p>
      <p>Content 3</p>
      <p>Content 3</p>
      <p>Content 3</p>
    </div>
  </div>
)