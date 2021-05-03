import React from 'react';
import axios from 'axios';

import '../../../css/Pages/Account/Postadding.css';
import { link }from '../../../Constants/Constants';

export default class PostsDisplay extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          error: null,
          isLoaded: true,
          items:[]
        };
      }
    
    async componentDidMount() {
        const url=`${link}allposts`;
        const response = await fetch(url);
        const data = await response.json();
        console.log(data);
        this.setState({ items: data, isLoaded: false });
    }

    display(array) {
        return array.map(function (post) {
            return (
                <div className="post">
                    <div className="Creator">
                        id autora: {post.creatorId}
                    </div>
                    <div className="postText">
                        {post.text}
                    </div>
                    <br></br>
                    <br></br>
                    <br></br>
                </div>
            )
        })
    }    
    render() {
        if (this.state.isLoaded) {
          return <div>loading...</div>;
        }
    
        return (
          <div>
            {this.display(this.state.items)}
          </div>
        );
      }
    }