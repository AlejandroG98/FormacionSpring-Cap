let display = document.getElementById('result');
let operator = '';
let firstOperand = '';
let secondOperand = '';
let shouldResetDisplay = false;

function inputDigit(digit) {
  if (shouldResetDisplay) {
    display.textContent = '';
    shouldResetDisplay = false;
  }
  display.textContent = display.textContent === '0' ? digit : display.textContent + digit;
}

function inputDecimal() {
  if (shouldResetDisplay) {
    display.textContent = '';
    shouldResetDisplay = false;
  }
  if (!display.textContent.includes('.')) {
    display.textContent += '.';
  }
}



function clearDisplay() {
  display.textContent = '0';
  operator = '';
  firstOperand = '';
  secondOperand = '';
}

function backspace() {
  if (shouldResetDisplay) {
    clearDisplay();
  } else {
    display.textContent = display.textContent.slice(0, -1);
    if (display.textContent === '') {
      display.textContent = '0';
    }
  }
}

function setOperator(newOperator) {
  if (operator !== '') {
    calculateResult();
  }
  operator = newOperator;
  firstOperand = display.textContent;
  shouldResetDisplay = true;
}

function calculateResult() {
  if (operator === '') {
    return;
  }
  secondOperand = display.textContent;
  let result;
  switch (operator) {
    case '+':
      result = parseFloat(firstOperand) + parseFloat(secondOperand);
      break;
    case '-':
      result = parseFloat(firstOperand) - parseFloat(secondOperand);
      break;
    case '*':
      result = parseFloat(firstOperand) * parseFloat(secondOperand);
      break;
    case '/':
      if (secondOperand === '0') {
        display.textContent = 'Error';
        operator = '';
        firstOperand = '';
        secondOperand = '';
        return;
      }
      result = parseFloat(firstOperand) / parseFloat(secondOperand);
      break;
    default:
      return;
  }
  display.textContent = result;
  operator = '';
  firstOperand = '';
  secondOperand = '';
}

document.querySelectorAll('.number').forEach(button => {
  button.addEventListener('click', () => inputDigit(button.textContent));
});

document.querySelector('#decimal').addEventListener('click', inputDecimal);

document.querySelectorAll('.clear').forEach(button => {
  button.addEventListener('click', clearDisplay);
});

document.querySelector('#backspace').addEventListener('click', backspace);

document.querySelectorAll('.operator').forEach(button => {
  if (button.textContent === '×') {
    button.addEventListener('click', () => setOperator('*'));
  } else if (button.id === 'divide') {
    button.addEventListener('click', () => setOperator('/'));
  } else {
    button.addEventListener('click', () => setOperator(button.textContent));
  }
});


document.querySelector('#equal').addEventListener('click', calculateResult);