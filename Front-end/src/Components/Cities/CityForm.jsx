import { React, useState } from "react";
import styles from "./cityForm.module.css";
import { Link } from "react-router-dom";
import Swal from "sweetalert2";
import { SlArrowLeft } from "react-icons/sl";

const CityForm = () => {
  const [name, setName] = useState("");
  const url = `${import.meta.env.VITE_BACKEND_URL}/cities/create`;
  const token = localStorage.getItem("token");

  const handleSubmit = (e) => {
    e.preventDefault();

    const city = {
      name: name,
    };

    createCity(city);
  };

  const createCity = (city) => {
    console.log(JSON.stringify(city))
    fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(city),
    })
      .then((response) => response.json())
      .then((data) => {
        const message = data.message;
        if (message === "Success") {
          Swal.fire({
            position: "center",
            icon: "success",
            text: "La ciudad " + city.name + " fue creada con éxito",
            showConfirmButton: true,
            confirmButtonColor: "#1dbeb4",
            customClass: {
              confirmButton: styles.confirmButton,
            },
          }).then(() => {
            setName("");
          });;
        }
        if (message === "El nombre del atributo ya esta en uso") {
          Swal.fire({
            position: "center",
            icon: "info",
            title: "Lo sentimos",
            text:
              "El atributo " + city.name + " ya se encuentra en el sistema.",
            showConfirmButton: true,
            confirmButtonColor: '#1dbeb4',
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

  return (
    <div  className={styles.productPag}>
      <div className={styles.header}>
        <h3 className={styles.h3}>Administración de Ciudades</h3>
        <Link to={"/dashboard"}>
          <span className={styles.flecha}>
            <SlArrowLeft />
          </span>
        </Link>
      </div>
      <form onSubmit={handleSubmit}>
        <section>
          <div>
            <h1 className={styles.h1}>Crear Nueva Ciudad</h1>
          </div>
        </section>
        <div className={styles.productPagForm}>
                <fieldset className={styles.fieldset}>
                  <p className={styles.p}>Nombre de la ciudad</p>
                  <label className={styles.label} htmlFor="name"></label>
                  <input
                    className={styles.input}
                    type="text"
                    name="name"
                    placeholder="Ingrese el nombre de la ciudad"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                  />
                </fieldset>
          <br />
          <button className={styles.button} type="submit">
            Crear Ciudad
          </button>
        </div>
      </form>
    </div>
  );
};

export default CityForm;
