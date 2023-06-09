import React, { Component } from 'react'
import GetAll from './Actores/GetAll'
import GetOne from './Actores/GetOne'
import GetFilmsFromActor from './Actores/GetFilmsFromActor'
import Post from './Actores/Post'
import Put from './Actores/Put'
import Delete from './Actores/Delete'

export default class Actores extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showGetAll: false,
      showGetOne: false,
      showGetFilmsFromActor: false,
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
    const { showGetAll, showGetOne, showGetFilmsFromActor, showPost, showPut, showDelete } = this.state;

    if (showGetAll) {
      return <GetAll cambiarComponente={this.props.cambiarComponente} />;
    } else if (showGetOne) {
      return <GetOne cambiarComponente={this.props.cambiarComponente} />;
    } else if (showGetFilmsFromActor) {
      return <GetFilmsFromActor cambiarComponente={this.props.cambiarComponente} />;
    } else if (showPut) {
      return <Put cambiarComponente={this.props.cambiarComponente} />;
    } else if (showPost) {
      return <Post cambiarComponente={this.props.cambiarComponente} />;
    }

    return (
      <div className='inicioActores'>
        <a> Hola, este es el Inicio de Actores</a>
        <br />
        <br />
        <div className="btn-group" role="group" aria-label="Button group with nested dropdown">
          <button type="button" className="btn btn-secondary" name="showGetAll" onClick={this.handleClick}>Get All</button>
          <button type="button" className="btn btn-secondary" name="showGetOne" onClick={this.handleClick}>Get One Actor</button>
          <button type="button" className="btn btn-secondary" name="showGetFilmsFromActor" onClick={this.handleClick}>Get Films from Actor</button>
          <button type="button" className="btn btn-secondary" name="showPost" onClick={this.handleClick}>Post</button>
          <button type="button" className="btn btn-secondary" name="showPut" onClick={this.handleClick}>Put</button>
        </div>
      </div>
    );
  }
}
