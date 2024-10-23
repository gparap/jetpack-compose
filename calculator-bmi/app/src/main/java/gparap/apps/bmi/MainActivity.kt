/*
 * Copyright 2024 gparap
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gparap.apps.bmi

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gparap.apps.bmi.ui.theme.MyBMIApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyBMIApplicationTheme {
                Calculator()
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun Calculator() {
    //state vars
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var bmi by remember { mutableFloatStateOf(0f) }
    var category by remember { mutableStateOf("") }
    val context = LocalContext.current

    MyBMIApplicationTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            //app label
            Spacer(modifier = Modifier.absolutePadding(0.dp, 64.dp, 0.dp, 0.dp))
            Row {
                Text(
                    "Body Mass Index Calculator", fontWeight = FontWeight.Bold, fontSize = 36.sp,
                    textAlign = TextAlign.Center, lineHeight = 48.sp
                )
            }

            //height input with label
            Spacer(modifier = Modifier.padding(0.dp, 32.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Enter height:", fontWeight = FontWeight.Normal, fontSize = 24.sp,
                    modifier = Modifier.absolutePadding(16.dp, 0.dp, 8.dp, 0.dp)
                )
                TextField(
                    modifier = Modifier.absolutePadding(0.dp, 0.dp, 16.dp, 0.dp),
                    singleLine = true,
                    value = height,
                    label = { Text("meters", fontStyle = FontStyle.Italic) },
                    onValueChange = { height = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            //weight input with label
            Spacer(modifier = Modifier.padding(0.dp, 16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Enter weight:", fontWeight = FontWeight.Normal, fontSize = 24.sp,
                    modifier = Modifier.absolutePadding(16.dp, 0.dp, 8.dp, 0.dp)
                )
                TextField(
                    modifier = Modifier.absolutePadding(0.dp, 0.dp, 16.dp, 0.dp),
                    singleLine = true,
                    value = weight,
                    label = { Text("kilos", fontStyle = FontStyle.Italic) },
                    onValueChange = { weight = it},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            //bmi result
            Spacer(modifier = Modifier.padding(0.dp, 16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "My BMI:", fontWeight = FontWeight.Normal, fontSize = 24.sp
                )
                Text(
                    String.format("%.2f", bmi), fontWeight = FontWeight.Normal, fontSize = 24.sp,
                    modifier = Modifier.absolutePadding(8.dp, 0.dp, 0.dp, 0.dp)
                )
            }

            //category result
            Spacer(modifier = Modifier.padding(0.dp, 16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Category:", fontWeight = FontWeight.Normal, fontSize = 24.sp
                )
                Text(
                    category, fontWeight = FontWeight.Normal, fontSize = 24.sp,
                    modifier = Modifier.absolutePadding(8.dp, 0.dp, 0.dp, 0.dp)
                )
            }

            //calculate bmi button
            Spacer(modifier = Modifier.padding(0.dp, 16.dp))
            Button(onClick = {
                //validate only not empty
                if (height.isEmpty() || weight.isEmpty()) {
                    Toast.makeText(context, "Please, enter values...", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                //calculate bmi
                bmi = weight.toFloat() / (height.toFloat() * height.toFloat())

                //find bmi category
                category = if (bmi < 18.5) {
                    "Underweight"
                } else if (bmi > 18.5 && bmi < 25) {
                    "Normal"
                } else if (bmi > 25 && bmi < 30) {
                    "Overweight"
                } else {
                    "Obese"
                }
            }) {
                Text("find my bmi".uppercase())
            }

            //clear all input button
            Spacer(modifier = Modifier.padding(0.dp, 0.dp))
            Button(onClick = { height = ""; weight = ""; bmi = 0.0f; category = ""},
                modifier = Modifier.absolutePadding(0.dp, 0.dp, 0.dp, 16.dp)) {
                Text("clear".uppercase())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    MyBMIApplicationTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            //app label
            Spacer(modifier = Modifier.absolutePadding(0.dp, 64.dp, 0.dp, 0.dp))
            Row {
                Text(
                    "Body Mass Index Calculator", fontWeight = FontWeight.Bold, fontSize = 36.sp,
                    textAlign = TextAlign.Center, lineHeight = 48.sp
                )
            }

            //height input with label
            Spacer(modifier = Modifier.padding(0.dp, 32.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Enter height:", fontWeight = FontWeight.Normal, fontSize = 24.sp,
                    modifier = Modifier.absolutePadding(16.dp, 0.dp, 8.dp, 0.dp)
                )
                TextField(
                    modifier = Modifier.absolutePadding(0.dp, 0.dp, 16.dp, 0.dp),
                    singleLine = true,
                    value = "",
                    label = { Text("meters", fontStyle = FontStyle.Italic) },
                    onValueChange = { },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            //weight input with label
            Spacer(modifier = Modifier.padding(0.dp, 16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Enter height:", fontWeight = FontWeight.Normal, fontSize = 24.sp,
                    modifier = Modifier.absolutePadding(16.dp, 0.dp, 8.dp, 0.dp)
                )
                TextField(
                    modifier = Modifier.absolutePadding(0.dp, 0.dp, 16.dp, 0.dp),
                    singleLine = true,
                    value = "",
                    label = { Text("kilos", fontStyle = FontStyle.Italic) },
                    onValueChange = { },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            //bmi result
            Spacer(modifier = Modifier.padding(0.dp, 16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "My BMI:", fontWeight = FontWeight.Normal, fontSize = 24.sp
                )
                Text(
                    "21.75", fontWeight = FontWeight.Normal, fontSize = 24.sp,
                    modifier = Modifier.absolutePadding(8.dp, 0.dp, 0.dp, 0.dp)
                )
            }

            //category result
            Spacer(modifier = Modifier.padding(0.dp, 16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Category:", fontWeight = FontWeight.Normal, fontSize = 24.sp
                )
                Text(
                    "Normal", fontWeight = FontWeight.Normal, fontSize = 24.sp,
                    modifier = Modifier.absolutePadding(8.dp, 0.dp, 0.dp, 0.dp)
                )
            }

            //calculate bmi button
            Spacer(modifier = Modifier.padding(0.dp, 16.dp))
            Button(onClick = {

            }) {
                Text("find my bmi".uppercase())
            }

            //clear all input button
            Spacer(modifier = Modifier.padding(0.dp, 0.dp))
            Button(onClick = { }, modifier = Modifier.absolutePadding(0.dp, 0.dp, 0.dp, 16.dp)) {
                Text("clear".uppercase())
            }
        }
    }
}