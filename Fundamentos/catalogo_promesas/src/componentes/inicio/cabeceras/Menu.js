import React, { Component } from 'react'

export default class Menu extends Component {
  handleClick = (event, componente) => {
    event.preventDefault();
    this.props.cambiarComponente(componente);
  }

  render() {
    const menuItems = this.props.menu.map((item) => {
      return (
        <li key={item.url}>
          <a href={item.url} onClick={(event) => this.handleClick(event, item.componente)}>{item.texto}</a>
        </li>
      );
    });

    return (
      <>
        <header>
          <meta name="viewport" content="width=device-width, initial-scale=1" />
          <section className="top-nav">
            <div>Falta el Logo</div>
            <input id="menu-toggle" type="checkbox" />
            <label className="menu-button-container" htmlFor="menu-toggle">
              <div className="menu-button" />
            </label>
            <ul className="menu no-underline">
              {menuItems}
            </ul>
          </section>
        </header>
      </>
    )
  }
}
