import React, { Component } from 'react';

export default class GetFilmsFromLanguage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      languageId: '',
      films: []
    };
  }

  handleInputChange = (event) => {
    this.setState({ languageId: event.target.value });
  }

  handleSubmit = (event) => {
    event.preventDefault();
    const { languageId } = this.state;
    fetch(`http://localhost:8001/idiomas/peliculasDelIdioma/${languageId}`)
      .then(response => response.json())
      .then(data => {
        const films = data.map(film => ({
          languageId: film.key,
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
            <a>Introduce la ID del Idioma:</a>
            <input type="number" value={this.state.languageId} onChange={this.handleInputChange} min={0} />
          </label><br />
          <button className="btn btn-info" type="submit">Buscar películas</button>
        </form>
        <h2>Películas del Idioma:</h2>
        <table className="table table-hover">
          <thead>
            <tr>
              <th scope="col">Películas</th>
            </tr>
          </thead>
          <tbody>
            {/* nombre y languageId -> Porque salen del ActorDTO ! */}
            {films.map(film => (
              <tr>
                <td key={film.languageId}>{film.titulo}</td>
              </tr>
            ))}
          </tbody>
        </table>


      </div >
    );
  }
}
