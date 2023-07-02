import React from "react";
import { routes } from "../../routes";
import { Link, useParams } from "react-router-dom";
import styles from "./AdminAtt.module.css";
import { BsTrash } from "react-icons/bs";
import { VscAdd } from "react-icons/vsc";
import { useContextGlobal } from "../GlobalContext/attributes.context";

const AdminAtt = () => {
  const params = useParams();
  const { attributeState, attributeDetDispatch } = useContextGlobal();
  const url = `${import.meta.env.VITE_BACKEND_URL}/attributes/get-all`;
  const token = localStorage.getItem("token");

  const handleDelete = (attributeId) => {
    const urlD = `${
      import.meta.env.VITE_BACKEND_URL}/attributes/delete/${attributeId}`;
    fetch(urlD, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        if (response.ok) {
          attributeDetDispatch({
            type: "DELETE_ATTRIBUTE",
            payload: attributeId,
          });
          console.log("Producto eliminado exitosamente");
          Swal.fire({
            position: "center",
            icon: "success",
            text: "El servicio fue eliminado con éxito",
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
          console.error("Error al eliminar atributo");
        }
      })
      .catch((error) => {
        console.error("Error en la solicitud:", error);
      });
  };

  return (
    <div className={styles.admin}>
      <div className={styles.header}>
        <h3 className={styles.h3}>Administrar Servicios</h3>
      </div>

      <div className={styles.buttonExp}>
        <button className={styles.button}>
          <Link to={routes.agregarA}>
            <VscAdd /> Añadir Nuevo Servicio
          </Link>
        </button>
      </div>

      <div>
        <table className={styles.table}>
          <thead>
            <tr className={styles.th}>
              <th>Servicios Existentes</th>
            </tr>
          </thead>
          <tbody>
            <tr className={styles.tr}>
              <td>
                {attributeState?.data?.map((attribute) => (
                  <li className={styles.li} key={attribute.id}>
                    {attribute.name}
                    <button
                      className={styles.delete}
                      onClick={() => handleDelete(attribute.id)}
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

export default AdminAtt;
