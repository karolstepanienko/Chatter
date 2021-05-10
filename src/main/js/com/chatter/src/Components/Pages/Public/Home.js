import React from 'react';

import '../../../css/Pages/Public/Home.css';
import PostsDisplay from '../Post/PostsDisplay';

export const Home = (props) => (
  <div className="home">
    <h2 className="home-title">Home page</h2>
    <div className="content-1">
    <form action="/addpost">
    <input className="button_home"type="submit" value="Add post" />
    </form>
    <form action="/friends">
    <input className="button_home"type="submit" value="Friends" />
    </form>
    <form action="/profile">
    <input className="button_home"type="submit" value="Profile" />
    </form>
      <p>Content 1</p>
      <p>Content 1</p>
      <p>Content 1</p>
      <p>Content 1</p>
      <p>Content 1</p>
    </div>
    <div className="content-2">
    <PostsDisplay />
     
    </div>
    <div className="content-3">
      <p>Content 3</p>
      <p>Content 3</p>
      <p>Content 3</p>
      <p>Content 3</p>
      <p>Content 3</p>
    </div>
  </div>
)