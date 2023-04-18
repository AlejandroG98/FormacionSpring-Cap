import logo from './logo.svg';
import './App.css';
import React, { Component } from 'react'

export default class App extends Component {
  render() {
    return (
      <>
        <Home />
        <DemosJSX />
      </>
    )
  }
}

class DemosJSX extends Component {
  render() {
    let mundo = 'mundo'
    let saluda = <h1>Hola {mundo}</h1>
    let estilo = 'App-link'
    let dim = {width:100, height:50}
    let errorStyle = {color:'white' , backgroundColor: 'red'}
    return (
      <>
      <div style={errorStyle}>DemosJSX</div>
      <h2 className={estilo}>Hola {saluda}</h2>
      <img src={logo} className="App-logo" alt="logo" {...dim} hidden={false} />
      </>
    )
  }
}

// Componente Home
function Home() {
  let url = process.env.API_URL
  url = "XXXXX"
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h2>url: {url}</h2>
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}
