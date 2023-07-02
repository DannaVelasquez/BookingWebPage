import { React, useState } from "react";
import styles from "./Recom.module.css";
import Card from "../CardRecom/Card";
import ReactPaginate from "react-paginate";
import { FiChevronsLeft, FiChevronsRight } from "react-icons/fi";
import { useContextGlobal } from "../GlobalContext/global.context";
import "../../Pagination.css";

const Recom = () => {
  const { tourState } = useContextGlobal();
  const [currentPage, setCurrentPage] = useState(0);
  const itemPerPage = 10;

  const offset = currentPage * itemPerPage;
  const filtro = tourState?.data?.filter((tour) => !tour.deleted);
  const random = filtro?.sort(() => 0.5 - Math.random()); //configura que las imagenes sean aleatorias validando que el objeto de respuesta si exista
  const currentPageData = random?.slice(offset, offset + itemPerPage);

  const handlePageChange = ({ selected }) => {
    setCurrentPage(selected);
  };

  return (
    <section className={styles.recom}>
      <div className={styles.h3Div}>
        <h3 className={styles.h3}>Recomendaciones</h3>
      </div>
      <hr />
      <br />
      <div className={styles.grid}>
        {currentPageData?.map((tour) => (
          <Card key={tour.id} tour={tour} />
        ))}
      </div>
      <div className={styles.pag}>
        <ReactPaginate
          pageCount={Math.ceil(random?.length / itemPerPage)} // Total de páginas
          pageRangeDisplayed={3} // Número de páginas mostradas en el componente
          marginPagesDisplayed={1} // Márgenes de páginas mostrados en el componente
          onPageChange={handlePageChange} // Función de cambio de página
          containerClassName="pagination"
          activeClassName="active"
          previousLabel={<FiChevronsLeft />}
          nextLabel={<FiChevronsRight />}
        />
      </div>
    </section>
  );
};

export default Recom;
