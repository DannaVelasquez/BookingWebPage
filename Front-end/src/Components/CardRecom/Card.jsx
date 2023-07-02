import React, { useState } from "react";
import { Link } from "react-router-dom";
import styles from "./Card.module.css";
import { FaMapMarkerAlt } from "react-icons/fa";
import { MdReadMore, MdHighlightOff} from "react-icons/md";

const Card = ({ tour }) => {
  const [showModal, setShowModal] = useState(false);

  const handleModalOpen = () => {
    setShowModal(true);
  };

  const handleModalClose = () => {
    setShowModal(false);
  };

  // const cutText = (text) => {
  //   const words = text.split(' ');
  //   if (words.length <= 6) {
  //     return text;
  //   } else {
  //     const cutWords = words.slice(0, 6);
  //     return <p onClick={handleModalOpen} className={styles.cutText}>{cutWords.join(' ') + '...'}</p> 
  //   }
  // };

  const cutText = (text) => {
    const maxLength = 80
    if (text.length <= maxLength) {
      return text;
    } else {
      const cutText = text.slice(0, maxLength);
      return <p onClick={handleModalOpen} className={styles.cutText}>{cutText + '...'}</p> 
    }
  };

  

  return (
    <div className={styles.card}>
      <img className={styles.cardImage} src={tour.images[0]} alt="" />
      <div>
      <h3 className={styles.h3}>{tour.title}</h3>
      <p className={styles.p}><FaMapMarkerAlt/>{tour.place.city}, {tour.place.country}</p>
      {showModal ? (
  <div className={styles.modal}>
    <div className={styles.modalContent}>
      <button className={styles.closeButton} onClick={handleModalClose}>
        <MdHighlightOff/>
      </button>
      <div className={styles.fullText}>{tour.description}</div>
    </div>
  </div>
) : (
  <div >
    <div className={styles.viewMore}>
      {cutText(tour.description) } 
   </div>
  </div>
)}
   
      <Link
        key={tour.id}
        to={"/tour/" + tour.id}
      >
        <button className={styles.button}>Ver más</button>
      </Link>
      </div>
    </div>
  );
};

export default Card;
