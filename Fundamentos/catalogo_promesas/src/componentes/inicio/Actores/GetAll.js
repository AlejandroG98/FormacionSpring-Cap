import React, { Component } from 'react';

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
          // Hago que el primer caracter sea en mayusculas tanto del nomber como del apellido
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

  render() {
    return (
      <div>
        <table class="table table-hover">
          <thead>
            <tr>
              <th scope="col">Actores</th>
              <th scope="col">Opciones</th>
            </tr>
          </thead>
          <tbody>
            {this.state.actors.map(actor => (
              <tr key={actor.id}>
                <td>{actor.nombre}</td>
                <td>---</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}
