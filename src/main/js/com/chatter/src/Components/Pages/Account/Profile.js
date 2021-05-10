import React from 'react';
import axios from 'axios';

import '../../../css/Pages/Account/Register.css';
import { link }from '../../../Constants/Constants';

const linkRegister = `${link}/account/register`;

export default class Profile extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          id: 101,
          login: [""],
          logChange:0,
          newlogin:"",
          response:""
        };
      }
    
    async getLogin(){
        const response = await axios.get(`${link}account/getlogin?id=${this.state.id}`);
        this.setState({login: [response.data]});
    }
    Change(){
        this.setState({logChange:1});
    }
    handleLoginChange(evt) {
        evt.preventDefault();
        this.state.newlogin = evt.target.value;
      }
    handleChange(evt){
        console.log(this.state.newlogin);
        
            // Simple POST request with a JSON body using axios
            const change = { id: this.state.id, login: this.state.newlogin };
            axios.post(`${link}account/changelogin`, change)
                .then(response => this.setState({ response: response.data }));
        console.log(this.state.response);
        alert("Nazwa zmieniona")
    }
    

    render() {
        this.getLogin()
        if (this.state.logChange){
            return <div className="login-page">
            <div >
            <input 
            className="login"
            type="text"
            name="login"
            placeholder="Your new login"
            minLength="3"
            maxLength="20"
            onChange={evt => this.handleLoginChange(evt)}>
            </input>
            <input 
            className="button"
            type="submit"
            value="Change"
            onClick={evt => this.handleChange(evt)}>
            </input>
            </div>
          </div>
        }
        else{
        return (
        <div className="login-page">
          <div className="profile_login" >
            <div className ="left">Login: </div><div className ="right"> {(this.state.login[0])}</div>
          </div>
          <div className="profile_login" >
            <div className ="left">Login: </div><div className ="right"> {(this.state.login[0])+"sdksajhdskjdhsjdhsjhd"}</div>
          </div>
          <input 
            className="button"
            type="submit"
            value="Change Login"
            onClick={evt => this.Change(evt)}>
          </input>
          
        </div>
        );
        
      }
    }
}