import { React, useState } from "react";
import styles from "./AttributeForm.module.css";
import { Link } from "react-router-dom";
import Swal from "sweetalert2";
import { SlArrowLeft } from "react-icons/sl";

const AttributeForm = () => {
  const [name, setName] = useState("");
  const url = `${import.meta.env.VITE_BACKEND_URL}/attributes/create`;
  const token = localStorage.getItem("token");

  const handleSubmit = (e) => {
    e.preventDefault();

    const attribute = {
      name: name,
    };

    createAttribute(attribute);
  };

  const createAttribute = (attribute) => {
    fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(attribute),
    })
      .then((response) => response.json())
      .then((data) => {
        const message = data.message;
        if (message === "Success") {
          Swal.fire({
            position: "center",
            icon: "success",
            text: "El atributo " + attribute.name + " fue creada con éxito",
            showConfirmButton: true,
            confirmButtonColor: "#1dbeb4",
            customClass: {
              confirmButton: styles.confirmButton,
            },
          }).then(() => {
            setName("");
          });
        }
        if (message === "El nombre del atributo ya esta en uso") {
          Swal.fire({
            position: "center",
            icon: "info",
            title: "Lo sentimos",
            text:
              "El atributo " +
              attribute.name +
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

  return (
    <div className={styles.productPag}>
      <div className={styles.header}>
        <h3 className={styles.h3}>Administración de Servicios</h3>
        <Link to={"/dashboard"}>
          <span className={styles.flecha}>
            <SlArrowLeft />
          </span>
        </Link>
      </div>
      <form onSubmit={handleSubmit}>
        <section>
          <div>
            <h1 className={styles.h1}>Crear Nuevo Servicio</h1>
          </div>
        </section>
        <div className={styles.productPagForm}>
          <fieldset className={styles.fieldset}>
            <p className={styles.p}>Servicio</p>
            <label className={styles.label} htmlFor="name"></label>
            <input
              className={styles.input}
              type="text"
              name="name"
              placeholder="Ingrese servicio"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </fieldset>
          <br />
          <button className={styles.button} type="submit">
            Crear servicio
          </button>
        </div>
      </form>
    </div>
  );
};

export default AttributeForm;
