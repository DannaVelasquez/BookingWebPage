import { React, useState } from "react";
import styles from "./CatForm.module.css";
import { Link } from "react-router-dom";
import Swal from "sweetalert2";
import { SlArrowLeft } from "react-icons/sl";

const CategoryForm = () => {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [image, setImage] = useState(null);
  const [uploadSuccess, setUploadSuccess] = useState(false);
  const url = `${import.meta.env.VITE_BACKEND_URL}/categories/create`;
  const urlFiles = `${import.meta.env.VITE_BACKEND_URL}/files/upload-base64`;
  const token = localStorage.getItem("token");
  const fileType = "UPLOAD_FILE";

  const category = {
    name: name,
    description: description,
    image: image,
  };

  const handleImageChange = (event) => {
    const fileList = event.target.files;
    const file = fileList[0];
    setImage(file);
  };

  //Convierte la imagen de file a base64
  const convertFileToBase64 = (file) => {
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
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (image) {
        const base64File = await convertFileToBase64(image);

        const payload = {
          fileName: image.name,
          fileType,
          encodeBase64: base64File.split(",")[1],
        };

        const response = await fetch(urlFiles, {
          method: "POST",
          body: JSON.stringify(payload),
          headers: {
            "Content-Type": "application/json",
          },
        });

        if (response.ok) {
          const data = await response.json();
          setUploadSuccess(true);
          const imageUrl = data.data;
          category.image = imageUrl; //setea la imagen de file a url dada por S3
          createCategory(category);
        } else {
          throw new Error("Upload failed. Please try again.");
        }
      } else {
        createCategory(category);
      }
    } catch (error) {
      console.error(error);
    }
  };

  const uploadImage = async (category) =>{
   

    console.log(image)
    
        if (!image) {
          alert('Please select a file.');
          return;
        }
    
        try {
    
          const base64File = await convertFileToBase64(image);
    
          console.log(base64File);
    
          const payload = {
            fileName: image.name,
            fileType,
            encodeBase64: base64File.split(',')[1]
          };
    
          const response = await fetch(urlFiles, {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: {
              'Content-Type': 'application/json'
            }
          });
    
          if (response.ok) {
            const data = await response.json();
            setUploadSuccess(true);
            console.log(data)
            const imageUrl = data.data; // Obtener la URL de la imagen del objeto de respuesta
           // category.image = imageUrl;
            category.image = setImage(imageUrl) // Agregar la URL al objeto experience
            console.log(imageUrl);
          } else {
            throw new Error('Upload failed. Please try again.');
          }
        } catch (error) {
          console.error(error);
          alert('An error occurred. Please try again later.');
        }
    
      }

  const createCategory = (category) => {
    fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(category),
    })
      .then((response) => response.json())
      .then((data) => {
        const message = data.message;
        if (message === "Success") {
          Swal.fire({
            position: "center",
            icon: "success",
            text: "La categoría " + category.name + " fue creada con éxito",
            showConfirmButton: true,
            confirmButtonColor: "#1dbeb4",
            customClass: {
              confirmButton: styles.confirmButton,
            },
          }).then(() => {
            setName("");
            setDescription("");
            setImage(null);
          });
        }
        if (message === "El nombre de la categoria ya esta en uso") {
          Swal.fire({
            position: "center",
            icon: "info",
            title: "Lo sentimos",
            text:
              "La categoria " +
              category.name +
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
        <h3 className={styles.h3}>Administración de Categorías</h3>
        <Link to={"/dashboard"}>
          <span className={styles.flecha}>
            <SlArrowLeft />
          </span>
        </Link>
      </div>
      <form onSubmit={handleSubmit}>
        <section>
          <div>
            <h1 className={styles.h1}>Crear Nueva Categoría</h1>
          </div>
        </section>
        <div className={styles.productPagForm}>
          <fieldset className={styles.fieldset}>
            <p className={styles.p}>Nombre de la categoría</p>
            <label className={styles.label} htmlFor="name"></label>
            <input
              className={styles.input}
              type="text"
              name="name"
              placeholder="Ingrese el nombre de la categoría"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </fieldset>
          <fieldset className={styles.fieldset}>
            <p className={styles.p}>Descripción</p>
            <label className={styles.label} htmlFor="description"></label>
            <textarea
              className={styles.textarea}
              type="text"
              placeholder="Agrega una descripción"
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
              type="file"
              placeholder="Insertar https://"
              onChange={handleImageChange}
            />
          </fieldset>
          <br />
          <button className={styles.button} type="submit">
            Crear Categoría
          </button>
        </div>
      </form>
    </div>
  );
};

export default CategoryForm;
