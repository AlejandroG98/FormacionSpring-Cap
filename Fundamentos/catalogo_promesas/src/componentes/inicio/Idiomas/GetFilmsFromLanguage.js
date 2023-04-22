import React, { Component } from 'react';

export default class GetFilmsFromLanguage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      categoryId: '',
      films: []
    };
  }

  handleInputChange = (event) => {
    this.setState({ categoryId: event.target.value });
  }

  handleSubmit = (event) => {
    event.preventDefault();
    const { categoryId } = this.state;
    fetch(`http://localhost:8001/idiomas/peliculasDelIdioma/${categoryId}`)
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
            <a>Introduce la ID de la Categoría:</a>
            <input type="number" value={this.state.categoryId} onChange={this.handleInputChange} min={0} />
          </label><br />
          <button className="btn btn-info" type="submit">Buscar películas</button>
        </form>
        <h2>Películas de la Categoría:</h2>
        <table className="table table-hover">
          <thead>
            <tr>
              <th scope="col">Películas</th>
            </tr>
          </thead>
          <tbody>
            {/* nombre y categoryId -> Porque salen del ActorDTO ! */}
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
