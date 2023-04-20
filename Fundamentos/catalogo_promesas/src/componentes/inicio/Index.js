import React, { Component } from 'react'
import Footer from './cabeceras/Footer'
import Menu from './cabeceras/Menu'
import Actores from './Actores.js'

export default class Index extends Component {
  constructor(props) {
    super(props)
    this.state = {
      componenteActual: 'Inicio'
    }
    this.menu = [
      { texto: 'Inicio', url: '/', componente: 'Inicio'},
      { texto: 'Actores', url: '/Actores', componente: 'Actores' },
      { texto: 'Categorias', url: '/Categorias', componente: 'Category' },
      { texto: 'Peliculas', url: '/Peliculas', componente: 'Film' },
      { texto: 'Idiomas', url: '/Idiomas', componente: 'Language' }
    ]
  }

  cambiarComponente = (componente) => {
    this.setState({ componenteActual: componente })
  }

  render() {
    let componenteAMostrar = null;
    if (this.state.componenteActual === 'Inicio') {
      componenteAMostrar = <h4 className='inicioCatalogo'>Esto es el inicio del Catalogo</h4>;
    } else if (this.state.componenteActual === 'Actores') {
      componenteAMostrar = <Actores />;
    }

    return (
      <>
        <div className="menuContainer">
          <Menu menu={this.menu} cambiarComponente={this.cambiarComponente} />
        </div>
        {componenteAMostrar}
        <pre>
          {"\n"}
          {"    "}
        </pre>
        <div className="footerContainer"> 
          <Footer menu={this.menu} cambiarComponente={this.cambiarComponente}  /> 
        </div>
      </>
    )
  }
}
