import React from 'react';
import AdminCat from './AdminCat';
import AdminExp from './AdminExp';
import AdminAtt from './AdminAtt';
import styles from "./ContentArea.module.css"
import AdminCity from './AdminCity';
import Banner from '../Banner/Banner';
import AdminPlace from './AdminPlaces';

const ContentArea = ({ selectedItem }) => {
  const renderComponent = () => {
    switch (selectedItem) {
      case 0:
        return <AdminExp/>;
      case 1:
        return <AdminCat />;
      case 2:
        return <AdminAtt />;
      case 3:
        return <AdminCity/>
      case 4:
        return <AdminPlace/>
      default:
        return <div>
        <div className={styles.header}>
        <h3 className={styles.h3}>DIGITAL BOOKING</h3>
      </div>
        <Banner />
      </div>
        
    }
  };

  return  <div className={styles.contentArea}>{renderComponent()}</div>;
};

export default ContentArea;
