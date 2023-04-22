import React, { Component } from 'react'
import GetAll from './Peliculas/GetAll'
import GetOne from './Peliculas/GetOne'
import Post from './Peliculas/Post'
import Put from './Peliculas/Put'
import Delete from './Peliculas/Delete'

export default class Peliculas extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showGetAll: false,
      showGetOne: false,
      showPost: false,
      showPut: false,
      showDelete: false
    };
    this.handleClick = this.handleClick.bind(this);
  }

  handleClick(event) {
    const { name } = event.target;
    this.setState({ [name]: true });
  }

  render() {
    const { showGetAll, showGetOne, showPost, showPut, showDelete } = this.state;

    if (showGetAll) {
      return <GetAll cambiarComponente={this.props.cambiarComponente} />;
    } else if (showGetOne) {
      return <GetOne cambiarComponente={this.props.cambiarComponente} />;
    } else if (showPut) {
      return <Put cambiarComponente={this.props.cambiarComponente} />;
    } else if (showPost) {
      return <Post cambiarComponente={this.props.cambiarComponente} />;
    }

    return (
      <div className='inicioActores'>
        <a> Hola, este es el Inicio de Peliculas</a>
        <br />
        <br />
        <div className="btn-group" role="group" aria-label="Button group with nested dropdown">
          <button type="button" className="btn btn-secondary" name="showGetAll" onClick={this.handleClick}>Get All</button>
          <button type="button" className="btn btn-secondary" name="showGetOne" onClick={this.handleClick}>Get One Film</button>
          <button type="button" className="btn btn-secondary" name="showPost" onClick={this.handleClick}>Post</button>
          <button type="button" className="btn btn-secondary" name="showPut" onClick={this.handleClick}>Put</button>
        </div>
      </div>
    );
  }
}
