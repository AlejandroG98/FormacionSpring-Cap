import logo from './logo.svg';
import './App.css';
import React, { Component } from 'react'
import { Contador } from './Componentes';

export default class App extends Component {
  render() {
    return (
      <>
      <main className='container-fluid'>
        {/* <Home/> */}
        {/* <DemosJSX /> */}
        <Contador init={10} delta={2} />
      </main>
      </>
    )
  }
}

// No se renderizan los true, false, null y undefined
class DemosJSX extends Component {
  render() {
    let mundo = 'mundo'
    let saluda = <h1>Hola {mundo}</h1>
    let estilo = 'App-link'
    let dim = {width:100, height:50}
    let errorStyle = {color:'white' , backgroundColor: 'red'}
    let limpia = true
    let falsa
    let lista = [
      {id: 1, nombre:'Barcelona'},
      {id: 2, nombre:'Madrid'},
      {id: 3, nombre:'Sevilla'},
      {id: 4, nombre:'Zaragoza'}
    ]
    return (
      <>
      {limpia ? 'verdadero' : 'falso'}
      <br/>
      {limpia ? <b>verdadero</b> : <b>falso</b>}
      <br/>
      <b>{limpia ? 'verdadero' : 'falso'}</b>
      <br/>
      {/* SI SE DA LA CONDICIÓN DE limpia = true , IMPRIME EL H2 */}
      {limpia && <h2>Limpia</h2>}
      {/* VALOR ALTERNATIVO: SI FALTA ES NULO, HACE EL RESTO */}
      {falsa ?? <b>No existe</b>}
      <br/>
      <div style={errorStyle}>DemosJSX</div>
      
      <h2 className={estilo}>Hola {saluda}
      <span dangerouslySetInnerHTML={{__html: mundo}} /></h2>
      <img src={logo} className="App-logo" alt="logo" {...dim} hidden={false} />

      {/* ARRAYS */}
      <ul>
        {[1,2,3,4,3,2,1].map((item,index) => <li key={index}>Elemento {item}</li>)}
      </ul>
      <ul>
        {lista.map(item => <option key={item.id} value={item.id}> {item.nombre} </option> )}
      </ul>
      </>
    )
  }
}

// Componente Home
function Home() {
  let url = process.env.REACT_APP_API_URL

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
