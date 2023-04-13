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
