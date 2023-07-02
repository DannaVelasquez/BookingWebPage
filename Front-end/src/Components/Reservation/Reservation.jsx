import React, { useState, useEffect } from 'react';
import styles from "./Reservation.module.css";
import { useContextGlobal } from "../GlobalContext/global.context";
import { useLocation , useNavigate, useParams } from "react-router-dom";
import { FaMapMarkerAlt } from "react-icons/fa";
import {  MdHighlightOff} from "react-icons/md";
import Swal from "sweetalert2";
import { routes } from "../../routes";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import "./CalendarReservation.css"
import { Link } from 'react-router-dom';
import { isWithinInterval } from 'date-fns';






const Reservation = () => {
  const location = useLocation();
  const params = useParams();
  const searchParams = new URLSearchParams(location.search);
  const savedUsername = localStorage.getItem('username');
  const savedFirstname = localStorage.getItem('firstName');
  const savedLastname = localStorage.getItem('lastName');
  const montoPersona = searchParams.get("mount");
  const tokenL = localStorage.getItem('token');
  const userId = localStorage.getItem('userId');
  const [showModal, setShowModal] = useState(false);
  const [showModalCheck, setShowModalCheck] = useState(false);
  const [selectedRange, setSelectedRange] = useState([new Date(), new Date()]);
  const { tourDetState, tourDetDispatch } = useContextGlobal();
  const { tourBookState, tourBookDispatch } = useContextGlobal();

  const navigate = useNavigate();
  const [username, setUsername] = useState(savedUsername);
  const [DNI, setdni] = useState("");
  const [birthDate, setBirthDate] = useState("");
  const [phone, setPhone] = useState("");
  const [comment, setComment] = useState("");
  const [people, setPeople] = useState(1);
  const [mountTotal, setMountTotal] = useState(montoPersona);
  const id = searchParams.get("id");
  const title = searchParams.get("title");
  const description = searchParams.get("description");
  const selecRange = searchParams.get("selectedRange");
  const image = searchParams.get("image")
  const [fecha , setFecha] = useState(selecRange)
 const [termsAccepted , setTermsAccepted] = useState(false)
  const [formCompleted, setFormCompleted] = useState(false);
  const url = `${import.meta.env.VITE_BACKEND_URL}/bookings/save-booking`;
  const urlUpdateUser = `${import.meta.env.VITE_BACKEND_URL}/users/actualize/`
  const urlBook = `${import.meta.env.VITE_BACKEND_URL}/experiences/get-booking/${params.id}`;


  useEffect(() => {
    fetch(urlBook)
      .then((res) => res.json())
      .then((data) =>
        tourBookDispatch({ type: "GET_TOUR_DETAIL", payload: data })
      );
  }, []);

  const urlExp = `${import.meta.env.VITE_BACKEND_URL}/experiences/get/${id}`;
  const urlUser = `${import.meta.env.VITE_BACKEND_URL}/users/get/${userId}`;

  useEffect(() => {
    fetch(urlExp)
      .then((res) => res.json())
      .then((data) =>
        tourDetDispatch({ type: "GET_TOUR_DETAIL", payload: data })
        
      );
  }, []);
  const place = tourDetState?.tourDetail?.data?.place


  
  useEffect(() => {
    fetch(urlUser, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${tokenL}`
      }
    })
      .then((res) => res.json())
      .then((data) => {
    
        setBirthDate(data?.data?.birthDate)
        setdni(data?.data?.document)
        setPhone(data?.data?.cell)
      })
      .catch((error) => {
        console.error("Error en la solicitud:", error);
      });
  }, [urlUser, tokenL]);

 


 
  const handleSubmit = (e) => {
    e.preventDefault();

    const reservation = {
          "people": people ,
          "amount": mountTotal,
          "startDate": rangeReservation[0],
          "endDate": rangeReservation[1],
          "comment": comment,
          "experienceId":  parseInt(id, 10)
    
    }
   const user={
      "birthDate": birthDate,
  
      "cell": phone,
  
      "document": DNI
      }
     
createReservation(reservation)
 console.log(user)
  updateUser(user) 
  };

  const updateUser = (user) => {
    fetch(urlUpdateUser + userId, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${tokenL}`
      },
      body: JSON.stringify(user),
    })
      .then((response) => response.json())
      .then((data) => {
        const message = data;
        console.log(message)
    
      
      })
      .catch((error) => {
        console.error("Error en la solicitud", error);
      });
  };

  const createReservation = (reservation) => {
    fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${tokenL}`
      },
      body: JSON.stringify(reservation),
    })
      .then((response) => response.json())
      .then((data) => {
        const message = data;
        console.log(message)
      
      })
      .catch((error) => {
        console.error("Error en la solicitud", error);
      });
  };
  
  const handleInputChange = (e) => {
    const isFormComplete = (!termsAccepted 
      && DNI.trim() !== ''
      && phone.trim() !== ''
      && birthDate.trim() !== ''
      && username.trim() !== '') ||
     (e.target.name === 'name' 
     && e.target.name === 'dni'
     && e.target.name === 'birthDate' 
     && e.target.name === 'phone'
      &&e.target.value.trim() !== '');
    setFormCompleted(isFormComplete);
  };
 

  
  const buttonClassName = formCompleted ? styles['button'] : styles['buttonDisabled'];

  const handleModalOpenCheck = () => {
   
    setShowModalCheck(true);
  };

  const handleModalCloseCheck = () => {
    setShowModalCheck(false);
  };
  const handleModalOpen = () => {
    setShowModal(true);
  };

  const handleModalClose = () => {
    setShowModal(false);
  };
  const cutText = (text) => {
    const maxLength = 80
    if (text.length <= maxLength) {
      return text;
    } else {
      const cutText = text.slice(0, maxLength);
      return <p onClick={handleModalOpen} className={styles.cutText}>{cutText + '...'}</p> 
    }
  };
 
  const formatRange = (range) => {
      const options = { year: 'numeric', month: 'numeric', day: 'numeric' };
      const formattedStart = range[0].toLocaleDateString('es-ES', options);
      const formattedEnd = range[1].toLocaleDateString('es-ES', options);
      
      return `${formattedStart} - ${formattedEnd}`;
    };
    const formatRan = (range) => {
      const options = { year: 'numeric', month: 'numeric', day: 'numeric' };
      const formattedStart = range[0].toLocaleDateString('es-ES', options);
      const formattedEnd = range[1].toLocaleDateString('es-ES', options);
      
      return [ `${formattedStart}`,`${formattedEnd}`]
    };
   const rangeReservation = formatRan(selectedRange)

   const formatoFecha = (fecha) => {
    const [dia, mes, anio] = fecha.split('/');
    return new Date(anio, mes - 1, dia);
  };



  
  const esFechaDeshabilitada = (date) => {
    const rangosDeshabilitados = tourBookState?.tourDetail?.data?.map((item) => {
      return [formatoFecha(item.dayStart), formatoFecha(item.dayEnd)];
    });
  
    return rangosDeshabilitados && rangosDeshabilitados.some((disabledRange) =>
      isWithinInterval(date, { start: disabledRange[0], end: disabledRange[1] })
    );
  };


   const handleRangeChange = (range) => {
    setSelectedRange(range);
    setFecha(formatRange(range))
  };
  const handleDecrease = () => {
    if (people > 1) {
      setPeople(people - 1);
      setMountTotal((people - 1) * montoPersona)
    }
  };
 
  const handleIncrease = () => {
    
      setPeople(people + 1);

    setMountTotal((people + 1) * montoPersona)
     
  };

  return (
    <div className={styles.reservation} >
      <div className={styles.section}>
      <h1 className={styles.h1}>Confirmación de la reserva </h1>
        <h3 className={styles.h3R}>La experiencia que quieres reservar</h3>
        <div className={styles.reservation}> 
          <div className={styles.conteinerRe1}>
               <p> {`Monto por persona: ${montoPersona}`}  </p>
               
                <div className={styles.nameInput}>
                <p>Cantidad de personas: </p>   
             
        <p className={styles.buttonPeople} onClick={handleDecrease}>-</p>
        <p className={styles.people}>{` ${people} `}</p>
        <p className={styles.buttonPeople}  onClick={handleIncrease}>+</p>
      </div>
      <div>
          
          </div>
                <p>{`Monto total: ${mountTotal}`  }</p>



           <p className={styles.pDay}>Dias de la reserva: {fecha}</p>

          </div>
            <div  className={styles.conteinerRe1}>
            <div className={styles.card}>
           <img className={styles.cardImage} src={image} alt="" />
         <div>
        <h3 className={styles.h3}>{title}</h3>
        <p className={styles.p}><FaMapMarkerAlt/> {place?.city}, {place?.country}</p>
      {showModal ? (
        <div className={styles.modal}>
      <div className={styles.modalContent}>
      <button className={styles.closeButton} onClick={handleModalClose}>
        <MdHighlightOff/>
      </button>
      <div className={styles.fullText}>{description}</div>
    </div>
  </div>
) : (
     <div >
       <div className={styles.viewMore}>
        {cutText(description) } 
       </div>
       </div>
)}
</div>
        </div>

            </div>
      

        </div>
        <h3 className={styles.h3R}>Datos personales</h3>
        <div className={styles.reservation}>
       
          <div className={styles.conteinerRe1}>
         
       
        <form >
        <fieldset className={styles.fieldset}>
        <div className={styles.nameInput}>
            <p>Nombre y Apellido</p><p className={styles.requerid}>*</p>
            </div>
            <label className={styles.label} htmlFor="name"></label>
            <input
              className={styles.input}
              type="text"
              name="name"
              value={ `${savedFirstname} ${savedLastname}`}
            />
          </fieldset>
          <fieldset className={styles.fieldset}>
          <div className={styles.nameInput}>
            <p>Email</p><p className={styles.requerid}>*</p>
            </div>
            <label className={styles.label} htmlFor="name"></label>
            <input
              className={styles.input}
              type="text"
              name="name"
              placeholder="Ingrese su email"
              value={username}
              onChange={(e) => {
                setUsername(e.target.value);
                handleInputChange(e);
              }}
              required
          
            />
          </fieldset>
           <fieldset className={styles.fieldset}>
          <div className={styles.nameInput}>
            <p>DNI</p><p className={styles.requerid}>*</p>
            </div>
            <label className={styles.label} htmlFor="name"></label>
            <input
              className={styles.input}
              type="text"
              name="dni"
              placeholder="Ingrese su DNI"
              required
              value={DNI}
              onChange={(e) => {
                setdni(e.target.value);
                handleInputChange(e);
              }}
            />
          </fieldset>
       
          <fieldset className={styles.fieldset}>
          <div className={styles.nameInput}>
            <p>Fecha de Nacimiento</p><p className={styles.requerid}>*</p>
            </div>
            <label className={styles.label} htmlFor="name"></label>
            <input
              className={styles.input}
              type="text"
              name="birthDate"
              placeholder="Ingrese su fecha de nacimiento"
              value={birthDate}
              required
              onChange={(e) => {
                setBirthDate(e.target.value);
                handleInputChange(e);
              }}
            />
          </fieldset>
          <fieldset className={styles.fieldset}>
            <div className={styles.nameInput}>
            <p>Teléfono</p><p className={styles.requerid}>*</p>
            </div>
            <label className={styles.label} htmlFor="name"></label>
            <input
              className={styles.input}
              type="text"
              name="phone"
              placeholder="Ingrese su teléfono"
              value={phone}
              onChange={(e) => {
                setPhone(e.target.value);
                handleInputChange(e);
              }}
              required
            />
          </fieldset> 
          <fieldset className={styles.fieldset}>
            <p >Pedidos Especiales</p>
            <label className={styles.label} htmlFor="name"></label>
            <input
              className={styles.input}
              type="text"
              name="name"
              placeholder="Ingrese aqui si necesita hacer un pedido especial"
              value={comment}
              onChange={(e) => {
                setComment(e.target.value);
              
              }}
            />
          </fieldset>
          </form>
          </div>
          <div className={styles.conteinerRe2}>

        
        
        <h3>Fechas Disponibles</h3>
        <Calendar
          className={styles.calendarDesk}v
          showDoubleView={true}
          selectRange={true}
          onChange={handleRangeChange}
          value={selectedRange}
          tileDisabled={({ date }) => esFechaDeshabilitada(date)}
          tileClassName={({ date }) =>
          esFechaDeshabilitada(date) ? "disabled" : ""}
        />

        <Calendar
          className={styles.calendarMovile}
          selectRange={true}
          onChange={handleRangeChange}
          value={selectedRange}
    
        />
          </div>
          
        </div>

 

  {showModalCheck ? (
        <div className={styles.modalCheck} >
      <div className={styles.modalContentCheck}>
      <p className={styles.closeButtonModalCheck} onClick={handleModalCloseCheck}>
        <MdHighlightOff/>
      </p>
      <div className={styles.fullTextCheck}> 
      
          <h2>Salud y Seguridad</h2>
         <p>{tourDetState?.tourDetail?.data?.politics?.healthAndSafety}</p>
      
        
          <h2>Reglas del lugar</h2>
           <p>{tourDetState?.tourDetail?.data?.politics?.houseRules}</p>
       
        
          <h2>Politicas de cancelación</h2>
          <p>{tourDetState?.tourDetail?.data?.politics?.cancellationPolicies}</p>
          </div>
          </div>
  </div>
) : (
     <div >
       <div>
             
  <fieldset className={styles.fieldsetCheck}>
  
      <input
        type="checkbox"
        name="termsAccepted"
        checked={termsAccepted} 
        required
        onChange={(e) => {
          setTermsAccepted(e.target.checked);
          console.log("boton check" + termsAccepted)
          handleInputChange(e);
        }}
      />
     <p className={styles.inputCheck} onClick={handleModalOpenCheck}>  He leído y acepto las politicas de reserva </p>
  
  </fieldset>
       </div>
       </div>
)}



<button
  className={buttonClassName}
  type="submit"
  onChange={handleSubmit}
  disabled={!formCompleted}
  onClick={(e)=>{navigate(routes.reservationConfirm) ; e.preventDefault();
    handleSubmit(e)}}
>
  Reservar
</button>
       
      </div>
    </div>
  );
};

export default Reservation;