import React, { useState, useEffect } from 'react';
import Delete from './Delete';

function CategoryList() {
  const [categorias, setCategorias] = useState([]);
  const [loading, setLoading] = useState(true);
  const [categoryIdToDelete, setCategoryIdToDelete] = useState(null);

  useEffect(() => {
    fetch('http://localhost:8001/categorias')
      .then(resp => {
        if (resp.ok) {
          resp.json().then(data => {
            setCategorias(data);
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

  const handleDeleteClick = (categoryId) => {
    setCategoryIdToDelete(categoryId);
  }

  if (loading) {
    return <div>Cargando categorías...</div>;
  }

  if (categorias.length === 0) {
    return <div>No se encontraron categorías.</div>;
  }

  return (
    <div>
      <table className="table table-hover">
        <thead>
          <tr>
            <th scope="col">Categoría</th>
            <th scope="col">Eliminar</th>
          </tr>
        </thead>
        <tbody>
          {categorias.map(categoria => (
            <tr key={categoria.categoryId}>
              <td>{categoria.name}-{ }</td>
              <td><button className="btn btn-danger" onClick={() => handleDeleteClick(categoria.categoryId)}>Eliminar</button></td>
            </tr>
          ))}
        </tbody>
      </table>
      {categoryIdToDelete && <Delete categoryId={categoryIdToDelete} />}
    </div>
  );
}

export default CategoryList;
