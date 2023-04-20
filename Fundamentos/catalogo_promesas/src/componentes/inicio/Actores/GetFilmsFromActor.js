import React, { Component } from 'react';

export default class GetFilmsFromActor extends Component {
  constructor(props) {
    super(props);
    this.state = {
      films: []
    };
  }

  componentDidMount() {
    const { actorId } = this.props;
    fetch(`http://localhost:8001/actores/peliculasDelActor/${actorId}`)
      .then(response => response.json())
      .then(data => {
        const films = data.map(film => ({
          filmId: film.key,
          titulo: film.value
        }));
        this.setState({ films });
      })
      .catch(error => console.error(error));
  }

  render() {
    return (
      <div>
        <h3>Películas del actor:</h3>
        <ul>
          {this.state.films.map(film => (
            <li key={film.filmId}>{film.titulo}</li>
          ))}
        </ul>
      </div>
    );
  }
}
