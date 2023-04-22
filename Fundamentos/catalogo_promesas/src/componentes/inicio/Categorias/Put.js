import React, { Component } from 'react';

export default class Put extends Component {
  constructor(props) {
    super(props);
    this.state = {
      categoryId: '',
      nombre: ''
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
    const { categoryId, nombre } = this.state;
    fetch(`http://localhost:8001/categorias/${categoryId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        categoryId,
        nombre
      })
    })
      .then(response => {
        if (response.ok) {
          alert('Categoría actualizado');
        } else {
          throw new Error('Error al actualizar la categoría');
        }
      })
      .catch(error => console.error(error));
  }

  render() {
    const { categoryId, nombre } = this.state;
    return (
      <div className='PutActor'>
        <h2>Actualizar categoría:</h2>
        <form onSubmit={this.handleSubmit}>
          <div>
            <label>ID:</label>
            <input
              type="text"
              name="categoryId"
              value={categoryId}
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
          <button type="submit">Actualizar</button>
        </form>
      </div>
    );
  }
}
