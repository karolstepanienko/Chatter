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
      var tab1=[]
      for (let i = 0; i < data.length; i++){
        tab1.push("unknown")
      }
      this.setState({login: tab1});
      for (let i = 0; i < data.length; i++) {
        this.getLogin(data[i].creatorId,i);
      }
      
    }

    getLogin(id,i){
      var tab = this.state.login;
      console.log(this.state.login);
      axios.get(`${link}/account/getlogin?id=${id}`) 
      .then((response) => {
        console.log(response.data)
        if (response.data != ""){tab[i]=(response.data)}
      {this.setState({login: tab});}
      }, (error) => {
        console.log(error);
      });
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