import React from "react";
import styles from "./Footer.module.css";

const Footer = () => {
  return (
    <footer className={styles.footer}>
      <p className={styles.p}>©2023 Digital Booking</p>
      <div className={styles.container}>
        <img
          className={styles.icon}
          src={window.location.origin + "/images/facebook.png"}
          alt="logo-facebook"
        />
        <img
          className={styles.icon}
          src={window.location.origin + "/images/linkedin.png"}
          alt="logo-linkedin"
        />
        <img
          className={styles.icon}
          src={window.location.origin + "/images/twitter.png"}
          alt="logo-twitter"
        />
        <img
          className={styles.icon}
          src={window.location.origin + "/images/instagram.png"}
          alt="logo-instagram"
        />
      </div>
    </footer>
  );
};

export default Footer;
