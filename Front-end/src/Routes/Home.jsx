import React from "react";
import Category from "../Components/Category/Category";
import Searchbar from "../Components/Search-Bar/Searchbar";
import Recom from "../Components/Recomendations/Recom";
import Banner from "../Components/Banner/Banner";
import "../index.css";

const Home = () => {
  return (
    <div className="home">
      <Searchbar />
      <Banner />
      <Category /> 
      <Recom />
    </div>
  );
};

export default Home;
