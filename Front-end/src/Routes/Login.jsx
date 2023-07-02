import { React, useContext, useState, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import { routes } from "../routes";
import styles from "./Login.module.css";
import Swal from "sweetalert2";
import { userContext } from "../Components/GlobalContext/user.context";

const Login = () => {
  const { userState, userDispatch } = useContext(userContext);
  const [username, setUsername] = useState(localStorage.getItem("username") || "");
  const [password, setPassword] = useState("");
  const [firstName, setFirstName] = useState(localStorage.getItem("firstName") || "");
  const [lastName, setLastName] = useState(localStorage.getItem("lastName") || "");
  const [token, setToken] = useState(localStorage.getItem("token") || "");
  const [rol, setRol] = useState(localStorage.getItem("rol")|| "")
  const navigate = useNavigate();

  useEffect(() => {
    localStorage.setItem("username", username);
  }, [username]);

  useEffect(() => {
    localStorage.setItem("rol", rol);
  }, [rol]);

  

  useEffect(() => {
    localStorage.setItem("token", token);
  }, [token]);
  useEffect(() => {
    localStorage.setItem("firstName", firstName);
  }, [firstName]);
  useEffect(() => {
    localStorage.setItem("lastName", lastName);
  }, [lastName]);

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const loginData = {
        username: username,
        password: password,
      };
      const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/authenticate?token=${token}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(loginData),
      });

      if (response.ok) {
       
        const data = await response.json();
      
        setToken(data?.data?.token);
        localStorage.setItem("token", data?.data?.token);
        localStorage.setItem("userId", data?.data?.userId);
   
        localStorage.setItem("firstName", data?.data?.firstName)
        setLastName(data?.data?.lastName)
        localStorage.setItem("lastName", data?.data?.lastName);
        setRol(data?.data?.roles[0].name)
        localStorage.setItem("roles", data?.data?.roles[0].name);
       
        userDispatch({
          type: "LOGIN",
          payload: {
            user: data,
          },
        });

       

        if (data?.data?.roles[0].name === "ROLE_USER") {
          navigate("/products");
        } else {        
          navigate(routes.dashboard);
        }
      } else {
        Swal.fire({
          position: "center",
          icon: "error",
          title: "Lo sentimos",
          text: "Los datos ingresados no son válidos. Verifique nuevamente por favor",
          showConfirmButton: true,
          confirmButtonColor: "#1dbeb4",
          customClass: {
            confirmButton: styles.confirmButton,
          },
        });
        userDispatch({
          type: "LOGIN_FAILURE",
          payload: {
            error: data.error,
          },
        });
      }
    } catch (error) {
      console.log(error);
    }
  };
 
 

  return (
    <div className={styles.loginPage}>
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
        {/* <Link to={routes.adminProducts}>
          <button >Admin</button>
        </Link> */}
      </div>

      <form className={styles.section} onSubmit={handleLogin}>
        <div>
          <h1 className={styles.h1}>Iniciar Sesión</h1>
        </div>
        <div className={styles.section}>
          <fieldset className={styles.fieldset}>
            <p className={styles.p}>Email</p>
            <label className={styles.label} htmlFor="name"></label>
            <input
              className={styles.input}
              type="email"
              id="username"
              placeholder="Ingrese su email"
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
              id="password"
              placeholder="Ingrese su contraseña"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&./#,:;~=!()])[A-Za-z\d@$!%*?&./#,:;~=!()]{8,}$" title="Password debe contener mí­nimo 8 caracteres con letras, mayúscula, números, caracteres especiales (@!%&./#,:;~=!)"
            />
          </fieldset>
          <br />
          <button className={styles.button} onClick={handleLogin}>
            Iniciar sesión
          </button>

          <Link to={routes.register}>
              <p className={styles.pLink}>¿No tienes cuenta? registrate aquí</p>
          </Link>
        </div>
        
      </form>
      
      
    </div>
  );
};

export default Login;
