import { createContext, useContext, useEffect, useReducer } from "react";
import { useLocation } from "react-router-dom";

const PlaceContext = createContext();
const initialPlaceState = { placeList: [], placeDetail: {} };

const placeReducer = (state, action) => {
  switch (action.type) {
    case "GET_PLACES":
      return action.payload;
    default:
      return state;
  }
};

const placeDetReducer = (state, action) => {
  switch (action.type) {
    case "GET_PLACES":
      return {
        placeList: action.payload,
        placeDetail: state.placeDetail,
      };
    case "GET_PLACE_DETAIL":
      return {
        placeDetail: action.payload,
        placeList: state.placeDetail,
      };
    case "DELETE_PLACE":
      const updatePlace = state.placeList.filter(
        (place) => place.id !== action.payload.placeId
      );
      return {
        placeList: updatePlace,
      };
    default:
      throw new Error();
  }
};

const placReducer = (state, action) => {
  switch (action.type) {
    case "GET_PLACES":
      return action.payload;
    default:
      return state;
  }
};

const PlaceProvider = ({ children }) => {
  const location = useLocation();
  const [placeState, placeDispatch] = useReducer(placeReducer, []);
  const [placeDetState, placeDetDispatch] = useReducer(
    placeDetReducer,
    initialPlaceState
  );

  
  const [placState, placDispatch] = useReducer(placReducer, []);

  console.log(placeState)

  const url = `${import.meta.env.VITE_BACKEND_URL}/places/get-all`;

  useEffect(() => {
    fetchData();
  }, [location.pathname]);

  const fetchData = () => {
    fetch(url)
      .then((res) => res.json())
      .then((data) => {
        placeDispatch({ type: "GET_PLACES", payload: data });
      });
  };

  return (
    <PlaceContext.Provider
      value={{
        placeState,
        placeDispatch,
        placeDetState,
        placeDetDispatch,
        placDispatch,
        placDispatch
      }}
    >
      {children}
    </PlaceContext.Provider>
  );
};

export default PlaceProvider;

export const useContextGlobal = () => useContext(PlaceContext);
