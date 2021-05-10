import React from 'react';
import axios from 'axios';

import '../../../css/Pages/Post/PostsDisplay.css';
import { link }from '../../../Constants/Constants';


export default class PostsDisplay extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          error: null,
          isLoaded: true,
          items:[],
          login:[]
        };
      }
    
    async componentDidMount() {
        const url=`${link}/allposts`;
        const response = await fetch(url);
        const data = await response.json();
        console.log(data);
        this.setState({ items: data, isLoaded: false});
         for (let i = 0; i < data.length; i++) {
           this.getLogin(data[i].creatorId);
         }

    }

    async getLogin(id){
      const response = await axios.get(`${link}account/getlogin?id=${id}`);
      console.log(response.data);
      var tab= this.state.login; 
      tab.push(response.data)
      this.setState({login: tab});
    }

    display(array,logins) {
      var i =0;
      return array.map(function (post) {
          return (
                <div className="post">
                    <div className="Creator">
                        autor: {logins[i++]}
                        
                    </div>
                    <div className="postText">
                        {(post.text)}
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
            {this.display(this.state.items,this.state.login)}
          </div>
        );
      }
    }