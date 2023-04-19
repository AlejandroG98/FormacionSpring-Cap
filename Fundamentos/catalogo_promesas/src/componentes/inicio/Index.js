import React, { Component } from 'react'
import Footer from './cabeceras/Footer'
import Menu from './cabeceras/Menu'
import Actores from './Actores.js'

export default class Index extends Component {
  constructor(props) {
    super(props)
    this.state = {
      componenteActual: 'inicio'
    }
    this.menu = [
      { texto: 'Inicio', url: '/', componente: 'Inicio'},
      { texto: 'Actores', url: '/Actores', componente: 'Actores' },
    ]
  }

  cambiarComponente = (componente) => {
    this.setState({ componenteActual: componente })
  }

  render() {
    let componenteAMostrar = null;
    if (this.state.componenteActual === 'Inicio') {
      componenteAMostrar = <a>Inicio del Catalogo</a>;
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
          <Footer /> 
        </div>
      </>
    )
  }
}
