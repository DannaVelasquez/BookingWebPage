import { React, useState, useEffect } from "react";
import styles from "./PlaceForm.module.css";
import { Link } from "react-router-dom";
import Swal from "sweetalert2";
import { SlArrowLeft } from "react-icons/sl";

const PlaceForm = () => {
  const [name, setName] = useState("");
  const [idCountry, setIdCountry] = useState([]);
  const [idCity, setIdCity] = useState([]);
  const [selectedValue, setSelectedValue] = useState("");
  const [selectedCountry, setSelectedCountry] = useState("");
  const url = `${import.meta.env.VITE_BACKEND_URL}/places/create`;
  const urlCity =`${import.meta.env.VITE_BACKEND_URL}/cities/get-all`;
  const urlCountry =`${import.meta.env.VITE_BACKEND_URL}/countries/get-all`;
  const token = localStorage.getItem("token");

  

  useEffect(() => {
    fetch(urlCity)
    .then(res => res.json())
    .then(data => {
      setIdCity(data)
    })
    .catch( error => {
      console.log("Error al obtener datos del backend", error)
    });
  }, []);


  useEffect(() => {
    fetch(urlCountry)
    .then(res => res.json())
    .then(data => {
      setIdCountry(data)
    })
    .catch( error => {
      console.log("Error al obtener datos del backend", error)
    });
  }, []);

  const handleSubmit = (e) =>{
    e.preventDefault();

    const place = {
      name: name,
      idCountry: selectedCountry,
      idCity: selectedValue,
      
    };

    createPlace(place);
  }

  console.log(idCity)
  console.log(idCountry)


  const createPlace = (place) => {
    fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(place),
    })
      .then((response) => response.json())
      .then((data) => {
        const message = data.message;
        if (message === "Success") {
          Swal.fire({
            position: "center",
            icon: "success",
            text: "El lugar " + place.name + " fue creado con éxito",
            showConfirmButton: true,
            confirmButtonColor: "#1dbeb4",
            customClass: {
              confirmButton: styles.confirmButton,
            },
          }).then(() => {
            setName("");
            setIdCity("");
            setIdCountry("");
          });
        }
        if (message === "El nombre del lugar ya esta en uso") {
          Swal.fire({
            position: "center",
            icon: "info",
            title: "Lo sentimos",
            text:
              "El lugar " +
              place.name +
              " ya se encuentra en el sistema.",
            showConfirmButton: true,
            confirmButtonColor: "#1dbeb4",
            customClass: {
              confirmButton: styles.confirmButton,
            },
          });
        }
      })
      .catch((error) => {
        console.error("Error en la solicitud", error);
      });
  };

  console.log(idCountry?.data)
  console.log(idCity?.data)

  return (
    <div className={styles.productPag}>
      <div className={styles.header}>
        <h3 className={styles.h3}>Administración de Lugares</h3>
        <Link to={"/dashboard"}>
          <span className={styles.flecha}>
            <SlArrowLeft />
          </span>
        </Link>
      </div>
      <form onSubmit={handleSubmit}>
        <section>
          <div>
            <h1 className={styles.h1}>Crear Nuevo Lugar</h1>
          </div>
        </section>
        <div className={styles.productPagForm}>
          <fieldset className={styles.fieldset}>
            <p className={styles.p}>Nombre del lugar</p>
            <label className={styles.label} htmlFor="name"></label>
            <input
              className={styles.input}
              type="text"
              name="name"
              placeholder="Ingrese el nombre del lugar"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </fieldset>
          <fieldset className={styles.fieldset}>
                  <p className={styles.p}>Pais</p>
                  <label className={styles.label} htmlFor="countryId"></label>
                  <select
                    className={styles.select}
                    name="countryId"
                    placeholder="Seleccione el pais"
                    id="selectedCountry"
                    value={selectedCountry}
                    onChange={(e) => setSelectedCountry(e.target.value)}
                    required
                  >
                    <option>Seleccionar</option>
                    {idCountry?.data?.map(country => (
                      <option key={country.id} value={country.id}>{country.name}</option>
                    ))}
                  </select>
                </fieldset>
          <fieldset className={styles.fieldset}>
                  <p className={styles.p}>Ciudad</p>
                  <label className={styles.label} htmlFor="cityId"></label>
                  <select
                    className={styles.select}
                    name="cityId"
                    placeholder="Seleccione la ciudad"
                    id="selectedValue"
                    value={selectedValue}
                    onChange={(e) => setSelectedValue(e.target.value)}
                    required
                  >
                    <option>Seleccionar</option>
                    {idCity?.data?.map(city => (
                      <option key={city.id} value={city.id}>{city.name}</option>
                    ))}
                  </select>
                </fieldset>
                
          
          <br />
          <button className={styles.button} type="submit">
            Crear Lugar
          </button>
        </div>
      </form>
    </div>
  );
};

export default PlaceForm;
