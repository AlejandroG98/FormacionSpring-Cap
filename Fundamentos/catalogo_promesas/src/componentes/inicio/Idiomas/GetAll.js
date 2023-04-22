import React, { useState, useEffect } from 'react';
import Delete from './Delete';

export default function LanguageList() {
  const [idiomas, setIdiomas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [languageIdToDelete, setLanguageIdToDelete] = useState(null);

  useEffect(() => {
    fetch('http://localhost:8001/idiomas')
      .then(resp => {
        if (resp.ok) {
          resp.json().then(data => {
            setIdiomas(data);
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

  const handleDeleteClick = (languageId) => {
    setLanguageIdToDelete(languageId);
  }

  if (loading) {
    return <div>Cargando categorías...</div>;
  }

  if (idiomas.length === 0) {
    return <div>No se encontraron idiomas.</div>;
  }

  return (
    <div>
      <table className="table table-hover">
        <thead>
          <tr>
            <th scope="col">Idioma</th>
            <th scope="col">Eliminar</th>
          </tr>
        </thead>
        <tbody>
          {idiomas.map(idioma => (
            <tr key={idioma.languageId}>
              <td>{idioma.name}</td>
              <td><button className="btn btn-danger" onClick={() => handleDeleteClick(idioma.languageId)}>Eliminar</button></td>
            </tr>
          ))}
        </tbody>
      </table>
      {languageIdToDelete && <Delete languageId={languageIdToDelete} />}
    </div>
  );
}
