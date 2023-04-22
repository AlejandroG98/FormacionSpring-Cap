import React, { Component } from 'react';

export default class Put extends Component {
  constructor(props) {
    super(props);
    this.state = {
      languageId: '',
      name: ''
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
    const { languageId, name } = this.state;
    fetch(`http://localhost:8001/idiomas/${languageId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        languageId,
        name
      })
    })
      .then(response => {
        if (response.ok) {
          alert('Idioma actualizado');
        } else {
          throw new Error('Error al actualizar el idioma');
        }
      })
      .catch(error => console.error(error));
  }

  render() {
    const { languageId, name } = this.state;
    return (
      <div className='PutActor'>
        <h2>Actualizar idioma:</h2>
        <form onSubmit={this.handleSubmit}>
          <div>
            <label>ID:</label>
            <input
              type="text"
              name="languageId"
              value={languageId}
              onChange={this.handleChange}
            />
          </div>
          <div>
            <label>Nombre:</label>
            <input
              type="text"
              name="name"
              value={name}
              onChange={this.handleChange}
            />
          </div>
          <button type="submit">Actualizar</button>
        </form>
      </div>
    );
  }
}
