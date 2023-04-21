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
      <div className="modal" tabIndex={-1} role="dialog">
        <div className="modal-dialog" role="document">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title">Películas del actor:</h5>
              <button
                type="button"
                className="close"
                data-dismiss="modal"
                aria-label="Close"
                onClick={this.props.onCloseClick}
              >
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div className="modal-body">
              <ul>
                {this.state.films.map(film => (
                  <li key={film.filmId}>{film.titulo}</li>
                ))}
              </ul>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
