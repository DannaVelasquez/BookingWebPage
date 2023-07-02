import { createContext, useContext, useEffect, useReducer } from "react";
import { useLocation } from "react-router-dom";

const CityContext = createContext();
const initialCityState = { cityList: [], cityDetail: {} };

const cityReducer = (state, action) => {
  switch (action.type) {
    case "GET_CITIES":
      return action.payload;
    default:
      return state;
  }
};

const cityDetReducer = (state, action) => {
  switch (action.type) {
    case "GET_CITIES":
      return {
        cityList: action.payload,
        cityDetail: state.cityDetail,
      };
    case "GET_CITY_DETAIL":
      return {
        cityDetail: action.payload,
        cityList: state.cityDetail,
      };
    case "DELETE_CITY":
      const updateCity = state.cityList.filter(
        (city) => city.id !== action.payload.cityId
      );
      return {
        cityList: updateCity,
      };
      case "UPDATE_CITY":
      const updatedCityList = state.cityList.map((city) => {
        if (city.id === action.payload.cityId) {
          return action.payload.updatedCity;
        }
        return city;
      });
      return {
        cityList: updatedCityList,
        cityDetail: state.cityDetail,
      };
    default:
      throw new Error();
  }
};

const citReducer = (state, action) => {
  switch (action.type) {
    case "GET_CITIES":
      return action.payload;
    default:
      return state;
  }
};

const CityProvider = ({ children }) => {
  const location = useLocation();
  const [cityState, cityDispatch] = useReducer(cityReducer, []);
  const [cityDetState, cityDetDispatch] = useReducer(
    cityDetReducer,
    initialCityState
  );
  const [citState, citDispatch] = useReducer(citReducer, []);

  console.log(cityState);

  const url =`${import.meta.env.VITE_BACKEND_URL}/cities/get-all`;

  useEffect(() => {
    fetchData();
  }, [location.pathname]);

  const fetchData = () => {
    fetch(url)
      .then((res) => res.json())
      .then((data) => {
        cityDispatch({ type: "GET_CITIES", payload: data });
      });
  };

  return (
    <CityContext.Provider
      value={{
        cityState,
        cityDispatch,
        citState,
        citDispatch,
        cityDetState,
        cityDetDispatch,
      }}
    >
      {children}
    </CityContext.Provider>
  );
};

export default CityProvider;

export const useContextGlobal = () => useContext(CityContext);
