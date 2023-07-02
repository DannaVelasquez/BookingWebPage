import React, { useRef } from 'react'
import videoBanner from "../../../src/banner.mp4"
import  styles from "./Banner.module.css"

const Banner= () => {


  const videoRef = useRef(null);

  const handleVideoEnded = () => {
    videoRef.current.play();
  };
  return (
    <div>
         <video ref={videoRef} autoPlay muted onEnded={handleVideoEnded} className={styles.video}>
        <source src={videoBanner} type="video/mp4" />
      </video>
    </div>
  )
}

export default 
Banner