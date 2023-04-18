import React, { Component } from 'react'

{/* LAS PROPIEDADES SON INMUTABLES, SOLO SON DE ENTRADA */}

// Componente funcional : Imprime el valor que pasamos por param
// Si le llamo props.valor, en la llamada la componente tiene que ser valor= algo
function Pantalla(props){
    return <output>{props.valor}</output>
}

export class Contador extends Component {
    constructor(props){
        super(props)
        // Si no existe valor, lo inicas a 0
        this.cont = this.props.init ?? 0
    }
    render() {
    return (
        <div>
            <Pantalla valor={this.cont}/>
            <div>
                <input type="button" value='-'/>
                <input type="button" value='+'/> 
            </div>
        </div>
    )
  }
}
