// Imprime por pantalla los indices y no los valores
t = [10,20,30]
for(v in t)
{
    console.log(v);
}

// Imprime por pantalla los valores y no los indices
t = [10,20,30]
for(v of t)
{
    console.log(v);
}

function suma(a,b) {return a+b}

filtrado = function(item){
    return item > a;
}

for(let i=0; i<10; i++)
{
    t.filter(filtrado);
}