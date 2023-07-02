import React from "react";
import { routes } from "../../routes";
import { Link, useParams } from "react-router-dom";
import styles from "./AdminExp.module.css";
import { BsTrash } from "react-icons/bs";
import { VscAdd } from "react-icons/vsc";
import { SlArrowLeft } from "react-icons/sl";
import { useContextGlobal } from "../GlobalContext/global.context";

const AdminExp = () => {
  const params = useParams();
  const { tourState, tourDetDispatch } = useContextGlobal();
  const url = `${import.meta.env.VITE_BACKEND_URL}/experiences/recommend`;
  const token = localStorage.getItem("token");

  const handleDelete = (tourId) => {
    const urlD = `${
      import.meta.env.VITE_BACKEND_URL}/experiences/delete/${tourId}`;
    console.log(tourId);
    fetch(urlD, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        if (response.ok) {
          tourDetDispatch({ type: "DELETE_TOUR", payload: tourId });
          console.log("Producto eliminado exitosamente");
          Swal.fire({
            position: "center",
            icon: "success",
            text: "El tour fue eliminado con éxito",
            showConfirmButton: true,
            confirmButtonColor: "#1dbeb4",
            customClass: {
              confirmButton: styles.confirmButton,
            }}).then((result) => {
              if (result.isConfirmed) {
                window.location.reload();
              }
            });
        } else {
          console.error("Error al eliminar el producto");
        }
      })
      .catch((error) => {
        console.error("Error en la solicitud:", error);
      });
  };

  return (
    <div className={styles.admin}>
      <div className={styles.header}>
        <h3 className={styles.h3}>Administrar Tours</h3>
      </div>
      <div className={styles.buttonExp}>
        <button className={styles.button}>
          <Link to={routes.agregar}>
            <VscAdd /> Añadir Nuevo Tour
          </Link>
        </button>
      </div>

      <div>
        <table className={styles.table}>
          <thead>
            <tr>
              <th className={styles.th}>Tours Existentes</th>
            </tr>
          </thead>
          <tbody>
            <tr className={styles.tr}>
              <td>
                {tourState?.data
                  ?.filter((tour) => !tour.deleted)
                  ?.map((tour) => (
                    <li className={styles.li} key={tour.id}>
                      {tour.title}
                      <button
                        className={styles.delete}
                        onClick={() => handleDelete(tour.id)}
                      >
                        <BsTrash />
                      </button>
                    </li>
                  ))}
              </td>
              <td></td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default AdminExp;
