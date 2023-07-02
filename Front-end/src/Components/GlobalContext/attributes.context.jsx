import { createContext, useContext, useEffect, useReducer } from "react";
import { useLocation } from "react-router-dom";

const AttContext = createContext();
const initialAttributeState = { attributeList: [], attributeDetail: {} };

const attributeReducer = (state, action) => {
  switch (action.type) {
    case "GET_ATTRIBUTE":
      return action.payload;
    default:
      return state;
  }
};

const attDetReducer = (state, action) => {
  switch (action.type) {
    case "GET_ATTRIBUTE":
      return {
        attributeList: action.payload,
        attributeDetail: state.attributeDetail,
      };
    case "GET_ATTRIBUTE_DETAIL":
      return {
        attributeDetail: action.payload,
        attributeList: state.attributeDetail,
      };
    case "DELETE_ATTRIBUTE":
      const updateAttribute = state.attributeList.filter(
        (attribute) => attribute.id !== action.payload.attributeId
      );
      return {
        attributeList: updateAttribute,
      };
    default:
      throw new Error();
  }
};

const attReducer = (state, action) => {
  switch (action.type) {
    case "GET_ATTRIBUTE":
      return action.payload;
    default:
      return state;
  }
};

const AttributeProvider = ({ children }) => {
  const location = useLocation();
  const [attributeState, attributeDispatch] = useReducer(attributeReducer, []);
  const [attributeDetState, attributeDetDispatch] = useReducer(
    attDetReducer,
    initialAttributeState
  );
  const [attState, attDispatch] = useReducer(attReducer, []);

  console.log(attributeState);

  const url = `${import.meta.env.VITE_BACKEND_URL}/attributes/get-all`;

  useEffect(() => {
    fetchData();
  }, [location.pathname]);

  const fetchData = () => {
    fetch(url)
      .then((res) => res.json())
      .then((data) => {
        attributeDispatch({ type: "GET_ATTRIBUTE", payload: data });
      });
  };

  return (
    <AttContext.Provider
      value={{
        attributeState,
        attributeDispatch,
        attState,
        attDispatch,
        attributeDetState,
        attributeDetDispatch,
      }}
    >
      {children}
    </AttContext.Provider>
  );
};

export default AttributeProvider;

export const useContextGlobal = () => useContext(AttContext);
