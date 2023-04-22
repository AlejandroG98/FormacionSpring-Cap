import React, { Component } from 'react';

export default class Delete extends Component {
  componentDidMount() {
    const { filmId } = this.props;
    fetch(`http://localhost:8001/peliculas/${filmId}`)
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        try {
          if (data) {
            fetch(`http://localhost:8001/peliculas/${filmId}`, {
              method: 'DELETE'
            })
              .then(response => {
                if (response.status === 204) {
                  console.log('Película eliminado.');
                  alert("Película eliminado!");
                } else {
                  alert("No se ha podido eliminar la Película porque tiene asignaciones");
                  throw new Error('Network response no es OK');
                }
              })
              .catch(error => console.error(error));
          } else {
            console.error(`No se encontró ningúna Película con id ${filmId}`);
          }
        } catch (error) {
          console.error(error);
        }
      })
  }

  render() {
    return (
      <></>
    );
  }
}
