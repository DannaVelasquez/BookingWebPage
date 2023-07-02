import React from "react";
import { routes } from "../../routes";
import { Link, useParams } from "react-router-dom";
import styles from "./AdminCity.module.css";
import { BsTrash } from "react-icons/bs";
import { VscAdd } from "react-icons/vsc";
import { FiRotateCw } from "react-icons/fi";
import Swal from "sweetalert2";
import { useContextGlobal } from "../GlobalContext/city.context";

const AdminCity = () => {
  const params = useParams();
  const { cityState, cityDetDispatch } = useContextGlobal();
  const url = `${import.meta.env.VITE_BACKEND_URL}/cities/get-all`;
  const token = localStorage.getItem('token');
  const filtro = cityState?.data?.filter((city) => !city.deleted);
  const urlUpdateCity = `${import.meta.env.VITE_BACKEND_URL}/cities/update/`
  const cityId = localStorage.getItem('cityId');

  console.log(cityState?.data?.data?.id)
  console.log(cityId)
  
  const handleUpdate = (cityId) => {
  Swal.fire({
    title: 'Actualizar Ciudad',
    input: 'text',
    inputLabel: 'Nuevo nombre',
    inputPlaceholder: 'Ingrese el nuevo nombre de la ciudad',
    showCancelButton: true,
    confirmButtonText: 'Actualizar',
    cancelButtonText: 'Cancelar',
    customClass: {
      popup: styles.swalPopup,
      title: styles.swalTitle,
      input: styles.swalInput,
      confirmButton: styles.swalConfirmButton,
      cancelButton: styles.swalCancelButton,
    },
    preConfirm: (name) => {
      if (!name) {
        Swal.showValidationMessage('El nombre de la ciudad es requerido');
      }
      return name;
    },
  }).then((result) => {
    if (result.isConfirmed) {
      const newName = result.value;
      const urlD = `${import.meta.env.VITE_BACKEND_URL}/cities/update/${cityId}`;
      fetch(urlD, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ name: newName }),
      })
        .then((response) => {
          if (response.ok) {
            cityDetDispatch({
              type: 'UPDATE_CITY',
              payload: { cityId, updatedCity: { id: cityId, name: newName } },
            });
            console.log('Ciudad actualizada exitosamente');
            Swal.fire({
              position: 'center',
              icon: 'success',
              text: 'La ciudad fue actualizada con éxito',
              showConfirmButton: true,
              confirmButtonColor: '#1dbeb4',
              customClass: {
                confirmButton: styles.confirmButton,
              },
            }).then((result) => {
              if (result.isConfirmed) {
                window.location.reload();
              }
            });
          } else {
            console.error('Error al actualizar la ciudad');
          }
        })
        .catch((error) => {
          console.error('Error en la solicitud:', error);
        });
    }
  });
};



  const handleDelete = (cityId) => {
    const urlD = `${import.meta.env.VITE_BACKEND_URL}/cities/delete/${cityId}`;
    fetch(urlD, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
      .then((response) => {
        if (response.ok) {
          cityDetDispatch({ type: "DELETE_CITY", payload: cityId });
          console.log("Producto eliminado exitosamente");
        Swal.fire({
            position: "center",
            icon: "success",
            text: "La ciudad fue eliminada con éxito",
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
        <h3 className={styles.h3}>Administrar Ciudades</h3>
      </div>
      
      <div className={styles.buttonExp} >
        <button className={styles.button}>
        <Link to={routes.agregarCity}>
        <VscAdd/> Añadir Nueva Ciudad
          </Link>
          </button>
        </div>
      <div>
        <table className={styles.table}>
          <thead>
            <tr className={styles.th}>
              <th>Ciudades Existentes</th>
            </tr>
          </thead>
          <tbody>
            <tr className={styles.tr}>
              <td>
                {filtro?.map((city) => (
                  <li className={styles.li} key={city.id}>
                    {city.name}
                    <div className={styles.buttonContainer}>
                <button
                  className={styles.delete}
                  onClick={() => handleDelete(city.id)}
                >
                  <BsTrash />
                </button>
                <button
                  className={styles.update}
                  onClick={() => handleUpdate(city.id)}
                >
                  <FiRotateCw />
                </button>
              </div>
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

export default AdminCity;
