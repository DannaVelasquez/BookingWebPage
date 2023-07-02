import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import App from "./App";
import ContextProvider from "./Components/GlobalContext/global.context";
import AuthProvider from "./Components/GlobalContext/user.context";
import "./index.css";
import CategoryProvider from "./Components/GlobalContext/category.context";
import AttributeProvider from "./Components/GlobalContext/attributes.context";
import CityProvider from "./Components/GlobalContext/city.context";
import PlaceProvider from "./Components/GlobalContext/places.context";

ReactDOM.createRoot(document.getElementById("root")).render(
  <BrowserRouter>
    <ContextProvider>
      <AuthProvider>
        <CategoryProvider>
          <AttributeProvider>
            <CityProvider>
              <PlaceProvider>
                <App />
              </PlaceProvider>
            </CityProvider>
          </AttributeProvider>
        </CategoryProvider>
      </AuthProvider>
    </ContextProvider>
  </BrowserRouter>
);
