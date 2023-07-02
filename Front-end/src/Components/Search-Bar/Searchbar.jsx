import { Link } from "react-router-dom";
import styles from "./Searchbar.module.css";
import React, { useState, useEffect } from "react";
import DatePicker from "react-datepicker";
import Select, {components} from 'react-select';
import {FiX} from 'react-icons/fi'
import "react-datepicker/dist/react-datepicker.css";
import "./calendario.css";
import { format } from 'date-fns';

const Searchbar = () => {
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [idCity, setIdCity] = useState([]);
  const [selectedCity, setSelectedCity] = useState(null);
  const urlCity = `${import.meta.env.VITE_BACKEND_URL}/cities/get-all`;
  const urlSearch = `${import.meta.env.VITE_BACKEND_URL}/experiences/search`;
  
//   URL:
// http://localhost:8080/experiences/search
// GET 
// body:
// {
//     "idCity": 9,

//     "startDay": "28/06/2023",

//     "endDay": "30/07/2023"
// }



  useEffect(() => {
    fetch(urlCity)
      .then((res) => res.json())
      .then((data) => {
        setIdCity(data);
      })
      .catch((error) => {
        console.log("Error al obtener datos del backend", error);
      });
  }, []);

const searchHeader = ()=>{ 
  const formattedStartDate = format(startDate, 'dd/MM/yyyy');
  const formattedEndDate = format(endDate, 'dd/MM/yyyy');
const requestData = {
  idCity: selectedCity.value ,
  startDay: formattedStartDate,
  endDay: formattedEndDate
};

fetch(urlSearch, {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify(requestData)
})
  .then(response => response.json())
  .then(responseData => {
    // Aquí puedes trabajar con los datos de respuesta
    console.log(responseData);
  })
  .catch(error => {
    console.error('Error en la solicitud:', error);
  });

 

 
 console.log(formattedEndDate, formattedStartDate ,selectedCity.value)
 
}


  const handleCityChange = (selectedOption) => {
    setSelectedCity(selectedOption)
  }

  const clearSelection = () => {
    setSelectedCity(null);
  }

  const handleStartDateChange = (date) => {
    setStartDate(date);
    console.log(date);
  };



  const handleEndDateChange = (date) => {
    setEndDate(date);
    console.log(date);
  };

  const handleSubmit = (e) => {
    const search = {
      idCity: selectedCity,
    };
  };

 

  return (
    <section className={styles.bar}>
      <h1 className={styles.h1}>Encuentra tu próxima aventura</h1>
      <p className={styles.p}>
        Explora destinos emocionantes y encuentra el tour perfecto para ti
      </p>
      <div className={styles.form}>
        <div className={styles.contSelect}>
        <Select
          className={styles.select}
          value={selectedCity}
          options={idCity?.data?.map((city) => ({ value: city.id, label: city.name }))}
          placeholder="En qué lugar quieres tu experiencia?"
          isSearchable={true}
          styles={{ container: (provided) => ({ ...provided, outline: 'none' }) }}
          onChange={handleCityChange}
        />
          {selectedCity && (
        <button className={styles.clearSearch} onClick={clearSelection}><FiX/></button>
      )}
        </div>
        
        
        <div className={styles.input}>
          <DatePicker
            dateFormat="dd/MM/yyyy"
            selected={startDate}
            onChange={handleStartDateChange}
            selectsStart
            startDate={startDate}
            endDate={endDate}
            placeholderText="Fecha de inicio  -  "
           
          />
          <DatePicker
            dateFormat="dd/MM/yyyy"
            selected={endDate}
            onChange={handleEndDateChange}
            selectsEnd
            startDate={startDate}
            endDate={endDate}
            placeholderText="    Fecha de fin"
            minDate={startDate} // Evita seleccionar fechas anteriores a la fecha de inicio
          />
        </div>
        {/* <Link to={"/products"}> */}
          <button className={styles.button} onClick={searchHeader}>Buscar</button>
        {/* </Link> */}
      </div>
    </section>
  );
};

export default Searchbar;
