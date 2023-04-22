import React, { Component } from 'react'
import GetAll from './Idiomas/GetAll'
import GetOne from './Idiomas/GetOne'
import GetFilmsFromLanguage from './Idiomas/GetFilmsFromLanguage'
import Post from './Idiomas/Post'
import Put from './Idiomas/Put'
import Delete from './Idiomas/Delete'

export default class Idiomas extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showGetAll: false,
            showGetOne: false,
            showGetFilmsFromLanguage: false,
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
        const { showGetAll, showGetOne, showGetFilmsFromLanguage, showPost, showPut, showDelete } = this.state;

        if (showGetAll) {
            return <GetAll cambiarComponente={this.props.cambiarComponente} />;
        } else if (showGetOne) {
            return <GetOne cambiarComponente={this.props.cambiarComponente} />;
        } else if (showGetFilmsFromLanguage) {
            return <GetFilmsFromLanguage cambiarComponente={this.props.cambiarComponente} />;
        } else if (showPut) {
            return <Put cambiarComponente={this.props.cambiarComponente} />;
        } else if (showPost) {
            return <Post cambiarComponente={this.props.cambiarComponente} />;
        }

        return (
            <div className='inicioActores'>
                <a> Hola, este es el Inicio de Idiomas</a>
                <br />
                <br />
                <div className="btn-group" role="group" aria-label="Button group with nested dropdown">
                    <button type="button" className="btn btn-secondary" name="showGetAll" onClick={this.handleClick}>Get All</button>
                    <button type="button" className="btn btn-secondary" name="showGetOne" onClick={this.handleClick}>Get One Language</button>
                    <button type="button" className="btn btn-secondary" name="showGetFilmsFromLanguage" onClick={this.handleClick}>Get Films from Language</button>
                    <button type="button" className="btn btn-secondary" name="showPost" onClick={this.handleClick}>Post</button>
                    <button type="button" className="btn btn-secondary" name="showPut" onClick={this.handleClick}>Put</button>
                </div>
            </div>
        );
    }
}
