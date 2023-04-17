// Imprime por pantalla los indices y no los valores
let t = [10,20,30]
for(i in t)
{
    console.log(i);
}

// Imprime por pantalla los valores y no los indices
t = [10,20,30]
for(v of t)
{
    console.log(v);
}

function suma(a,b) {return a+b}

function filtrado(item, a){
    return item > a;
}

t = [10, 20, 30];

for(let i = 0; i < 10; i++){
    t = t.filter(item => filtrado(item, i)); 
}

console.log(t); 

// ARRAYS
let tab = new Array();
tab = [10,20,30];
tab[10]= 'otro';
// Indices
for(i in tab){
    console.log(tab[i])
}
// Valores
for(v of tab){
    console.log(v)
}

let o = new Object();
o = {
  nombre: 'Pepito',
  apellidos: 'Grillo',
  nom: function() {
    return this.nombre + ' ' + this.apellidos;
  }
}
console.log(`${o.nombre} ${o.apellidos} ${o.nom()}`);

console.log(cond ? `Cierto` : `Falso`)

// function avg() {
//     var rslt = 0;
//     for (var i = 0; i < arguments.length; i++) {
//         rslt += arguments[i];
//     }
//     return arguments.length ? (rslt / arguments.length) : 0;
// }
// console.log(avg(10,20,30))
// console.log(avg(...t))
// punto = { x: 10, y: 20}
// function coordenadas(x, y) {
//     return x + y;
// }
// coordenadas = (x, y) =>  x + y;
// console.log(coordenadas(punto.x, punto.y))
// console.log((coordenadas)(punto.x, punto.y))

// console.log(coordenadas(...punto))

// function suma(a, b) { return a + b; }
// suma = (a, b) => a + b;

// t.filter(item => item % 2)

// t.filter(item => item > 10)
// for(let i=0; i < 10; i++)
//     t.filter(function(item) { return item > a; })

// filtrado = function(item) { return item > 10; }

// for(let i=0; i < 10; i++)
//     t.filter(filtrado);


// let tab = new Array();
// tab = [10, 20, 30];
// tab[5] = (a, b) => a + b;
// tab[10] = 'manolo'
// tab.push('benito')
// tab.splice(2, 1)
// console.log('Indices')
// for (v in tab) {
//     console.log(v)
// }
// console.log('Valores')
// for (v of tab) {
//     console.log(v)
// }
// console.log(tab[5](2,3))


function Persona(id, nombre, apellidos, trata) {
    var vm = this;
    var tratamiento = trata ? trata : 'Sr.';
    this.id = id
    this.nombre = nombre
    this.apellidos = apellidos

    this.nombreCompleto = function() { 
        return tratamiento + ' ' + vm.nombre + ' ' + this.apellidos; 
    }
    this.Tratamiento = function(value) { 
        if(value) 
            tratamiento = value
        else
            return tratamiento; 
    }
}

class PersonaObj {
    constructor(id, nombre, apellidos, trata) {
        this._tratamiento = trata ? trata : 'Sr.';
        this.id = id
        this.nombre = nombre
        this.apellidos = apellidos
    }
    nombreCompleto() { 
        return this._tratamiento + ' ' + this.nombre + ' ' + this.apellidos; 
    }
    set Tratamiento(value) { 
        this._tratamiento = value ? value : 'Sr.'
    }
    get Tratamiento() { 
        return this._tratamiento;
    }
}
function fn(tratamiento, algo) {
    return tratamiento + ' ' + this.nombre + ' ' + this.apellidos; 
}

Persona.prototype.count = 0;
Persona.prototype.saluda = function() { 
    return `Hola, soy ${this.nombre}`; 
};

var alum1 = new Persona(1, 'Pepito', 'Grillo');
var alum2 =  Persona(2, 'Carmelo', 'Coton');
var alum3 =  PersonaObj(2, 'Carmelo', 'Coton');

alum3.id = 3;
this._tratamiento =  'Don';
alum3.Tratamiento = 'Don';
alum3.setTratamiento('Don');

cad = alum3.Tratamiento;
cad = alum3.getTratamiento();

cad = alum1.Tratamiento();
alum1.Tratamiento('Don.')

fn.nombre = 'algo';

cad = fn();
x = 1;
y = 2;

punto = { x: x, y: y };
punto = { x, y, z: 0, suma(x, y) {return x + y } };

fn.bind(alum2);
cad = fn('Sr.');
fn.bind(alum1);
cad = fn('Sr.');
// ...
cad = fn('Sr.');
cad = fn(...['Sr.', 'Otro']);

cad = fn.call(alum1, 'Sr.', 'Otro')
cad = fn.apply(alum2, ['Sr.', 'Otro'])

alum1.id
alum1.count = 1;
alum1.prototype.count = 0;

cad = alum1.saluda();
cad = alum2.saluda();
alum1.prototype.saluda = function() { 
    return `Adios, soy ${this.nombre}`; 
};
cad = alum2.saluda();

delete alum2.id
alum2.nombreCompleto = () => this.apellidos + ', ' + this.nombre; 
alum1.edad = 99;
