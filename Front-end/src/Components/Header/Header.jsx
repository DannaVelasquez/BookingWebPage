import { React, useState, useContext, useEffect } from "react";
import { Link } from "react-router-dom";
import { useParams } from "react-router-dom";
import { routes } from "../../routes";
import styles from "./Header.module.css";
import { useNavigate } from "react-router-dom";
import { FaBars, FaTimes } from "react-icons/fa";
import { userContext } from "../GlobalContext/user.context";

const Header = () => {
  const navigate = useNavigate();
  const params = useParams();
  const [showMenu, setShowMenu] = useState(false);
  const [showMenuAdm, setShowMenuAdm] = useState(false);
  const { userState, userDispatch } = useContext(userContext);
  
  
  const firstName = localStorage.getItem("firstName");
  const lastName = localStorage.getItem("lastName");
  const roles = localStorage.getItem("roles");
  const token = localStorage.getItem("token")


  const initials =
    firstName && lastName ? `${firstName.charAt(0).toUpperCase()}${lastName.charAt(0).toUpperCase()}` : "";

  
  const toggleMenu = () => {
    setShowMenu(!showMenu);
  };

  const toggleMenuAdm = () => {
    setShowMenuAdm(!showMenuAdm);
  };


  const toggleHideMenuAdm = () => {
    setShowMenuAdm(false);
  };

  const handleLogout = () => {
    userDispatch({ type: "LOGOUT" });
    localStorage.removeItem("token"); 
    localStorage.removeItem("username");
    localStorage.removeItem("password");
    localStorage.removeItem("roles");
    localStorage.removeItem("firstName");
    localStorage.removeItem("lastName")
    navigate("/");
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      userDispatch({
        type: "LOGIN",
        payload: {
          user: {
            data: {
              firstName: localStorage.getItem("firstName"),
              lastName: localStorage.getItem("lastName"),
            },
            
          },
        },
      });
    }
    
  }, [userDispatch]);

  console.log(showMenuAdm)

  return (
    <div className={styles.header}>
      <div className={styles.container}>
        <Link to={routes.home}>
          <img
            className={styles.logo}
            src={window.location.origin + "/images/logoDB.png"}
            alt="Logo"
          />
        </Link>
        <br />
        <br />
        <Link to={routes.home}>
          <p className={styles.p}>Un destino, mil recuerdos</p>
        </Link>
      </div>
      {token ?  (
        <>
          <header className={styles.login}>
            <p className={styles.avatar} onClick={toggleMenuAdm}>
            {showMenuAdm}
            <span className={styles.initials}>{initials}</span>
            </p>
            </header>
            { showMenuAdm && roles=="ROLE_ADMIN"? (
          <div className={styles.listMenu}>
            <h3 className={styles.h3}>{firstName.toUpperCase() + ' ' + lastName.toUpperCase()}</h3>
            <p className={styles.rol}>Administrador</p>
            <Link to={routes.profile}>
              <button className={styles.buttonMenu} onClick={toggleHideMenuAdm}>Mi Perfil</button>
            </Link>
            <Link to={routes.dashboard}>
              <button className={styles.buttonMenu} onClick={toggleHideMenuAdm}>Panel Admin</button>
            </Link>
            <button className={styles.buttonMenu} onClick={handleLogout}>Cerrar Sesión</button>
          </div>
        ) : showMenuAdm && roles== "ROLE_USER" ?(
          <div className={styles.listMenu}>
            <h3 className={styles.h3}>{firstName.toUpperCase()}</h3>
            <p className={styles.rol}>Usuario</p>
            <Link to={routes.profile}>
              <button className={styles.buttonMenu} onClick={toggleHideMenuAdm}>Mi Perfil</button>
            </Link>
            <Link to={routes.misReservas}>
            <button className={styles.buttonMenu} onClick={toggleHideMenuAdm}>Mis Reservas</button>
            </Link>
            <button className={styles.buttonMenu} onClick={handleLogout}>Cerrar Sesión</button>
          </div>
        ):null}
        </>
      ) : (
        <div className={styles.menuDesk}>
          <Link to={routes.register}>
            <button className={styles.buttons}>Crear Cuenta</button>
          </Link>
          <Link to={routes.login}>
            <button className={styles.buttons}>Iniciar Sesión</button>
          </Link>
        </div>
      )}
       

      {/* Menú hamburguesa */}
      {!token ? (
      <div className={styles.containerHamburger}>
        <button className={styles.hamburger} onClick={toggleMenu}>
          {showMenu ? <FaTimes /> : <FaBars />}
        </button>

        {showMenu ? (
          <div className={styles.listMenuHam}>
            <Link to={routes.register}>
              <button className={styles.buttons}>Crear Cuenta</button>
            </Link>
            <Link to={routes.login}>
              <button className={styles.buttons}>Iniciar Sesión</button>
            </Link>
          </div>
        ) : null}
      </div>):null}
    </div>
  );
};

export default Header;
