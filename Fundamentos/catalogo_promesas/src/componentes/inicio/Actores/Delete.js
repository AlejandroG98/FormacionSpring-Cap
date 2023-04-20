import React from 'react';

const Delete = ({ actorId, handleDelete }) => {
  const handleClick = () => {
    handleDelete(actorId);  
  }

  return (
    <button className="btn btn-danger" onClick={handleClick}>Eliminar</button>
  );
}

export default Delete;
