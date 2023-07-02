import React from 'react'
import styles from "./IconosSideBar.module.css"
import { MdOutlineAssignmentTurnedIn, MdTravelExplore, MdCategory, MdLocationCity  } from "react-icons/md";

const IconsSideBar = ({ icon }) => {
  let statusMessage;

  switch (icon) {
    case 'Tours':
      statusMessage =    <p className={styles.p}> <MdTravelExplore className={styles.icon}/> Tours</p>  ;
      break;
    case 'Categorias':
      statusMessage = <p className={styles.p}><MdCategory className={styles.icon}/> Categorias</p>;
      break;
    case 'Servicios':
      statusMessage = <p className={styles.p}> <MdOutlineAssignmentTurnedIn className={styles.icon}/> Servicios</p>;
      break;
      case 'Ciudades':
      statusMessage = <p className={styles.p}>  <MdLocationCity className={styles.icon}/> Ciudades</p>;
      break;
      case 'Lugares':
      statusMessage = <p className={styles.p}>  <MdLocationCity className={styles.icon}/> Lugares</p>;
      break;
    
    default:
      statusMessage = '';
  }

  return <div >{statusMessage}</div>;
};

export default IconsSideBar;