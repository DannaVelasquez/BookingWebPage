import { createContext, useContext, useEffect, useReducer } from "react";
import { useLocation } from "react-router-dom";

const ContextGlobal = createContext();
const initialTourState = { tourList: [], tourDetail: {} };

const tourReducer = (state, action) => {
  switch (action.type) {
    case "GET_TOURS":
      return action.payload;
    default:
      return state;
  }
};

const tourDetReducer = (state, action) => {
  switch (action.type) {
    case "GET_TOURS":
      return {
        tourList: action.payload,
        tourDetail: state.tourDetail,
      };
    case "GET_TOUR_DETAIL":
      return {
        tourDetail: action.payload,
        tourList: state.tourDetail,
      };
    case "DELETE_TOUR":
      const updateTours = state.tourList.filter(
        (tour) => tour.id !== action.payload.tourId
      );
      window.location.reload();
      return {
        tourList: updateTours,
      };
    default:
      throw new Error();
  }
};

const catReducer = (state, action) => {
  switch (action.type) {
    case "GET_CATEGORY":
      return action.payload;
    default:
      return state;
  }
};

const ContextProvider = ({ children }) => {
  const location = useLocation();
  const [tourState, tourDispatch] = useReducer(tourReducer, []);
  const [tourDetState, tourDetDispatch] = useReducer(
    tourDetReducer,
    initialTourState
  );
  const [tourBookState, tourBookDispatch] = useReducer(
    tourDetReducer,
    initialTourState
  );
  const [catState, catDispatch] = useReducer(catReducer, []);

  console.log(tourState);

  const url = `${import.meta.env.VITE_BACKEND_URL}/experiences/recommend`;

  useEffect(() => {
    fetchData();
  }, [location.pathname]);

  const fetchData = () => {
    fetch(url)
      .then((res) => res.json())
      .then((data) => {
        tourDispatch({ type: "GET_TOURS", payload: data });
      });
  };

  return (
    <ContextGlobal.Provider
      value={{
        tourState,
        tourDispatch,
        catState,
        catDispatch,
        tourDetState,
        tourDetDispatch,
        tourBookState,
        tourBookDispatch
      }}
    >
      {children}
    </ContextGlobal.Provider>
  );
};

export default ContextProvider;

export const useContextGlobal = () => useContext(ContextGlobal);
