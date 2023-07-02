import axios from "axios";
import { useEffect, useState } from "react";
import styles from "./EmailConfirmation.module.css"
import { AiFillLike } from "react-icons/ai";
import { TiTime } from "react-icons/ti";
import { Link } from "react-router-dom";
import { routes } from "../routes";
import { useSpring, animated } from "react-spring";




function EmailConfirmation() {

  const [confirmed, setConfirmed] = useState(false);

  useEffect(() => {
    // Obtener el token de la URL
    const urlParams = new URLSearchParams(window.location.search);
    const token = urlParams.get('token');
    const url = `${import.meta.env.VITE_BACKEND_URL}/users/register/confirm/`;
    // Realizar la solicitud al backend


    axios
      .post(url + token)
      .then(response => {
        setTimeout(() => {
          if (response.status === 200) {
            setConfirmed(true);
          }
        }, 3000);
      })
      .catch(error => {
        console.error(error);
      });
  }, []);


  const stylesAnimation = useSpring({
    loop: true,
    to: { transform: "rotate(360deg)" },
    from: { transform: "rotate(0deg)" },
    config: { duration: 1000 },
  });



  return (
    <div className={styles.container} >
      <div className={styles.container2}>

        <img className={styles.logo1} src={window.location.origin + "/images/logoDB.png"} alt="Logo" />

        <br />
        <br />

        <p className={styles.p}>Un destino, mil recuerdos</p>

      </div>
      <div className={styles.contenedorMail}>
        {confirmed ? (
          <div className={styles.contenedorMailConfirmation}>
            <AiFillLike className={styles.logoTransition1} />
            <h1>Correo electrónico confirmado</h1>

            <Link to={routes.login}>
              <button className={styles.buttons}>Iniciar Sesión</button>
            </Link>
          </div>

        ) : (
          <div className={styles.contenedorMailConfirmation}>
            <animated.div style={stylesAnimation}>
              <TiTime className={styles.logoTransition} />
            </animated.div>

            <h1>Confirmando correo electrónico...</h1>
          </div>

        )}
      </div>

    </div>
  );
}

export default EmailConfirmation;