import React from "react";
import "mapbox-gl/dist/mapbox-gl.css";
import Map, { Marker, NavigationControl } from "react-map-gl";

import { useState } from "react";

const Location = () => {
  const [lng, setLng] = useState(-100);
  const [lat, setLat] = useState(40);

  return (
    <div>
      <Map
        mapboxAccessToken="pk.eyJ1IjoiY2FsZGVyb25heWVsZW44OSIsImEiOiJjbGliMjVhYWwwODFuM2RwZm90ZTl0YjQ4In0.2ieq0swrJUPwGCY5ftFRnw"
        style={{
          width: "90%",
          height: "400px",
          borderRadius: "15px",
          border: "2px solid #1DBEB4",
          margin: "3%",
        }}
        initialViewState={{
          longitude: lng,
          latitude: lat,
        }}
        mapStyle="mapbox://styles/mapbox/streets-v9"
        zoom={6}
      >
        <Marker longitude={lng} latitude={lat} />
        <NavigationControl position="bottom-right" />
      </Map>
    </div>
  );
};

export default Location;
