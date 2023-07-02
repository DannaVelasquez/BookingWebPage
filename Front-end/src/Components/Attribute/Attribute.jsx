import React from 'react'
import { BsFillCupHotFill} from "react-icons/bs";
import { IoRestaurant , IoBusinessSharp, IoTicket,  IoBusSharp} from "react-icons/io5";
import { IoIosWine } from "react-icons/io";
import { GiBackpack } from "react-icons/gi";
import styles from "./Atribute.module.css"
const Attribute = ({ attr }) => {
  let statusMessage;

  switch (attr) {
    case 'Hotel':
      statusMessage = <p> <IoBusinessSharp className={styles.icon}/> Hotel</p>  ;
      break;
    case 'Transporte':
      statusMessage = <p><IoBusSharp className={styles.icon}/> Transporte</p>;
      break;
    case 'Comida':
      statusMessage = <p> <IoRestaurant className={styles.icon}/> Comida</p>;
      break;
      case 'Bebida':
      statusMessage = <p>  <IoIosWine className={styles.icon}/> Bebida</p>;
      break;
    case 'Desayuno':
      statusMessage = <p><BsFillCupHotFill className={styles.icon}/> Desayuno</p>;
      break;
    case 'Entrada':
      statusMessage = <p> <IoTicket className={styles.icon}/> Entrada</p>;
      break;
      case 'Equipo':
      statusMessage = <p> <GiBackpack className={styles.icon}/> Equipo </p>;
      break;
    
    default:
      statusMessage = '';
  }

  return <div className={styles.containerAttr}>{statusMessage}</div>;
};

export default Attribute;