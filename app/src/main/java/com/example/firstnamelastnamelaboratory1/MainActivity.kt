package com.example.firstnamelastnamelaboratory1

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    private val activity: Activity = this

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen() {
        val heightInput: MutableState<String> = remember { mutableStateOf("") }
        val weightInput: MutableState<String> = remember { mutableStateOf("") }
        val bmi: MutableState<String> = remember { mutableStateOf("") }
        val bmiCategory: MutableState<String> = remember { mutableStateOf("") }
        val bmiColor: MutableState<Color> = remember { mutableStateOf(Color.Black) }
        val errorMessage: MutableState<String> = remember { mutableStateOf("") }
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "BMI CALCULATOR",
                fontSize = 30.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            TextField(
                value = heightInput.value,
                onValueChange = {
                    heightInput.value = it
                },
                label = { Text(text = "Enter your height (cm)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Gray,
                    focusedPlaceholderColor = Color.Black,
                ),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 20.dp)
            )

            TextField(
                value = weightInput.value,
                onValueChange = {
                    weightInput.value = it
                },
                label = { Text(text = "Enter your weight (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Gray,
                ),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 20.dp)
            )

            Button(
                onClick = {
                    val height = heightInput.value.toDoubleOrNull()
                    val weight = weightInput.value.toDoubleOrNull()

                    hideKeyboard(activity = activity)

                    if (height == null || weight == null || height <= 0 || weight <= 0) {
                        val error = "Please enter valid positive numbers for height and weight."
                        errorMessage.value = error
                        showErrorToast(context, error)
                        bmi.value = ""
                        bmiCategory.value = ""
                        bmiColor.value = Color.Black
                    } else {
                        val heightInMeters = height / 100
                        val bmiValue = weight / (heightInMeters * heightInMeters)
                        bmi.value = "BMI: %.2f".format(bmiValue)
                        errorMessage.value = ""

                        when {
                            bmiValue < 18.5 -> {
                                bmiCategory.value = "Underweight"
                                bmiColor.value = Color.Red
                            }

                            bmiValue in 18.5..24.99 -> {
                                bmiCategory.value = "Healthy"
                                bmiColor.value = Color.Green
                            }

                            bmiValue in 25.0..29.99 -> {
                                bmiCategory.value = "Overweight"
                                bmiColor.value = Color.Red
                            }

                            bmiValue >= 30 -> {
                                bmiCategory.value = "Obesity"
                                bmiColor.value = Color.Black
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 20.dp)
            ) {
                Text(text = "Calculate my BMI", fontSize = 20.sp)
            }

            if (bmi.value.isNotEmpty()) {
                Text(
                    text = bmi.value,
                    fontSize = 30.sp,
                    color = bmiColor.value,
                    modifier = Modifier.padding(top = 20.dp)
                )
                Text(
                    text = bmiCategory.value,
                    fontSize = 20.sp,
                    color = bmiColor.value,
                    modifier = Modifier.padding(top = 10.dp)
                )

                when (bmiCategory.value) {
                    "Underweight" -> Image(
                        painter = painterResource(id = R.drawable.underweight),
                        contentDescription = "Underweight Image",
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth()
                            .height(300.dp)
                    )

                    "Healthy" -> Image(
                        painter = painterResource(id = R.drawable.normal),
                        contentDescription = "Normal Image",
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth()
                            .height(300.dp)
                    )

                    "Overweight" -> Image(
                        painter = painterResource(id = R.drawable.overweight),
                        contentDescription = "Overweight Image",
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth()
                            .height(300.dp)
                    )

                    "Obesity" -> Image(
                        painter = painterResource(id = R.drawable.obesity),
                        contentDescription = "Obesity Image",
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                }

            }

            if (errorMessage.value.isNotEmpty()) {
                Text(
                    text = errorMessage.value,
                    fontSize = 16.sp,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
    }

    private fun showErrorToast(context: android.content.Context, message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)

        val toastView = toast.view
        val textView = toastView?.findViewById<TextView>(android.R.id.message)
        textView?.textSize = 18f
        textView?.setTextColor(android.graphics.Color.WHITE)

        toastView?.setBackgroundResource(android.R.color.holo_red_light)
        toast.setGravity(android.view.Gravity.TOP or android.view.Gravity.CENTER_HORIZONTAL, 0, 100)
        toast.show()
    }

    private fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
