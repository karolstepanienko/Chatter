import React from "react";
import axios from "axios";

import "../../../css/Pages/Post/PostsDisplay.css";
import { link }from "../../../Constants/Constants";


export default class Friends extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          error: null,
          isLoaded: true,
          items:[],
          login:[]
        };
      }
    
    render() {
        return (
          <div>
            siema2
          </div>
        );
      }
    }