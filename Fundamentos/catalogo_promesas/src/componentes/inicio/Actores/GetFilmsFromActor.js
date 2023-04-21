import React, { Component } from 'react';

export default class GetFilmsFromActor extends Component {
  constructor(props) {
    super(props);
    this.state = {
      actorId: '',
      films: []
    };
  }

  handleInputChange = (event) => {
    this.setState({ actorId: event.target.value });
  }

  handleSubmit = (event) => {
    event.preventDefault();
    const { actorId } = this.state;
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
    const { films } = this.state;
    return (
      <div className='getFilmsFromActor'>
        <form onSubmit={this.handleSubmit}>
          <label>
            <a>Introduce la ID del actor:</a>
            <input type="number" value={this.state.actorId} onChange={this.handleInputChange} min={0} />
          </label><br />
          <button className="btn btn-info" type="submit">Buscar películas</button>
        </form>
        <h2>Películas del actor:</h2>
        <table className="table table-hover">
          <thead>
            <tr>
              <th scope="col">Películas</th>
            </tr>
          </thead>
          <tbody>
            {/* nombre y actorId -> Porque salen del ActorDTO ! */}
            {films.map(film => (
              <tr>
                <td key={film.filmId}>{film.titulo}</td>
              </tr>
            ))}
          </tbody>
        </table>


      </div >
    );
  }
}
