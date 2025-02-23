package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Calculator : AppCompatActivity() {
    private lateinit var tvDisplay: TextView
    private var operand1: Double? = null
    private var operator: String? = null
    private var resetDisplay: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Asegúrate de que el layout se llame activity_main.xml
        setContentView(R.layout.activity_calculator)

        // Referencias a la vista
        tvDisplay = findViewById(R.id.tvDisplay)
        val btnClear = findViewById<Button>(R.id.btnClear)
        val btnDivide = findViewById<Button>(R.id.btnDivide)
        val btnMultiply = findViewById<Button>(R.id.btnMultiply)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        val btn7 = findViewById<Button>(R.id.btn7)
        val btn8 = findViewById<Button>(R.id.btn8)
        val btn9 = findViewById<Button>(R.id.btn9)
        val btnSubtract = findViewById<Button>(R.id.btnSubtract)
        val btn4 = findViewById<Button>(R.id.btn4)
        val btn5 = findViewById<Button>(R.id.btn5)
        val btn6 = findViewById<Button>(R.id.btn6)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btn1 = findViewById<Button>(R.id.btn1)
        val btn2 = findViewById<Button>(R.id.btn2)
        val btn3 = findViewById<Button>(R.id.btn3)
        val btnEquals = findViewById<Button>(R.id.btnEquals)
        val btn0 = findViewById<Button>(R.id.btn0)
        val btnDot = findViewById<Button>(R.id.btnDot)
        val btnReturn = findViewById<Button>(R.id.btn_return)

        // Configuramos los listeners para los botones numéricos y el punto
        val numberButtons = listOf(btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnDot)
        for (button in numberButtons) {
            button.setOnClickListener {
                if (resetDisplay) {
                    tvDisplay.text = ""
                    resetDisplay = false
                }
                // Evita que se agreguen múltiples puntos
                if ((it as Button).text == "." && tvDisplay.text.contains(".")) {
                    return@setOnClickListener
                }
                // Si la pantalla muestra "0" se reemplaza
                if (tvDisplay.text == "0") {
                    tvDisplay.text = it.text
                } else {
                    tvDisplay.append(it.text)
                }
            }
        }

        // Listeners para los operadores
        btnAdd.setOnClickListener { operatorClicked("+") }
        btnSubtract.setOnClickListener { operatorClicked("-") }
        btnMultiply.setOnClickListener { operatorClicked("*") }
        btnDivide.setOnClickListener { operatorClicked("/") }

        // Listener para el botón igual
        btnEquals.setOnClickListener { calculateResult() }

        // Listener para limpiar la pantalla
        btnClear.setOnClickListener {
            tvDisplay.text = "0"
            operand1 = null
            operator = null
        }

        // Listener para borrar el último dígito
        btnDelete.setOnClickListener {
            val currentText = tvDisplay.text.toString()
            if (currentText.isNotEmpty() && currentText != "0") {
                tvDisplay.text = currentText.dropLast(1)
                if (tvDisplay.text.isEmpty()) {
                    tvDisplay.text = "0"
                }
            }
        }

        btnReturn.setOnClickListener {
            finish()
        }
    }

    /**
     * Se invoca cuando se presiona un operador.
     * Se guarda el operando actual y el operador seleccionado.
     */
    private fun operatorClicked(op: String) {
        // Si ya hay un operando y operador, se calcula el resultado previo
        if (operand1 != null && operator != null && !resetDisplay) {
            calculateResult()
        }
        operand1 = tvDisplay.text.toString().toDoubleOrNull()
        operator = op
        resetDisplay = true
    }

    /**
     * Calcula el resultado de la operación actual y lo muestra en la pantalla.
     */
    private fun calculateResult() {
        val operand2 = tvDisplay.text.toString().toDoubleOrNull()
        if (operand1 == null || operator == null || operand2 == null) return

        val result = when (operator) {
            "+" -> operand1!! + operand2
            "-" -> operand1!! - operand2
            "*" -> operand1!! * operand2
            "/" -> {
                if (operand2 != 0.0) {
                    operand1!! / operand2
                } else {
                    Toast.makeText(this, "Error: Dividir por cero", Toast.LENGTH_SHORT).show()
                    return
                }
            }
            else -> return
        }
        // Muestra el resultado; si es entero, se quita el .0
        tvDisplay.text = if (result % 1 == 0.0) result.toInt().toString() else result.toString()
        // Se prepara para operaciones encadenadas
        operand1 = result
        operator = null
        resetDisplay = true
    }
}