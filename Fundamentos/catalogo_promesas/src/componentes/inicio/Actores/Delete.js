import React, { useState } from 'react';

function DeleteActor() {
  const [actorId, setActorId] = useState('');
  const [deleteMessage, setDeleteMessage] = useState('');
  const [actorData, setActorData] = useState(null);
  const [confirmDelete, setConfirmDelete] = useState(false);

  const handleDelete = () => {
    fetch(`http://localhost:8001/actores/${actorId}`, {
      method: 'DELETE'
    })
      .then(response => {
        if (response.ok) {
          setDeleteMessage(`Actor con ID ${actorId} eliminado exitosamente`);
          setActorData(null);
          setActorId('');
        } else {
          throw new Error('Error al eliminar actor');
        }
      })
      .catch(error => {
        setDeleteMessage(error.message);
      });
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    fetch(`http://localhost:8001/actores/${actorId}`)
      .then(response => response.json())
      .then(data => {
        setActorData(data);
        setConfirmDelete(true);
      })
      .catch(error => {
        setDeleteMessage('Error al obtener datos del actor');
      });
  }

  return (
    <div class="deleteActor">
      <form onSubmit={handleSubmit}>
        <label>
          <a>ID del Actor a eliminar:</a>
          <input type="number" value={actorId} onChange={(e) => setActorId(e.target.value)} min="1" />
        </label><br/>
        <button class="btn btn-danger" type="submit">Eliminar</button>
      </form>
      {deleteMessage && <p>{deleteMessage}</p>}
      {actorData && confirmDelete && (
        <div>
          <p>¿Estás seguro que deseas eliminar al siguiente actor?</p>
          <p>Nombre: {actorData.nombre}</p>
          <p>Apellidos: {actorData.apellidos}</p>
          <button class="btn btn-success" onClick={handleDelete}>Sí, eliminar</button>
          <button class="btn btn-secondary" onClick={() => setConfirmDelete(false)}>Cancelar</button>
        </div>
      )}
    </div>
  );
}

export default DeleteActor;
