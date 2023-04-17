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

function avg() {
    var rslt = 0;
    for (var i = 0; i < arguments.length; i++) {
        rslt += arguments[i];
    }
    return arguments.length ? (rslt / arguments.length) : 0;
}
console.log(avg(10,20,30))
console.log(avg(...t))
punto = { x: 10, y: 20}
function coordenadas(x, y) {
    return x + y;
}
coordenadas = (x, y) =>  x + y;
console.log(coordenadas(punto.x, punto.y))
console.log((coordenadas)(punto.x, punto.y))

console.log(coordenadas(...punto))

function suma(a, b) { return a + b; }
suma = (a, b) => a + b;

t.filter(item => item % 2)

t.filter(item => item > 10)
for(let i=0; i < 10; i++)
    t.filter(function(item) { return item > a; })

filtrado = function(item) { return item > 10; }

for(let i=0; i < 10; i++)
    t.filter(filtrado);


let tab = new Array();
tab = [10, 20, 30];
tab[5] = (a, b) => a + b;
tab[10] = 'manolo'
tab.push('benito')
tab.splice(2, 1)
console.log('Indices')
for (v in tab) {
    console.log(v)
}
console.log('Valores')
for (v of tab) {
    console.log(v)
}
console.log(tab[5](2,3))