import {createSlice} from "@reduxjs/toolkit";

// Creating the store
export const userSlice = createSlice({
  name: "user",
  initialState: {
    user: {
      id: null,
      userName: '',
      email: '',
      passwordHash: '',
      login: '',
      role: '',
    }
  },
  reducers: {
    login: (state, action) => {
      state.user = action.payload;
    },
    logout: (state) => {
      state.user = null;
    }
  }
})

export const {login, logout} = userSlice.actions;

export const selectUser = (state) => state.user;

export default userSlice.reducer;