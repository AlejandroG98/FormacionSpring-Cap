import React, { useState } from 'react';

const ActorSearch = () => {
  const [actorId, setActorId] = useState('');
  const [actorData, setActorData] = useState(null);
  const [error, setError] = useState(null);

  const handleActorIdChange = (event) => {
    setActorId(event.target.value);
  };

  const handleSearch = () => {
    fetch(`http://localhost:8001/actores/${actorId}`)
      .then((response) => {
        if (response.ok) {
          return response.json();
        } else {
          throw new Error('ERROR: No existe ningún actor con esa ID ...');
        }
      })
      .then((data) => {
        // Convertir el primer carácter del nombre y apellidos a mayúsculas y el resto a minúsculas
        data.nombre = data.nombre.charAt(0).toUpperCase() + data.nombre.slice(1).toLowerCase();
        data.apellidos = data.apellidos.charAt(0).toUpperCase() + data.apellidos.slice(1).toLowerCase();
        setActorData(data);
        setError(null);
      })
      .catch((error) => {
        setError(error.message);
        setActorData(null);
      });
  };

  return (
    <div className='getOne'>
      <label>
        <a>Ingrese el ID del actor:</a>
        <input type="number" value={actorId} onChange={handleActorIdChange} min={0} />
      </label><br />
      <button className="btn btn-info" onClick={handleSearch}>Buscar</button>
      {error && <p>{error}</p>}
      {actorData && (
        <div>
          <p>{actorData.nombre} {actorData.apellidos}</p>
        </div>
      )}
    </div>
  );
};

export default ActorSearch;
