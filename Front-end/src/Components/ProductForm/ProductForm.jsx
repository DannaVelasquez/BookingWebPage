import { React, useEffect, useState } from "react";
import styles from "./ProductForm.module.css";
import { Link } from "react-router-dom";
import Swal from "sweetalert2";
import axios from "axios";
import { SlArrowLeft } from "react-icons/sl";
import { useContextGlobal } from "../GlobalContext/category.context";

const ProductForm = () => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [images, setImages] = useState([]);
  const [durationInDays, setDurationInDays] = useState("");
  const [selectedValue, setSelectedValue] = useState("");
  const [selectedPlace, setSelectedPlace] = useState("");
  const [idCategory, setIdCategory] = useState([]);
  const [idPlace, setIdPlace] = useState([]);
  const [attributes, setAttributes] = useState([]);
  const [amountForPerson, setAmountForPerson] = useState("");
  const [selectedAttributes, setSelectedAttributes] = useState([])
  const [uploadSuccess, setUploadSuccess] = useState(false);
  const url = `${import.meta.env.VITE_BACKEND_URL}/experiences/create`;
  const urlC = `${import.meta.env.VITE_BACKEND_URL}/categories/get-all`;
  const urlFiles = `${import.meta.env.VITE_BACKEND_URL}/files/upload-base64`;
  const urlAtt = `${import.meta.env.VITE_BACKEND_URL}/attributes/get-all`;
  const urlPlaces = `${import.meta.env.VITE_BACKEND_URL}/places/get-all`;
  const token = localStorage.getItem('token');
  const fileType = 'UPLOAD_FILE';

  const experience = {
    title: title,
    description: description,
    images: images,
    durationInDays: durationInDays,
    idCategory: selectedValue,
    attributes: selectedAttributes,
    idPlace: selectedPlace,
    amountForPerson: amountForPerson,
  };

  useEffect(() => {
    fetch(urlC)
    .then(res => res.json())
    .then(data => {
      setIdCategory(data)
    })
    .catch( error => {
      console.log("Error al obtener datos del backend", error)
    });
  }, []);

  useEffect(() => {
    fetch(urlAtt)
    .then(res => res.json())
    .then(data => {
      const attributesData = data.data;
      setAttributes(attributesData)
    })
    .catch( error => {
      console.log("Error al obtener datos del backend", error)
    });
  }, []);

  useEffect(() => {
    fetch(urlPlaces)
    .then(res => res.json())
    .then(data => {
      setIdPlace(data)
    })
    .catch( error => {
      console.log("Error al obtener datos del backend", error)
    });
  }, []);

  //Config File Images
// const uploadFiles = (e) => {
//   const fileList = e.target.files;
//   console.log(fileList)
//   const fileArray = Array.from(fileList);
//   setImages(fileArray);
// };

  const handleImageChange = (event) => {
    const fileList = event.target.files;
  const filesArray = Array.from(fileList);
  setImages(filesArray);
  };

  console.log(images)

  const convertFileToBase64 = (files) => {
    const promises = files.map((file) => {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();

      reader.onloadend = () => {
        resolve(reader.result);
      };

      reader.onerror = (error) => {
        reject(error);
      };

      reader.readAsDataURL(file);
    });
  });
  return Promise.all(promises);
}


  const handleCheckBoxChange = (e) => {
    const attributeId = parseInt(event.target.value);
    if(event.target.checked) {
      setSelectedAttributes([...selectedAttributes, attributeId])
    } else{
      setSelectedAttributes(selectedAttributes.filter((id) =>
      id !== attributeId))
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
  
    try {
      if (images.length > 0) {
        const base64Files = await convertFileToBase64(images);
  
        const uploadPromises = base64Files.map((base64File, index) => {
          const payload = {
            fileName: images[index].name,
            fileType,
            encodeBase64: base64File.split(",")[1],
          };
  
          return fetch(urlFiles, {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {
              "Content-Type": "application/json",
            },
          });
        });
  
        const uploadResponses = await Promise.all(uploadPromises);
        const imageUrls = [];
  
        for (const response of uploadResponses) {
          if (response.ok) {
            console.log(response)
            const data = await response.json();
            const imageUrl = data.data;
            console.log(imageUrl)
            imageUrls.push(imageUrl);
          } else {
            throw new Error("Upload failed. Please try again.");
          }
        }
  
        experience.images = imageUrls;
        createProduct(experience);
        console.log(experience)
      } else {
        createProduct(experience);
      }
    } catch (error) {
      console.error(error);
    }
  };
  


//Creacion de producto
  const createProduct = (experience) => {
    fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify(experience),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data)
        const message = data.message;
        if (message === "Success") {
          Swal.fire({
            position: "center",
            icon: "success",
            text: "El tour " + experience.title + " fue creado con éxito",
            showConfirmButton: true,
            confirmButtonColor: "#1dbeb4",
            customClass: {
              confirmButton: styles.confirmButton,
            },
          });
        }
        if (message === "El Titulo de la experiencia ya esta en uso") {
          Swal.fire({
            position: "center",
            icon: "info",
            title: "Lo sentimos",
            text:
              "El tour " + experience.title + " ya se encuentra en el sistema.",
            showConfirmButton: true,
            confirmButtonColor: "#1dbeb4",
            customClass: {
              confirmButton: styles.confirmButton,
            },
          });
        }
        if (data.errors == "durationInDays: La duración debe ser mayor a 1 día"){
          Swal.fire({
            position: "center",
            icon: "warning",
            title: "Lo sentimos",
            text:
              "La duración del tour deberá ser superior a 1 día",
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
        <h3 className={styles.h3}>Administración de Tours</h3>
        <Link to={"/dashboard"}>
          <span className={styles.flecha}>
            <SlArrowLeft />
          </span>
        </Link>
      </div>
      <form onSubmit={handleSubmit}>
        <div>
          <h1 className={styles.h1}>Crear Nuevo Tour</h1>
        </div>
        <div className={styles.productPagForm}>
          <table className={styles.table}>
              <td className={styles.col}>
                <fieldset className={styles.fieldset}>
                  <p className={styles.p}>Nombre del tour</p>
                  <label className={styles.label} htmlFor="name"></label>
                  <input
                    className={styles.input}
                    type="text"
                    name="name"
                    placeholder="Ingrese el nombre del tour"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    required
                  />
                </fieldset>
              </td>
              <td className={styles.col}>
                <fieldset className={styles.fieldset}>
                  <p className={styles.p}>Duracion del tour</p>
                  <label className={styles.label} htmlFor="name"></label>
                  <input
                    className={styles.input}
                    type="text"
                    name="name"
                    placeholder="Ingrese la duracion del tour"
                    value={durationInDays}
                    onChange={(e) => setDurationInDays(e.target.value)}
                    required
                  />
                </fieldset>
              </td>
              <td className={styles.col}>
                <fieldset className={styles.fieldset}>
                  <p className={styles.p}>Monto por persona</p>
                  <label className={styles.label} htmlFor="name"></label>
                  <input
                    className={styles.input}
                    type="text"
                    name="name"
                    placeholder="Ingrese el monto por persona"
                    value={amountForPerson}
                    onChange={(e) => setAmountForPerson(e.target.value)}
                    required
                  />
                </fieldset>
              </td>
              
          </table>
          <table className={styles.table}>
          <td className={styles.col6}>
                <fieldset className={styles.fieldset}>
                  <p className={styles.p}>Lugar</p>
                  <label className={styles.label} htmlFor="placeId"></label>
                  <select
                    className={styles.select}
                    name="placeId"
                    placeholder="Seleccione el lugar"
                    id="selectedPlace"
                    value={selectedPlace}
                    onChange={(e) => setSelectedPlace(e.target.value)}
                    required
                  >
                    <option>Seleccionar</option>
                    {idPlace?.data?.map(place => (
                      <option key={place.id} value={place.id}>{place.name}</option>
                    ))}
                  </select>
                </fieldset>
              </td>
              <td className={styles.col6}>
                <fieldset className={styles.fieldset}>
                  <p className={styles.p}>Categoría</p>
                  <label className={styles.label} htmlFor="categoryId"></label>
                  <select
                    className={styles.select}
                    name="categoryId"
                    placeholder="Seleccione la categoria"
                    id="selectedValue"
                    value={selectedValue}
                    onChange={(e) => setSelectedValue(e.target.value)}
                    required
                  >
                    <option>Seleccionar</option>
                    {idCategory?.data?.map(category => (
                      <option key={category.id} value={category.id}>{category.name}</option>
                    ))}
                  </select>
                </fieldset>
              </td>
          </table>
          <fieldset className={styles.fieldset}>
            <p className={styles.p1}>Descripción</p>
            <label className={styles.label} htmlFor="description"></label>
            <textarea
              className={styles.textarea}
              type="text"
              placeholder="Agrega una descripcion"
              name="description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              required
              maxLength={255}
            />
          </fieldset>
          
          
          <fieldset className={styles.fieldset}>
            <label className="image"></label>
            <input
              className={styles.file}
              type="file" multiple
              placeholder="Insertar https://"
              onChange={handleImageChange}
            />
          </fieldset>
          <fieldset className={styles.fieldset}>
            <p className={styles.p1}>Caracteristicas</p>
          {Array.isArray(attributes) ? (
          attributes.map((attribute) => (
            <div className={styles.check} key={attribute.id}>
              <input 
              type="checkbox" 
              id={attribute.id} 
              value={attribute.id}
              onChange={handleCheckBoxChange} />
              <label  htmlFor={attribute.id}>{attribute.name}</label>
            </div>
          ))
        ) : (
          <h4 className={styles.h4}>No se pudo cargar la lista de atributos.</h4>
        )}
          </fieldset>
          <br />
          <button className={styles.button} type="submit">
            Crear Tour
          </button>
        </div>
      </form>
    </div>
  );
};


export default ProductForm;