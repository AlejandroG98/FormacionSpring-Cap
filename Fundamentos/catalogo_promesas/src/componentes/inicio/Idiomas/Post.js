import React, { Component } from 'react';

export default class Post extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: ''
    };
  }

  handleInputChange = event => {
    const { name, value } = event.target;
    this.setState({ [name]: value });
  };

  handleSubmit = event => {
    event.preventDefault();
    const { name } = this.state;
    fetch(`http://localhost:8001/categorias?name=${name}`, {
      method: 'POST'
    })
      .then(response => {
        if (response.ok) {
          alert('Categoria añadida');
        }
        return response.json();
      })
      .then(data => console.log(data))
      .catch(error => console.error(error));
  };

  render() {
    const { name, } = this.state;
    return (
      <div>
        <h2>Agregar categoría</h2>
        <form onSubmit={this.handleSubmit}>
          <div>
            <label htmlFor="name">Nombre:</label>
            <input type="text" name="name" value={name} onChange={this.handleInputChange} />
          </div>
          <button className="btn btn-info" type="submit">Agregar</button>
        </form>
      </div>
    );
  }
}
