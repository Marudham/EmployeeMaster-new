import { createSlice } from '@reduxjs/toolkit';

const initialState = localStorage.getItem('reduxState')
  ? JSON.parse(localStorage.getItem('reduxState'))
  : { user: null};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    login: (state, action) => {
      state.user = action.payload.user;
      localStorage.setItem('reduxState', JSON.stringify(state));
    },
    logout: (state) => {
      state.user = null;
      localStorage.setItem('reduxState', JSON.stringify(state));
    },
  },
});

export const { actions } = authSlice; 
export const { login, logout } = actions;

export const selectUser = (state) => state.auth.user;
export default authSlice.reducer;
