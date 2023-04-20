import React, { Component } from 'react'
import GetAll from './componentes/inicio/Categorias/GetAll'
import GetOne from './componentes/inicio/Categorias/GetOne'
import GetFilmsFromCategory from './componentes/inicio/Categorias/GetFilmsFromCategory'
import Post from './componentes/inicio/Categorias/Post'
import Put from './componentes/inicio/Categorias/Put'
import Delete from './componentes/inicio/Categorias/Delete'

export default class Categorias extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showGetAll: false,
            showGetOne: false,
            showGetFilmsFromCategory: false,
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
        const { showGetAll, showGetOne, showGetFilmsFromCategory, showPost, showPut, showDelete } = this.state;

        if (showGetAll) {
            return <GetAll cambiarComponente={this.props.cambiarComponente} />;
        } else if (showGetOne) {
            return <GetOne cambiarComponente={this.props.cambiarComponente} />;
        } else if (showPost) {
            return <Post cambiarComponente={this.props.cambiarComponente} />;
        }

        return (
            <div className='inicioActores'>
                <a> Hola, este es el Inicio de Categorias</a>
                <br />
                <br />
                <div className="btn-group" role="group" aria-label="Button group with nested dropdown">
                    <button type="button" className="btn btn-secondary" name="showGetAll" onClick={this.handleClick}>Get All</button>
                    <button type="button" className="btn btn-secondary" name="showGetOne" onClick={this.handleClick}>Get One Category</button>
                    <button type="button" className="btn btn-secondary" name="showPost" onClick={this.handleClick}>Post</button>
                </div>
            </div>
        );
    }
}
