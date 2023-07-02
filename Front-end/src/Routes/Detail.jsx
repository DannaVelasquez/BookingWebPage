import { React, useEffect, useState} from "react";
import { Link, useParams, useNavigate  } from "react-router-dom";
import { useContextGlobal } from "../Components/GlobalContext/global.context";
import { SlArrowLeft } from "react-icons/sl";
import "react-responsive-carousel/lib/styles/carousel.min.css";
import { Carousel } from "react-responsive-carousel";
import Attribute from "../Components/Attribute/Attribute";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import styles from "./Detail.module.css";
import "./Calendar.css";
import "../Carousel.css";
import Location from "../Components/Location/Location";
import { routes } from "../routes";
import { isWithinInterval } from 'date-fns';
import { FaMapMarkerAlt } from "react-icons/fa";
const Detail = () => {
  const params = useParams();
  const [showMore, setShowMore] = useState(false);
  const [selectedRange, setSelectedRange] = useState([new Date(), new Date()]);
  const { tourDetState, tourDetDispatch } = useContextGlobal();
  const { tourBookState, tourBookDispatch } = useContextGlobal();
  const navigate = useNavigate();
  const title = tourDetState?.tourDetail?.data?.title;
  const description = tourDetState?.tourDetail?.data?.description;
  const tourId = tourDetState?.tourDetail?.data?.id;
  const token = localStorage.getItem("token")
  const url = `${import.meta.env.VITE_BACKEND_URL}/experiences/get/${params.id}`;
  const urlBook = `${import.meta.env.VITE_BACKEND_URL}/experiences/get-booking/${params.id}`;

console.log(tourBookState?.tourDetail?.data)
console.log(tourId)
console.log(tourBookState)

  const place = tourDetState?.tourDetail?.data?.place
  const montoPersona = tourDetState?.tourDetail?.data?.amountForPerson
  
  const formatRange = (range) => {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    const formattedStart = range[0].toLocaleDateString('es-ES', options);
    const formattedEnd = range[1].toLocaleDateString('es-ES', options);
    
    return `${formattedStart} - ${formattedEnd}`;
  };
 const formattedRange = formatRange(selectedRange)


 console.log(formattedRange)
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


  useEffect(() => {
    fetch(url)
      .then((res) => res.json())
      .then((data) =>
        tourDetDispatch({ type: "GET_TOUR_DETAIL", payload: data })
      );
  }, []);

  useEffect(() => {
    fetch(urlBook)
      .then((res) => res.json())
      .then((data) =>
        tourBookDispatch({ type: "GET_TOUR_DETAIL", payload: data })
      );
  }, []);

  const handleShowMore = () => {
    setShowMore(true);
  };
  const handleShowLess = () => {
    setShowMore(false);
  };
  const handleRangeChange = (range) => {
    setSelectedRange(range);
  };

  const handleReserveClick = () => {
   const id = params.id
    navigate(`/reservation?id=${id}&title=${title}&description=${description}&selectedRange=${formattedRange}&image=${tourDetState.tourDetail.data.images[0]}&mount=${montoPersona}`);
  };

  const alert = ()=>{
    Swal.fire({
      position: "center",
      icon: "error",
      title: "Lo sentimos",
      text: "Los datos ingresados no son válidos. Verifique nuevamente por favor",
      showConfirmButton: true,
      confirmButtonColor: "#1dbeb4",
      customClass: {
        confirmButton: styles.confirmButton,
      },})
  }

    

  return (
    <div className={styles.detail}>
      <div className={styles.det}>
        <h2 className={styles.h2}>{tourDetState?.tourDetail?.data?.title}</h2>
        <Link to={"/"}>
          <span className={styles.flecha}>
            <SlArrowLeft />
          </span>
        </Link>
      </div>
      <p className={styles.place}> <FaMapMarkerAlt/>{place?.city}, {place?.country} </p>
      {!showMore ? (
        <div>
          <div className={styles.container}>
            {tourDetState &&
              tourDetState.tourDetail &&
              tourDetState.tourDetail.data &&
              tourDetState.tourDetail.data.images && (
                <div className={styles.cont1}>
                  {tourDetState.tourDetail.data.images.length > 0 && (
                    <img
                      src={tourDetState.tourDetail.data.images[0]}
                      className={styles.singleImage}
                    />
                  )}
                </div>
              )}
            {tourDetState &&
              tourDetState.tourDetail &&
              tourDetState.tourDetail.data &&
              tourDetState.tourDetail.data.images && (
                <div className={styles.cont1}>
                  <div className={styles.grid}>
                    {tourDetState.tourDetail.data.images
                      .slice(1, 5)
                      .map((image, index) => (
                        <img key={index} src={image} className={styles.image} />
                      ))}
                  </div>
                </div>
              )}
          </div>

          <p className={styles.buttonImg} onClick={handleShowMore}>
            ver mas
          </p>
        </div>
      ) : (
        <div className={styles.container2}>
          <Carousel>
            {tourDetState.tourDetail.data.images.map((image, index) => (
              <div>
                <img key={index} src={image} />
              </div>
            ))}
          </Carousel>

          <p className={styles.buttonImg} onClick={handleShowLess}>
            ver menos
          </p>
        </div>
      )}

      {tourDetState &&
        tourDetState.tourDetail &&
        tourDetState.tourDetail.data &&
        tourDetState.tourDetail.data.images && (
          <div className={styles.containerCarrouselMovile}>
            <Carousel>
              {tourDetState.tourDetail.data.images.map((image, index) => (
                <div>
                  <img key={index} src={image} />
                </div>
              ))}
            </Carousel>
          </div>
        )}

      <h3 className={styles.h3}>Descripción</h3>
      <hr />
      <p className={styles.p}>{tourDetState?.tourDetail?.data?.description}</p>
      <hr />

      <h3 className={styles.h3}>¿Qué incluye?</h3>

      <p className={styles.at}>
        {tourDetState?.tourDetail?.data?.attributes?.map((item) => (
            <Attribute attr={item.name} key={item.id} />
          ))}
      </p>

      <div className={styles.container4}>
        <h3>Fechas Disponibles</h3>
        <Calendar
          className={styles.calendarDesk}
          showDoubleView={true}
          selectRange={true}
          onChange={handleRangeChange}
          value={selectedRange}
          tileDisabled={({ date }) => esFechaDeshabilitada(date)}
          tileClassName={({ date }) =>
    esFechaDeshabilitada(date) ? "disabled" : ""
  }
        />

        <Calendar
          className={styles.calendarMovile}
          selectRange={true}
          onChange={handleRangeChange}
          value={selectedRange}
    
        />

        <div className={styles.sectionReservation}>
          <p>Agregá tus fechas de viaje para obtener precios exactos</p>
          
          {token ? (
            <button className={styles.sectionReservationButton} onClick={() => {navigate(routes.reservation); handleReserveClick()}}>
            Iniciar Reserva
          </button>
          ): 
          <button className={styles.sectionReservationButton} onClick={() => {navigate(routes.login)}}>
              Iniciar Reserva
            </button>}

        </div>
      </div>

      <div >
        <h3 className={styles.h3}>¿Dónde vas a estar?</h3>
         <Location /> 
      
       
      </div>
      <div className={styles.politicsConteiner}>
        <h3 className={styles.h3}>¿Que tenés que saber?</h3>
        <div className={styles.politics}>

        
        <div className={styles.divPol} >
          <h2>Salud y Seguridad</h2>
         <p>{tourDetState?.tourDetail?.data?.politics?.healthAndSafety}</p>
        </div>
        <div className={styles.divPol}>
          <h2>Reglas del lugar</h2>
           <p>{tourDetState?.tourDetail?.data?.politics?.houseRules}</p>
        </div>
        <div className={styles.divPol}>
          <h2>Politicas de cancelación</h2>
          <p>Agregá las fechas de tu experiencia <br/> para obtener los detalles de cancelación</p>
        </div>
        </div>
      </div>
    </div>
  );
};

export default Detail;
