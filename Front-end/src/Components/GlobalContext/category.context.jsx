import { createContext, useContext, useEffect, useReducer } from "react";
import { useLocation } from "react-router-dom";

const CatContext = createContext();
const initialCategoryState = { categoryList: [], categoryDetail: {} };

const categoryReducer = (state, action) => {
  switch (action.type) {
    case "GET_CATEGORY":
      return action.payload;
    default:
      return state;
  }
};

const catDetReducer = (state, action) => {
  switch (action.type) {
    case "GET_CATEGORY":
      return {
        categoryList: action.payload,
        categoryDetail: state.categoryDetail,
      };
    case "GET_CATEGORY_DETAIL":
      return {
        categoryDetail: action.payload,
        categoryList: state.categoryDetail,
      };
    case "DELETE_CATEGORY":
      const updateCategory = state.categoryList.filter(
        (category) => category.id !== action.payload.categoryId
      );
      
      return {
        categoryList: updateCategory,
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

const CategoryProvider = ({ children }) => {
  const location = useLocation();
  const [categoryState, categoryDispatch] = useReducer(categoryReducer, []);
  const [categoryDetState, categoryDetDispatch] = useReducer(
    catDetReducer,
    initialCategoryState
  );
  const [catState, catDispatch] = useReducer(catReducer, []);

  const url = `${import.meta.env.VITE_BACKEND_URL}/categories/get-all`;

  useEffect(() => {
    fetchData();
  }, [location.pathname]);

  const fetchData = () => {
    fetch(url)
      .then((res) => res.json())
      .then((data) => {
        categoryDispatch({ type: "GET_CATEGORY", payload: data });
      });
  };

  return (
    <CatContext.Provider
      value={{
        categoryState,
        categoryDispatch,
        catState,
        catDispatch,
        categoryDetState,
        categoryDetDispatch,
      }}
    >
      {children}
    </CatContext.Provider>
  );
};

export default CategoryProvider;

export const useContextGlobal = () => useContext(CatContext);
