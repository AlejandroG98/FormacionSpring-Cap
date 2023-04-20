import React, { Component, useState } from 'react';
import Delete from './Delete';
import GetFilmsFromCategory from './GetFilmsFromCategory';

export default class GetAll extends Component {
  constructor(props) {
    super(props);
    this.state = {
      categories: [],
      categoryIdToDelete: null,
      categoryIdToConsult: null,
      filmsToShow: null
    };
  }

  componentDidMount() {
    fetch('http://localhost:8001/categorias/getAll')
      .then(response => response.json())
      .then(data => {
        const categories = data.map(actor => {
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
        this.setState({ categories });
      });
  }

  handleDeleteClick = (categoryId) => {
    this.setState({ categoryIdToDelete: categoryId });
  }

  handleConsultClick = (categoryId) => {
    fetch(`http://localhost:8001/categorias/peliculasDeLaCategoria/${categoryId}`)
      .then(response => response.json())
      .then(data => {
        const filmsToShow = data.map(film => ({
          id: film.id,
          title: film.nombre
        }));
        this.setState({
          categoryIdToConsult: categoryId,
          filmsToShow
        });
      })
      .catch(error => {
        console.error(error);
        alert(`Error al consultar las películas del actor ${categoryId}`);
      });
  }

  handleConsultCloseClick = () => {
    this.setState({
      categoryIdToConsult: null,
      filmsToShow: null
    });
  }

  render() {
    return (
      <div>
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
            {/* nombre y categoryId -> Porque salen del ActorDTO ! */}
            {this.state.categories.map(actor => (
              <tr key={actor.categoryId}>
                <td>{actor.nombre}</td>
                <td><button className="btn btn-info" onClick={() => this.handleConsultClick(actor.categoryId)}>Consultar</button></td>
                <td>-</td>
                <td><button className="btn btn-danger" onClick={() => this.handleDeleteClick(actor.categoryId)}>Eliminar</button></td>
              </tr>
            ))}
          </tbody>
        </table>
        {this.state.categoryIdToDelete && <Delete categoryId={this.state.categoryIdToDelete} />}
        {this.state.categoryIdToConsult && <GetFilmsFromCategory
          categoryId={this.state.categoryIdToConsult}
          films={this.state.filmsToShow}
          onCloseClick={this.handleConsultCloseClick}
        />}
      </div>
    );
  }
}
