import React, { useEffect, useState } from "react";
import styles from "./Category.module.css";
import CardCat from "../CardCat/CardCateg";
import { useContextGlobal } from "../GlobalContext/global.context";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Slider from "react-slick";
import "../../carruselSlider.css"

const Category = () => {
    const { catState, catDispatch } = useContextGlobal();
    const url = `${import.meta.env.VITE_BACKEND_URL}/categories/get-all`;
    const [categories, setCategories] = useState([]);
  
    useEffect(() => {
      fetch(url)
        .then((res) => res.json())
        .then((data) => {
          catDispatch({ type: "GET_CATEGORY", payload: data });
          console.log(data.data)
          setCategories(data.data); // Guardar los datos en el estado local
        });
    }, []);
  
    console.log(catState?.data);
  
  
    const carouselSettings = {
      dots: false,
      infinite: true,
      speed: 500,
      slidesToShow: 4,
      slidesToScroll: 3,
      autoplay: true, 
      
    };
    const carouselSettingsMovile = {
      dots: false,
      infinite: true,
      speed: 500,
      slidesToShow: 2,
      slidesToScroll: 2,
      autoplay: true, 
      
    };
  
    return (
      <div className={styles.category}>
       <div className={styles.titleConteiner}>
       <h3 className={styles.h3}>Buscar por Categorías</h3>
        </div> 
        <hr />
        <br />
        <div className={styles.grid}>
          <Slider {...carouselSettings}>
            {categories.map((tour) => (
              <div key={tour.id} className={styles.cardContainer}>
                <CardCat tour={tour} />
              </div>
            ))}
          </Slider>
        </div>
        <div className={styles.gridMovile}>
          <Slider {...carouselSettingsMovile}>
            {categories.map((tour) => (
              <div key={tour.id} className={styles.cardContainer}>
                <CardCat tour={tour} />
              </div>
            ))}
          </Slider>
        </div> 
      </div>
    );
  };
  
  export default Category;
