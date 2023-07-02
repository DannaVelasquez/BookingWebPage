import { React, useContext } from "react";
import { userContext } from "../GlobalContext/user.context";
import styles from "./Profile.module.css";

const Profile = () => {
  const { userState, userDispatch } = useContext(userContext);
  const savedFirstname = localStorage.getItem('firstName');
  const savedLastname = localStorage.getItem('lastName');
  const savedUsername = localStorage.getItem('username');


  console.log(userState)
  return (
    <div className={styles.profile}>
      <fieldset className={styles.header}>
        <div className={styles.avatar}>
          <span className={styles.initials}>
            {savedFirstname.charAt(0).toUpperCase()}
            {savedLastname.charAt(0).toUpperCase()}
          </span>
        </div>
        <h2 htmlFor="name">
          {savedFirstname + " " + savedLastname}
        </h2>
      </fieldset>
      <h3 className={styles.h3} htmlFor="name">
          Datos de Usuario
        </h3>
      <table className={styles.table}>
        <tbody>
          <tr>
          <td className={styles.td}><label className={styles.label}>Nombre: </label>{savedFirstname} {savedLastname}</td>
          </tr>
          <tr>
          <td className={styles.td}><label className={styles.label}>Email: </label> {savedUsername}</td>
          </tr>
        </tbody>
      </table>
      <h3 className={styles.h3} htmlFor="name">
          Mis Favoritos
        </h3>
    </div>
  );
};

export default Profile;
