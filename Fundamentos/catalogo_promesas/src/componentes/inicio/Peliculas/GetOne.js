import React, { useState } from 'react';

const FilmSearch = () => {
  const [filmId, setFilmId] = useState('');
  const [filmData, setFilmData] = useState(null);
  const [error, setError] = useState(null);

  const handleActorIdChange = (event) => {
    setFilmId(event.target.value);
  };

  const handleSearch = () => {
    fetch(`http://localhost:8001/peliculas/${filmId}`)
      .then((response) => {
        if (response.ok) {
          return response.json();
        } else {
          throw new Error('ERROR: No existe ninguna Película con esa ID ...');
        }
      })
      .then((data) => {
        setFilmData(data);
        setError(null);
      })
      .catch((error) => {
        setError(error.message);
        setFilmData(null);
      });
  };

  return (
    <div className='getOne'>
      <label>
        <a>Ingrese el ID de la Película:</a>
        <input type="number" value={filmId} onChange={handleActorIdChange} min={0} />
      </label><br />
      <button className="btn btn-info" onClick={handleSearch}>Buscar</button>
      {error && <p>{error}</p>}
      {filmData && (
        <div>
          <p>{filmData.title}</p>
        </div>
      )}
    </div>
  );
};

export default FilmSearch;