import React from 'react';
import axios from 'axios';

import '../../../css/Pages/Account/Postadding.css';
import { link }from '../../../Constants/Constants';

export default class Post extends React.Component {
    constructor(props) {
        super(props);
        this.post = {
          id: null,
          creatorId: 1,
          text: '',
        }
      }
    handleAddingPost(evt) {
        evt.preventDefault();
        axios.post(`${link}/addpost`, this.post);
        alert("Post have been added");
        document.forms["post_texti"].reset();
    }
    handlePostChange(evt) {
        this.post.text = evt.target.value;
      }
    render() {
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
          onChange={evt => this.handlePostChange(evt)}>
        </textarea>
      </form>
      
      <form>
        <input 
            id="button_post"
            type="submit"
            value="Post"
            onClick={evt => this.handleAddingPost(evt)}>
        </input>
      </form>
    </div>
    )
  }
}