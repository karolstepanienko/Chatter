import React from 'react';
import axios from 'axios';

import '../../../css/Pages/Post/PostsDisplay.css';
import { link }from '../../../Constants/Constants';
import {connect} from 'react-redux' 

class  PostsDisplay extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      error: null,
      isLoaded: true,
      items:[],
      login:[],
      id:this.props.id
      };
      console.log("-----------------------")
      console.log(this.state.id)
    }
    handleLike(evt,i){
        console.log("like")
        console.log("dislike")
        console.log(evt.target.checked);
        console.log(i);
      var like={status:evt.target.checked, user:this.state.id, post:this.state.items[i].id}
      axios.post(`${link}/like`, like)
      .then((response) => {this.componentDidMount()})
  }
    async componentDidMount() {
      const url=`${link}/allposts`;
      const response = await fetch(url);
      const data = await response.json();
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
      if (id!=null){
      var tab = this.state.login;
      console.log(this.state.login);
      axios.get(`${link}/account/getlogin?id=${id}`) 
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
        var a = i
        return (
          <div className="post">
            <div className="Creator">
              autor: {logins[i++]}         
            </div>
            <div className="postText">
              {(post.text)}
            </div>
            <input type="checkbox" key={i} onChange={evt =>this.handleLike(evt,a)}></input>
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
        return <div>loading...</div>;
      }
      return (
        <div>
          {this.display(this.state.items,this.state.login)}
        </div>
      );
      }
    }
const mapStateToProps = state => ({
  id: state.user.user.id,
  userName: state.user.user.userName,
  email: state.user.user.email,
  passwordHash: state.user.user.passwordHash,
  login: state.user.user.login,
   role: state.user.user.role,
    });
export default connect(mapStateToProps)(PostsDisplay)