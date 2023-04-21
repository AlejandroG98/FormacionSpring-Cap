import React, { useState } from 'react';

const CategorySearch = () => {
  const [categoryId, setCategoryId] = useState('');
  const [categoryData, setCategoryData] = useState(null);
  const [error, setError] = useState(null);

  const handleActorIdChange = (event) => {
    setCategoryId(event.target.value);
  };

  const handleSearch = () => {
    fetch(`http://localhost:8001/categorias/${categoryId}`)
      .then((response) => {
        if (response.ok) {
          return response.json();
        } else {
          throw new Error('ERROR: No existe ninguna Categoria con esa ID ...');
        }
      })
      .then((data) => {
        // Convertir el primer carácter del nombre y apellidos a mayúsculas y el resto a minúsculas
        setCategoryData(data);
        setError(null);
      })
      .catch((error) => {
        setError(error.message);
        setCategoryData(null);
      });
  };

  return (
    <div className='getOne'>
      <label>
        <a>Ingrese el ID de la Categoria:</a>
        <input type="number" value={categoryId} onChange={handleActorIdChange} />
      </label><br />
      <button className="btn btn-info" onClick={handleSearch}>Buscar</button>
      {error && <p>{error}</p>}
      {categoryData && (
        <div>
          <p>{categoryData.nombre}</p>
        </div>
      )}
    </div>
  );
};

export default CategorySearch;
