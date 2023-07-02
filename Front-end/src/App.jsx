import Header from "./Components/Header/Header";
import { Route, Routes } from "react-router-dom";
import { routes } from "./routes";
import Home from "./Routes/Home";
import Footer from "./Components/Footer/Footer";
import Login from "./Routes/Login";
import Register from "./Routes/Register";
import Detail from "./Routes/Detail";
import List from "./Routes/List";
import ProductForm from "./Components/ProductForm/ProductForm";
import AdminExp from "./Components/Admin/AdminExp";
import AdminCat from "./Components/Admin/AdminCat";
import CategoryForm from "./Components/Category/CatForm";
import Categories from "./Routes/Categories";
import Profile from "./Components/Profile/Profile";
import Attribute from "./Components/Attribute/Attribute";
import AdminAtt from "./Components/Admin/AdminAtt";
import AttributeForm from "./Components/Attribute/AttributeForm";
import EmailConfirmation from "./Routes/EmailConfirmation";
import { useLocation } from "react-router-dom";
import CityForm from "./Components/Cities/CityForm";
import AdminCity from "./Components/Admin/AdminCity";
import Dashboard from "./Components/Admin/Dashboard";
import Reservation from "./Components/Reservation/Reservation";
import ConfirmReserv from "./Components/Reservation/ConfirmReserv";
import AdminPlace from "./Components/Admin/AdminPlaces";
import PlaceForm from "./Components/Places/PlaceForm";
import BookingProfile from "./Components/Reservation/BookingProfile";
import ResultSearch from "./Routes/ResultSearch";


function App() {

  const location = useLocation();
  const hideHeaderRoutes = [routes.emailConfirmation, 
    routes.login, 
    routes.register , 

  ]

  const shouldShowHeader = !(
    hideHeaderRoutes.includes(location.pathname) ||
    location.search.includes("token")
  );
  return (
    <div className="App">
      {shouldShowHeader && <Header />}
      <Routes>
        <Route path={routes.home} element={<Home />} />
        <Route path={routes.login} element={<Login />} />
        <Route path={routes.register} element={<Register />} />
        <Route path={routes.tour} element={<Detail />} />
        <Route path={routes.products} element={<List />} />
        <Route path={routes.adminExp} element={<AdminExp />} />
        <Route path={routes.adminCat} element={<AdminCat />} />
        <Route path={routes.adminAtt} element={<AdminAtt />} />
        <Route path={routes.adminCity} element={<AdminCity/>} />
        <Route path={routes.dashboard} element={<Dashboard/>} />
        <Route path={routes.agregar} element={<ProductForm />} />
        <Route path={routes.agregarC} element={<CategoryForm />} />
        <Route path={routes.agregarA} element={<AttributeForm/>} />
        <Route path={routes.agregarCity} element={<CityForm/>} />
        <Route path={routes.category} element={<Categories />} />
        <Route path={routes.attribute} element={<Attribute/>} />
        <Route path={routes.profile} element={<Profile/>} />
        <Route path={routes.reservation} element={<Reservation/>} />
        <Route path={routes.reservationConfirm} element={<ConfirmReserv/>} />
        <Route path={routes.misReservas} element={<BookingProfile/>} />
        <Route path={routes.adminPlace} element={<AdminPlace/>} />
        <Route path={routes.agregarP} element={<PlaceForm/>} />
        <Route
          path={`${routes.emailConfirmation}/:token`}
          element={<EmailConfirmation />}
        />
        <Route path={routes.emailConfirmation} element={<EmailConfirmation/>} />
        <Route path={routes.resultSearch} element={<ResultSearch/>} />
      </Routes>

      <Footer />
    </div>
  );
}

export default App;
