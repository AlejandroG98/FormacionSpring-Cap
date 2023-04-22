import React, { Component } from 'react';

export default class Post extends Component {
  constructor(props) {
    super(props);
    this.state = {
      firstName: '',
      lastName: ''
    };
  }

  handleInputChange = event => {
    const { name, value } = event.target;
    this.setState({ [name]: value });
  };

  handleSubmit = event => {
    event.preventDefault();
    const { firstName, lastName } = this.state;
    fetch(`http://localhost:8001/actores?firstname=${firstName}&lastname=${lastName}`, {
      method: 'POST'
    })
      .then(response => {
        if (response.ok) {
          alert('Actor añadido');
        }
        return response.json();
      })
      .then(data => console.log(data))
      .catch(error => console.error(error));
  };

  render() {
    const { firstName, lastName } = this.state;
    return (
      <div>
        <h2>Agregar actor</h2>
        <form onSubmit={this.handleSubmit}>
          <div>
            <label htmlFor="firstName">Nombre:</label>
            <input type="text" name="firstName" value={firstName} onChange={this.handleInputChange} />
          </div>
          <div>
            <label htmlFor="lastName">Apellido:</label>
            <input type="text" name="lastName" value={lastName} onChange={this.handleInputChange} />
          </div>
          <button className="btn btn-info" type="submit">Agregar</button>
        </form>
      </div>
    );
  }
}
