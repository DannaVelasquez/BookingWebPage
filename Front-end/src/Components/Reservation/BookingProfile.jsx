import React, { useEffect, useContext, useState } from "react";
import { userContext } from "../GlobalContext/user.context";
import styles from "./BookingProfile.module.css";
import { Link, useParams, useNavigate } from "react-router-dom";
import { useContextGlobal } from "../GlobalContext/global.context";
import { FiUsers } from "react-icons/fi";
import { useLocation } from "react-router-dom";
const MisReservas = () => {
  const params = useParams();
  const [reservas, setReservas] = useState([]);
  const { tourState, tourDetState, tourDetDispatch } = useContextGlobal();
  const [username, setUsername] = useState(localStorage.getItem("username") || "");
  const [userId, setUserId] = useState(localStorage.getItem("userId").toString());
  const [rol, setRol] = useState(localStorage.getItem("roles"));
  const userID = userId; 
  const url = `${import.meta.env.VITE_BACKEND_URL}/experiences/get/${params.id}`;
  const urlDetele =`${import.meta.env.VITE_BACKEND_URL}/bookings/remove/`
  const token = localStorage.getItem("token");

  useEffect(() => {
    fetch(url)
      .then((res) => res.json())
      .then((data) =>
        tourDetDispatch({ type: "GET_TOUR_DETAIL", payload: data })
      );
  }, []);

  useEffect(() => {
    fetch(`${import.meta.env.VITE_BACKEND_URL}/bookings/get-booking`, {
      headers: {
        Authorization: `Bearer ${token}`, 
      },
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error("Error en la solicitud");
        }
        return res.json();
      })
      .then((data) => {
        
        const reservasUsuario = data?.data?.filter(       
          (reserva) => reserva?.userID == userId
        );
        setReservas(reservasUsuario);
        console.log(reservasUsuario);
      })
      .catch((error) => {
        console.error(error);
        
      });
  }, []);

  useEffect(() => {
   
    reservas.map((reserva) => {
      console.log(`${import.meta.env.VITE_BACKEND_URL}/experiences/get/${reserva?.experienceID}`);
      fetch(`${import.meta.env.VITE_BACKEND_URL}/experiences/get/${reserva?.experienceID}`, {
        headers: {
          Authorization: `Bearer ${token}`, 
        },
      })
        .then((response) => response.json())
        .then((data) => {
          console.log(data);
          tourDetDispatch({ type: "GET_TOUR_DETAIL", payload: data });
        })
        .catch((error) => {
          console.error(error);
        });
    });
  }, [reservas]); 

  const deleteReserva= (idR)=>{
   
      fetch(`${urlDetele}${idR}`, {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then((res) => {
          if (!res.ok) {
            throw new Error("Error en la solicitud");
          }
          console.log("Solicitud DELETE exitosa");
          window.location.reload()
        })
        .catch((error) => {
          console.error(error);
        });
      
  }
 
  
  
  
  
  
  
  
  console.log(tourDetState);
  console.log(reservas)
  console.log(tourDetState.tourDetail.data);
  console.log(tourDetState.tourDetail.status)
  return (
    <div className={styles.reserva}>
      <div className={styles.section}>
      <h1 className={styles.h1}>Mis Reservas</h1>
      </div>
      {tourDetState && tourDetState.tourDetail.status === "OK" ? (
  <div className={styles.li}>
    {reservas.map((reserva) => (
      
          <div key={reserva.id} className={styles.card}>
            <img
              className={styles.cardImage}
              src={tourDetState.tourDetail.data.images[0]}
              alt={tourDetState.tourDetail.data.title}
            />
            <div>
              <h3 className={styles.h3}>{tourDetState.tourDetail.data.title}</h3>
              <p className={styles.p}>
                <FiUsers/> {reserva.people}
              </p>
              <p className={styles.p}>
                Fecha Inicio: {reserva.startDate}
              </p>
              <p className={styles.p}>
                Fecha Fin: {reserva.endDate}
              </p>
              <button className={styles.button} onClick={()=>{deleteReserva(reserva.id)}}>Cancelar Reserva</button>
            </div>
          </div>
       
    ))}
  </div>
) : (
  <p>No tienes reservas realizadas.</p>
)}

    </div>
  );
  
  

};

export default MisReservas;
