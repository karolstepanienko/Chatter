import {createSlice} from "@reduxjs/toolkit";

// Creating the slice, store consists of multiple slices
export const userSlice = createSlice({
  name: "user",
  initialState: {
    user: {
      id: null,
      userName: '',
      login: '',
      email: '',
      passwordHash: '',
      role: '',
      postList: '', 
    }
  },
    // Actions - functions that set data in the state
    // Need to be imported in brackets, oterwise will be imported as normal functions
  reducers: {
    login: (state, action) => {
      state.user = action.payload;
    },
    logout: (state) => {
      state.user = {
        user: {
          id: null,
          userName: '',
          login: '',
          email: '',
          passwordHash: '',
          role: '',
          postList: '', 
        }
      };
    },
    updateLogin: (state, action) => {
      state.user.login = action.payload;
    }
  }
})



export const {
  login,
  logout,
  updateLogin} = userSlice.actions;

// export const selectUser = (state) => {state};

// userReducer
export default userSlice.reducer;
