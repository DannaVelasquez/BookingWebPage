import React from "react";
import { routes } from "../../routes";
import { Link, useParams } from "react-router-dom";
import styles from "./AdminCat.module.css";
import { BsTrash } from "react-icons/bs";
import { VscAdd } from "react-icons/vsc";
import Swal from "sweetalert2";
import { useContextGlobal } from "../GlobalContext/category.context";

const AdminCat = () => {
  const params = useParams();
  const { categoryState, categoryDetDispatch } = useContextGlobal();
  const url = `${import.meta.env.VITE_BACKEND_URL}/categories/get-all`;
  const token = localStorage.getItem("token");

  const handleDelete = (categoryId) => {
    const urlD = `${
      import.meta.env.VITE_BACKEND_URL}/categories/delete/${categoryId}`;
      fetch(urlD, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        if (response.ok) {
          categoryDetDispatch({ type: "DELETE_CATEGORY", payload: categoryId });
          console.log("Producto eliminado exitosamente");
          Swal.fire({
            position: "center",
            icon: "success",
            text: "La categoría fue eliminada con éxito",
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
        <h3 className={styles.h3}>Administrar Categorías</h3>
      </div>
      <div className={styles.buttonExp}>
        <button className={styles.button}>
          <Link to={routes.agregarC}>
            <VscAdd /> Añadir Nueva Categoría
          </Link>
        </button>
      </div>
      <div>
        <table className={styles.table}>
          <thead>
            <tr className={styles.th}>
              <th>Categorías Existentes</th>
              <hr/>
            </tr>
          </thead>
          <tbody>
            <tr className={styles.tr}>
              <td>
                {categoryState?.data?.map((category) => (
                  <li className={styles.li} key={category.id}>
                    {category.name}
                    <button
                      className={styles.delete}
                      onClick={() => handleDelete(category.id)}
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

export default AdminCat;
