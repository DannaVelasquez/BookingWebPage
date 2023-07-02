import React, {useState} from 'react';
import AdminExp from './AdminExp';
import AdminCat from './AdminCat';
import styles from "./Dashboard.module.css";
import { Link } from "react-router-dom";
import { routes } from "../../routes";
import Sidebar from './SideBar';
import ContentArea from './ContentArea';


const Dashboard = () => {
    const [selectedItem, setSelectedItem] = useState(null);

  const handleItemClick = (index) => {
    setSelectedItem(index);
  };

  return (
    <div className={styles.dashboardContainer} >
        <div >
        <Sidebar onItemClick={handleItemClick}/>
        </div>
      <div className={styles.contentContainer}>
      <ContentArea selectedItem={selectedItem}/>
      </div>
      
      
    </div>
  );
};

export default Dashboard;
