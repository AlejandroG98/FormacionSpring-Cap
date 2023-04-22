import React, { useState, useEffect } from 'react';
import Delete from './Delete';

export default function LanguageList() {
  const [peliculas, setPeliculas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filmIdToDelete, setFilmIdToDelete] = useState(null);

  useEffect(() => {
    fetch('http://localhost:8001/peliculas')
      .then(resp => {
        if (resp.ok) {
          resp.json().then(data => {
            setPeliculas(data);
            setLoading(false);
          });
        } else {
          console.error(`${resp.status} - ${resp.statusText}`);
          setLoading(false);
        }
      })
      .catch(err => {
        console.error(err);
        setLoading(false);
      });
  }, []);

  const handleDeleteClick = (filmId) => {
    setFilmIdToDelete(filmId);
  }

  if (loading) {
    return <div>Cargando peliculas...</div>;
  }

  if (peliculas.length === 0) {
    return <div>No se encontraron películas.</div>;
  }

  return (
    <div>
      <table className="table table-hover">
        <thead>
          <tr>
            <th scope="col">Película</th>
            <th scope="col">Eliminar</th>
          </tr>
        </thead>
        <tbody>
          {peliculas.map(pelicula => (
            <tr key={pelicula.filmId}>
              <td>{pelicula.title}</td>
              <td><button className="btn btn-danger" onClick={() => handleDeleteClick(pelicula.filmId)}>Eliminar</button></td>
            </tr>
          ))}
        </tbody>
      </table>
      {filmIdToDelete && <Delete languageId={filmIdToDelete} />}
    </div>
  );
}
