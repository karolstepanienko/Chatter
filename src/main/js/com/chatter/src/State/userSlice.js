import {createSlice} from "@reduxjs/toolkit";

// Creating the slice, store consists of multiple slices
export const userSlice = createSlice({
  name: "user",
  initialState: {
    user: {
      id: '2',
      userName: 'test',
      email: '',
      passwordHash: '',
      login: '',
      role: '',  
    }
  },
    // Actions - functions that set data in the state
    // Need to be imported in brackets, oterwise will be imported as normal functions
  reducers: {
    login: (state, action) => {
      state.user = action.payload;
    },
    logout: (state) => {
      state.user = initialState;
    }
  }
})



export const {login, logout} = userSlice.actions;

// export const selectUser = (state) => {state};

// userReducer
export default userSlice.reducer;
