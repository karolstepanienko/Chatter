import React from 'react';
import axios from 'axios';

import { Link } from 'react-router-dom';
import '../../../css/Pages/Post/PostsDisplay.css';
import { link }from '../../../Constants/Constants';
import {connect} from 'react-redux';


class  PostsDisplay extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      error: null,
      isLoaded: true,
      items:[],
      login:[],
      liked:[],
      id:this.props.id,
      init:false
      };
    }

    handleLike(evt,i,id){
        if(evt.target.checked){
          this.state.liked.push(id);
        }
        else{
         for( var j = 0; j < this.state.liked.length; j++){ 
    
          if ( this.state.liked[j] === id) { 
      
            this.state.liked.splice(j, 1); 
          }  
      }
        }
        console.log(this.state.liked);
      var like={status:evt.target.checked, user:this.state.id, post:id}
      axios.post(`${link}/post/like`, like)
      .then((response) => {this.componentDidMount()})
      this.componentDidMount()
  }

    async componentDidMount() {
      const url=`${link}/post/allposts`;
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
      if (this.state.init) {
        this.setState({init:false});
        this.liked();
      }
    }
  
    liked(){
      if (this.state.id!=null){
        axios.get(`${link}/post/likedposts?id=${this.state.id}`) 
        .then((response2) => {
          if (response2.data != ""){this.setState({liked: response2.data});}
        {console.log(response2.data);}
        }, (error) => {
          console.log(error);
        });
      }
    }

    getLogin(id,i){
      if (id!=null){
      var tab = this.state.login;
      axios.get(`${link}/account/getUserName?id=${id}`) 
      .then((response) => {
        if (response.data != ""){tab[i]=(response.data)}
      {this.setState({login: tab});}
      }, (error) => {
        console.log(error);
      });
    }
    }

    display(array,logins) {
      let i = 0;
      const val= array.map((post)=> {
        var a = i;
        var linkUserPage =`/user/${(logins[i])}`;
        if (this.state.id==null)
        {var input = <input type="checkbox" key={i} onChange={evt =>this.handleLike(evt,a,post.id)}  disabled></input> }
        else if(this.state.liked.includes(post.id)){
          var input = <input type="checkbox" key={i} checked={true} onChange={evt =>this.handleLike(evt,a,post.id) } ></input>
        }
        else {
          var input = <input type="checkbox" checked = {false} key={i} onChange={evt =>this.handleLike(evt,a,post.id)} ></input>
        }
        return (
          <div className="post">
            <div className="Creator">
              <span>Autor: </span>
              <Link to={linkUserPage} userName={logins[i]}>{logins[i]}</Link>
            </div>
            <div className="postText">
              {(post.text)}
            </div>
            {/* <input type="checkbox" key={i} onChange={evt =>this.handleLike(evt,a)}></input> */}
            {input}
            <label>{post.likes}</label>
            {/* checked="true" */}
            <br></br>
            <br></br>
            <br></br>
          </div>
            )
        })
        return val;
    }
    render() {
      if (this.state.isLoaded) {
        return <div>Loading...</div>;
      }
      return (
        <div>
          {this.display(this.state.items, this.state.login)}
        </div>
      );
      }
    }

const mapStateToProps = state => ({
  id: state.user.user.id
});

export default connect(mapStateToProps)(PostsDisplay)