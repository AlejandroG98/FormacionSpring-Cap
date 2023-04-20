import React, { Component } from 'react';

export default class Delete extends Component {
  componentDidMount() {
    const { actorId } = this.props;
    fetch(`http://localhost:8001/actores/${actorId}`)
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        try {
          if (data) {
            fetch(`http://localhost:8001/actores/${actorId}`, {
              method: 'DELETE'
            })
              .then(response => {
                if (response.status === 204) {
                  console.log('Actor eliminado.');
                } else {
                  throw new Error('Network response no es OK');
                }
              })
              .catch(error => console.error(error));
          } else {
            console.error(`No se encontró ningún actor con id ${actorId}`);
          }
        } catch (error) {
          console.error(error);
        }
      })
  }

  render() {
    return (
      <div>
        <p>Se ha eliminado el Actor</p>
      </div>
    );
  }
}
