import React from 'react';
import styles from "./SideBar.module.css"
import { useState } from 'react';
import { FaBars } from 'react-icons/fa';
import IconsSideBar from './IconosSideBar';

const Sidebar = ({ onItemClick }) => {
  const options = [ 'Tours', 'Categorias', 'Servicios', 'Ciudades', 'Lugares'];
  const [showSideBar, setShowSideBar] = useState(true)

  const toggleSideBar = () => {
    setShowSideBar(!showSideBar)
  }

  return (
    <div className={`${styles.sidebar} ${showSideBar ? '' : styles.sidebarHide}`}>
      <button className={styles.toggleButton} onClick={toggleSideBar}>
        <FaBars /> 
      </button>
      
      <ul className={styles.sidebarList}>
        {options.map((option, index) => (
          <li key={index} onClick={() => onItemClick(index)} className={styles.sidebarItem}>
            <IconsSideBar icon={option}/>
          </li>
        ))}
      </ul>
      
      
    </div>
  );
};

export default Sidebar;
