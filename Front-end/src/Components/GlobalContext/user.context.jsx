import { createContext, useReducer, useContext } from "react";

export const userContext = createContext();

const initialState = {
  user:{},
  isAuthenticated: false,
  error: null,  
};

const userReducer = (state, action) => {
  switch (action.type) {
    case "LOGIN":
      return {
        ...state,
        user: action.payload.user,
        isAuthenticated: true,
        isLoading: false,
        error: null,
      };
      case "LOGIN_FAILURE":
      return {
        ...state,
        user: null,
        isAuthenticated: false,
        isLoading: false,
        error: action.payload.error,
      };
    case "LOGOUT":
      return {
        ...state,
        user: null,
        isAuthenticated: false,
        isLoading: false,
        error: null,        
      };
    default:
      return state;
  }
};

const AuthProvider = ({ children }) => {
  const [userState, userDispatch] = useReducer(userReducer, initialState);

  console.log(userState);

  return (
    <userContext.Provider
      value={{
        userState,
        userDispatch,
      }}
    >
      {children}
    </userContext.Provider>
  );
};

export default AuthProvider;
