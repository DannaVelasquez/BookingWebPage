import React from "react";
import { routes } from "../../routes";
import { Link, useParams } from "react-router-dom";
import styles from "./AdminPlaces.module.css";
import { BsTrash } from "react-icons/bs";
import { VscAdd } from "react-icons/vsc";
import { useContextGlobal } from "../GlobalContext/places.context";

const AdminPlace = () => {
  const params = useParams();
  const { placeState, placeDetDispatch } = useContextGlobal();
  const url = `${import.meta.env.VITE_BACKEND_URL}/places/get-all`;
  const token = localStorage.getItem("token");

  console.log(placeState)
  
  const handleDelete = (placeId) => {
    const urlD = `${
      import.meta.env.VITE_BACKEND_URL}/places/delete/${placeId}`;
      fetch(urlD, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        if (response.ok) {
          placeDetDispatch({ type: "DELETE_PLACE", payload: placeId });
          console.log("Producto eliminado exitosamente");
          Swal.fire({
            position: "center",
            icon: "success",
            text: "El lugar fue eliminado con éxito",
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
        <h3 className={styles.h3}>Administrar Lugares</h3>
      </div>
      <div className={styles.buttonExp}>
        <button className={styles.button}>
          <Link to={routes.agregarP}>
            <VscAdd /> Añadir Nuevo Lugar
          </Link>
        </button>
      </div>
      <div>
        <table className={styles.table}>
          <thead>
            <tr className={styles.th}>
              <th>Lugares Existentes</th>
              <hr/>
            </tr>
          </thead>
          <tbody>
            <tr className={styles.tr}>
              <td>
                {placeState?.data?.map((place) => (
                  <li className={styles.li} key={place.id}>
                    {place.name}
                    <button
                      className={styles.delete}
                      onClick={() => handleDelete(place.id)}
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

export default AdminPlace;
