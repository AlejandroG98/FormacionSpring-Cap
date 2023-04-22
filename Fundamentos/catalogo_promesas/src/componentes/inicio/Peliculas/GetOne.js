import React, { useState } from 'react';

const LanguageSearch = () => {
  const [languageId, setLanguageId] = useState('');
  const [languageData, setLanguageData] = useState(null);
  const [error, setError] = useState(null);

  const handleActorIdChange = (event) => {
    setLanguageId(event.target.value);
  };

  const handleSearch = () => {
    fetch(`http://localhost:8001/idiomas/${languageId}`)
      .then((response) => {
        if (response.ok) {
          return response.json();
        } else {
          throw new Error('ERROR: No existe ningun Idioma con esa ID ...');
        }
      })
      .then((data) => {
        setLanguageData(data);
        setError(null);
      })
      .catch((error) => {
        setError(error.message);
        setLanguageData(null);
      });
  };

  return (
    <div className='getOne'>
      <label>
        <a>Ingrese el ID del Idioma:</a>
        <input type="number" value={languageId} onChange={handleActorIdChange} min={0} />
      </label><br />
      <button className="btn btn-info" onClick={handleSearch}>Buscar</button>
      {error && <p>{error}</p>}
      {languageData && (
        <div>
          <p>{languageData.name}</p>
        </div>
      )}
    </div>
  );
};

export default LanguageSearch;