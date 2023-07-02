import React, { useState, useEffect } from "react";
import styles from "./ConfirmReserv.module.css";
import { AiFillLike } from "react-icons/ai";
import { useSpring, animated } from "react-spring";
import { Link } from "react-router-dom";
import { routes } from "../../routes";
import { FiCheck } from "react-icons/fi";


function ConfirmReserv() {

    const [showLikeIcon, setShowLikeIcon] = useState(false);

    useEffect(() => {
        const timer = setTimeout(() => {
          setShowLikeIcon(true);
        }, 1500); // Cambiar a mostrar el icono después de 3 segundos (3000 ms)
    
        return () => {
          clearTimeout(timer); // Limpiar el temporizador cuando el componente se desmonte
        };
      }, []);
    
      const slideUp = useSpring({
        from: { transform: "translateY(0)" },
        to: { transform: showLikeIcon ? "translateY(-100%)" : "translateY(0)" },
      });

  

  return (
    <div className={styles.container}>
    <div className={styles.contenedorMail}>
      <div className={styles.contenedorMailConfirmation}>
        {showLikeIcon ? (
          <animated.div className={styles.iconContainer} >
            <AiFillLike className={styles.logoTransition1} />
          </animated.div>
        ) : (
          <animated.img
            className={`${styles.logo1} ${styles.fadeIn}`}
            src={window.location.origin + "/images/logoDB.png"}
            alt="Logo"
            
          />
        )}
        <h1>Reserva Exitosa!</h1>
        <h2 className={styles.h2}>Gracias por preferirnos</h2>
        <br />
        <Link to={routes.home}>
          <button className={styles.button}>Volver al Home</button>
        </Link>
      </div>
    </div>
  </div>
  );
}

export default ConfirmReserv;