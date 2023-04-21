import React, { Component, useState } from 'react';
import Delete from './Actores/Delete';
import GetFilmsFromActor from './Actores/GetFilmsFromActor';
import GetOne from './Actores/GetOne';

export default class GetAll extends Component {
  constructor(props) {
    super(props);
    this.state = {
      actors: [],
      actorIdToDelete: null,
      actorIdToConsult: null,
      filmsToShow: null
    };
  }

  componentDidMount() {
    fetch('http://localhost:8001/actores')
      .then(response => response.json())
      .then(data => {
        const actors = data.map(actor => {
          // Separo el nombre completo en: Nombre y Apellido
          const [firstName, ...lastName] = actor.nombre.toLowerCase().split(' ');
          // Hago que el primer caracter sea en mayúsculas tanto del nombre como del apellido
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

  handleDeleteClick = (actorId) => {
    this.setState({ actorIdToDelete: actorId });
  }

  handleConsultClick = (actorId) => {
    fetch(`http://localhost:8001/actores/peliculasDelActor/${actorId}`)
      .then(response => response.json())
      .then(data => {
        const filmsToShow = data.map(film => ({
          id: film.id,
          title: film.nombre
        }));
        this.setState({
          actorIdToConsult: actorId,
          filmsToShow
        });
      })
      .catch(error => {
        console.error(error);
        alert(`Error al consultar las películas del actor ${actorId}`);
      });
  }

  handleConsultCloseClick = () => {
    this.setState({
      actorIdToConsult: null,
      filmsToShow: null
    });
  }

  render() {
    return (
      <div>
        <table className="table table-hover">
          <thead>
            <tr>
              <th scope="col">Get One Actor</th>
              <th scope="col">Post</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td><GetOne /></td>
              <td>-</td>
            </tr>
          </tbody>
        </table>

        <table className="table table-hover">
          <thead>
            <tr>
              <th scope="col">Actores</th>
              <th scope="col">Películas del Actor</th>
              <th scope="col">Actualizar</th>
              <th scope="col">Eliminar</th>
            </tr>
          </thead>
          <tbody>
            {/* nombre y actorId -> Porque salen del ActorDTO ! */}
            {this.state.actors.map(actor => (
              <tr key={actor.actorId}>
                <td>{actor.nombre}</td>
                <td><button className="btn btn-info" data-toggle="modal" data-target="#exampleModal" onClick={() => this.handleConsultClick(actor.actorId)}>Consultar</button></td>
                <td>-</td>
                <td><button className="btn btn-danger" onClick={() => this.handleDeleteClick(actor.actorId)}>Eliminar</button></td>
              </tr>
            ))}
          </tbody>
        </table>
        {this.state.actorIdToDelete && <Delete actorId={this.state.actorIdToDelete} />}
        {this.state.actorIdToConsult && <GetFilmsFromActor
          actorId={this.state.actorIdToConsult}
          films={this.state.filmsToShow}
          onCloseClick={this.handleConsultCloseClick}
        />}
      </div>
    );
  }
}
