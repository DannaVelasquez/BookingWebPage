import { React, useState } from "react";
import Searchbar from "../Components/Search-Bar/Searchbar";
import Card from "../Components/CardRecom/Card";
import { useContextGlobal } from "../Components/GlobalContext/global.context";
import ReactPaginate from "react-paginate";
import { FiChevronsLeft, FiChevronsRight } from "react-icons/fi";
import styles from "./List.module.css";
import "../Pagination.css";

const List = () => {
  const { tourState } = useContextGlobal();
  const [currentPage, setCurrentPage] = useState(0);
  const itemPerPage = 10;
  const offset = currentPage * itemPerPage;
  const filtro = tourState?.data?.filter((tour) => !tour.deleted);
  const random = filtro?.sort(() => 0.5 - Math.random());
  const currentPageData = random?.slice(offset, offset + itemPerPage);

  const handlePageChange = ({ selected }) => {
    setCurrentPage(selected);
  };

  return (
    <div className={styles.divList}>
      <Searchbar />
      <div className={styles.recom}>
        <h3 className={styles.h3}>Tours Disponibles</h3>
        <div className={styles.grid}>
          {currentPageData?.map((tour) => (
            <Card tour={tour} />
          ))}
        </div>
        <br />
        <div className={styles.pag}>
          <ReactPaginate
            pageCount={Math.ceil(random?.length / itemPerPage)}
            pageRangeDisplayed={3}
            marginPagesDisplayed={1}
            onPageChange={handlePageChange}
            containerClassName="pagination"
            activeClassName="active"
            previousLabel={<FiChevronsLeft />}
            nextLabel={<FiChevronsRight />}
          />
        </div>
      </div>
    </div>
  );
};

export default List;
