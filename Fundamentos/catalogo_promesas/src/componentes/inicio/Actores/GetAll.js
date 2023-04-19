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
      .then(data => this.setState({ actors: data }));
  }

  render() {
    return (
      <div>
        <h1>getAll Actors</h1>
        <ul>
          {this.state.actors.map(actor => (
            <li key={actor.id}>{actor.nombre}</li>
          ))}
        </ul>
      </div>
    );
  }
}
