import { React, useState } from "react";
import Searchbar from "../Components/Search-Bar/Searchbar";
import Card from "../Components/CardRecom/Card";
import { useContextGlobal } from "../Components/GlobalContext/global.context";
import ReactPaginate from "react-paginate";
import styles from "./Categories.module.css";
import { useParams } from "react-router-dom";
import { FiChevronsLeft, FiChevronsRight } from "react-icons/fi";
import "../Pagination.css";

const Categories = () => {
  const { tourState } = useContextGlobal();
  const [currentPage, setCurrentPage] = useState(0);
  const itemPerPage = 10;
  const params = useParams();
  const cate = params.category;
  const product = [];

  console.log(tourState);
  const filtradoDeCategoria = () => {
    for (let i = 0; i < tourState?.data?.length; i++) {
      const element = tourState?.data?.[i];
      console.log(element)
      if (element.category.name.toLowerCase() == cate.toLowerCase()) {
        product.push(element);
      }
    }
    
    return product;
    
  };

  
  filtradoDeCategoria();

  const offset = currentPage * itemPerPage;
  const currentPageData = product?.slice(offset, offset + itemPerPage);

  const handlePageChange = ({ selected }) => {
    setCurrentPage(selected);
    console.log(currentPage)
    console.log(currentPageData)
  };

  console.log(currentPageData)

  return (
    <div className={styles.detail}>
      <Searchbar />
      <section className={styles.list}>
        <h3 className={styles.h3}>Tours  {cate}</h3>
        {currentPageData.length>0 ? (
          <>
          <div className={styles.grid}>
          {currentPageData?.map((tour) => (
            <Card key={tour.id} tour={tour} />
          ))}
          </div>
          <div className={styles.pag}>
            <ReactPaginate
              pageCount={Math.ceil(tourState?.data?.length / itemPerPage)}
              pageRangeDisplayed={3}
              marginPagesDisplayed={1}
              onPageChange={handlePageChange}
              containerClassName="pagination"
              activeClassName="active"
              previousLabel={<FiChevronsLeft />}
              nextLabel={<FiChevronsRight />}
            />
          </div>
          </>
        ) :  (<h4 className={styles.h4}>No se tienen tours en esta categoria</h4> )}
        
        
      </section>
    </div>
  );
};

export default Categories;
