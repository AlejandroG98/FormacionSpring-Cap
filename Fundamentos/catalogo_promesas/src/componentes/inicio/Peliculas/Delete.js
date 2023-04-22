import React, { Component } from 'react';

export default class Delete extends Component {
  componentDidMount() {
    const { languageId } = this.props;
    fetch(`http://localhost:8001/idiomas/${languageId}`)
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        try {
          if (data) {
            fetch(`http://localhost:8001/idiomas/${languageId}`, {
              method: 'DELETE'
            })
              .then(response => {
                if (response.status === 204) {
                  console.log('Idioma eliminado.');
                  alert("Idioma eliminado!");
                } else {
                  alert("No se ha podido eliminar el Idioma porque esta asignado a una Película");
                  throw new Error('Network response no es OK');
                }
              })
              .catch(error => console.error(error));
          } else {
            console.error(`No se encontró ningún Idioma con id ${languageId}`);
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
