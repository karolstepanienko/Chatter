import {configureStore} from "@reduxjs/toolkit";
import userReducer from "./userSlice";

const store = configureStore({
  reducer:{
    //  Here to a slice by the name user, the reducer is added
    user: userReducer,
  }
});

export default store;
