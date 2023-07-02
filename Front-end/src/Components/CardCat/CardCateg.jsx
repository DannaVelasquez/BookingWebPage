import React from "react";
import { Link } from "react-router-dom";
import styles from "./CardCat.module.css";

const CardCat = ({ tour }) => {

    return (
        <div className={styles.card}>
          <img
            className={styles.cardImage}
            src={tour.image}
            alt=""
           
          />
          <Link
            key={tour.id}
            to={"/products/" + tour.name}
          >
            <h3 className={styles.h3}>{tour.name}</h3>
          </Link>
          {/* <p className={styles.p}>{tour.amount}</p> */}
        </div>
      );
    };

export default CardCat;
