import React, { Component } from 'react';

export default class Put extends Component {
  constructor(props) {
    super(props);
    this.state = {
      actorId: '',
      nombre: '',
      apellidos: ''
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {
    const { name, value } = event.target;
    this.setState({ [name]: value });
  }

  handleSubmit(event) {
    event.preventDefault();
    const { actorId, nombre, apellidos } = this.state;
    fetch(`http://localhost:8001/actores/${actorId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        actorId,
        nombre,
        apellidos
      })
    })
      .then(response => {
        if (response.ok) {
          alert('Actor actualizado');
        } else {
          throw new Error('Error al actualizar el actor');
        }
      })
      .catch(error => console.error(error));
  }

  render() {
    const { actorId, nombre, apellidos } = this.state;
    return (
      <div className='PutActor'>
        <h2>Actualizar actor:</h2>
        <form onSubmit={this.handleSubmit}>
          <div>
            <label>ID:</label>
            <input
              type="text"
              name="actorId"
              value={actorId}
              onChange={this.handleChange}
            />
          </div>
          <div>
            <label>Nombre:</label>
            <input
              type="text"
              name="nombre"
              value={nombre}
              onChange={this.handleChange}
            />
          </div>
          <div>
            <label>Apellidos:</label>
            <input
              type="text"
              name="apellidos"
              value={apellidos}
              onChange={this.handleChange}
            />
          </div>
          <button className="btn btn-info" type="submit">Actualizar</button>
        </form>
      </div>
    );
  }
}
