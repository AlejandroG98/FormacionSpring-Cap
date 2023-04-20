import React, { Component, useState } from 'react';
import Delete from './Delete';

export default class GetAll extends Component {
  constructor(props) {
    super(props);
    this.state = {
      actors: []
    };
  }

  componentDidMount() {
    fetch('http://localhost:8001/actores/getAll')
      .then(response => response.json())
      .then(data => {
        const actors = data.map(actor => {
          // Separo el nomber completo en: Nombre y Apellido
          const [firstName, ...lastName] = actor.nombre.toLowerCase().split(' ');
          // Hago que el primer caracter sea en mayusculas tanto del nombre como del apellido
          const capitalizedFirstName = firstName.charAt(0).toUpperCase() + firstName.slice(1);
          const capitalizedLastName = lastName.map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(' ');
          // Devolver el nombre completo con el cambio
          return {
            ...actor,
            nombre: capitalizedFirstName + ' ' + capitalizedLastName
          };
        });
        this.setState({ actors });
      });
  }

  handleDelete = (actorId) => {
    fetch(`http://localhost:8001/actores/${actorId}`, {
      method: 'DELETE'
    })
      .then(response => {
        if (response.ok) {
          const updatedActors = this.state.actors.filter(actor => actor.id !== actorId);
          this.setState({ actors: updatedActors });
        } else {
          throw new Error('Error al eliminar actor');
        }
      })
      .catch(error => {
        console.error(error);
      });
  }

  render() {
    return (
      <div>
        <table className="table table-hover">
          <thead>
            <tr>
              <th scope="col">Actores</th>
              <th scope="col">Eliminar</th>
            </tr>
          </thead>
          <tbody>
            {this.state.actors.map(actor => (
              <tr key={actor.id}>
                <td>{actor.nombre}</td>
                <td>
                  <Delete actorId={actor.id} handleDelete={this.handleDelete} />
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}
