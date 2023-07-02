import { React, useState } from "react";
import styles from "./Register.module.css";
import Swal from "sweetalert2";
import { Link } from "react-router-dom";
import { routes } from "../routes";

const Register = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [repeatedPassword, setRepeatedPassword] = useState("");
  const [firstname, setFirsname] = useState("");
  const [lastname, setLastname] = useState("");
  const url = `${import.meta.env.VITE_BACKEND_URL}/users/register`;

  const handleSubmit = (e) => {
    e.preventDefault();

    const user = {
      username: username,
      password: password,
      repeatedPassword: repeatedPassword,
      firstname: firstname,
      lastname: lastname,
    };
    console.log(user);
    createUser(user);
  };
console.log(url)
  const createUser = (user) => {
    fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(user),
    })
      .then((response) => response.json())
      .then((data) => {
        const message = data.message;
        console.log(message)
        if (message === "Success") {
          Swal.fire({
            position: "center",
            icon: "success",
            text: "El usuario ha sido creado con éxito. Le enviamos un mail para confirmar",
   
            showConfirmButton: true,
            confirmButtonColor: "#1dbeb4",
            customClass: {
              confirmButton: styles.confirmButton,
            },
          });
          console.log("usuario creado");
        }
        if (message === "El correo electronico ya esta registrado") {
          Swal.fire({
            position: "center",
            icon: "warning",
            text:
              "El correo " +
              user.username +
              " ya se encuentra registrado.",
            showConfirmButton: true,
            confirmButtonColor: "#1dbeb4",
            customClass: {
              confirmButton: styles.confirmButton,
            },
          });
        }
      })
      .catch((error) => {
        console.error("Error en la solicitud", error);
      });
  };

  return (
    <div className={styles.register} >
            <div className={styles.containerHeader}>
       <Link to={routes.home}>
          <img className={styles.logo1} src={window.location.origin + "/images/logoDB.png"} alt="Logo" />
        </Link>
     <br />
     <br />
    
     <Link to={routes.home}>
     <p className={styles.pHeader}>Un destino, mil recuerdos</p>
        </Link>
    
             </div>
      <div className={styles.header}>
        <h3 className={styles.h3}>Bienvenido</h3>
      </div>
      <form className={styles.section} onSubmit={handleSubmit}>
        <div>
          <h1 className={styles.h1}>Registrarse</h1>
        </div>
        <div className={styles.section}>
          <fieldset className={styles.fieldset}>
            <p className={styles.p}>Email</p>
            <label className={styles.label} htmlFor="name"></label>
            <input
              className={styles.input}
              type="email"
              name="name"
              placeholder="Ingrese su email."
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </fieldset>
          <fieldset className={styles.fieldset}>
            <p className={styles.p}>Contraseña</p>
            <label className={styles.label} htmlFor="name"></label>
            <input
              className={styles.input}
              type="password"
              name="name"
              placeholder="Ingrese una contraseña"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&./#,:;~=!()])[A-Za-z\d@$!%*?&./#,:;~=!()]{8,}$" title="Password debe contener mí­nimo 8 caracteres con letras, mayúscula, números, caracteres especiales (@!%&./#,:;~=!)"
            />
          </fieldset>
          <fieldset className={styles.fieldset}>
            <p className={styles.p}>Repetir Contraseña</p>
            <label className={styles.label} htmlFor="name"></label>
            <input
              className={styles.input}
              type="password"
              name="name"
              placeholder="Repita su contraseña"
              value={repeatedPassword}
              onChange={(e) => setRepeatedPassword(e.target.value)}
              required
              pattern={password} title="Contraseña no coincide con la anterior, vuelva a intentar"
            />
          </fieldset>
          <fieldset className={styles.fieldset}>
            <p className={styles.p}>Nombre</p>
            <label className={styles.label} htmlFor="name"></label>
            <input
              className={styles.input}
              type="text"
              name="name"
              placeholder="Ingrese su nombre"
              value={firstname}
              onChange={(e) => setFirsname(e.target.value)}
              required
              minLength={2}
            />
          </fieldset>
          <fieldset className={styles.fieldset}>
            <p className={styles.p}>Apellido</p>
            <label className={styles.label} htmlFor="name"></label>
            <input
              className={styles.input}
              type="text"
              name="name"
              placeholder="Ingrese su apellido"
              value={lastname}
              onChange={(e) => setLastname(e.target.value)}
              required
              minLength={2}
            />
          </fieldset>
          <br />
          <button className={styles.button} type="submit">
            Registrarme
          </button>
          <Link to={routes.login}>
              <p className={styles.pLink}>¿Ya tienes cuenta? Ingresa aquí</p>
          </Link>
        </div>
      </form>
    </div>
  );
};

export default Register;
