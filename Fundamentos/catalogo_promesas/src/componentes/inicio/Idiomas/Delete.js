import React, { Component } from 'react';

export default class Delete extends Component {
  componentDidMount() {
    const { categoryId } = this.props;
    fetch(`http://localhost:8001/categorias/${categoryId}`)
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        try {
          if (data) {
            fetch(`http://localhost:8001/categorias/${categoryId}`, {
              method: 'DELETE'
            })
              .then(response => {
                if (response.status === 204) {
                  console.log('Category eliminado.');
                  alert("Category eliminado!");
                } else {
                  alert("No se ha podido eliminar el Category porque esta asignado a una Película");
                  throw new Error('Network response no es OK');
                }
              })
              .catch(error => console.error(error));
          } else {
            console.error(`No se encontró ningún Category con id ${categoryId}`);
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
