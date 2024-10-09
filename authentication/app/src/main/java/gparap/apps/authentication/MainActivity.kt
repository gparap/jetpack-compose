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
package gparap.apps.authentication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gparap.apps.authentication.ui.theme.MyAuthenticationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAuthenticationAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.inverseOnSurface) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxSize()
                    ) {
                        LoginForm()
                    }
                }
            }
        }
    }
}

@Composable
fun LoginForm() {
    MyAuthenticationAppTheme {
        //screen background
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.inverseOnSurface)
        {
            //screen layout
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                //logo
                Image(
                    painter = painterResource(R.drawable.logo), contentDescription = "Logo",
                    modifier = Modifier
                        .width(196.dp)
                        .height(196.dp)
                )

                Spacer(modifier = Modifier.padding(16.dp))

                //main label
                Text("My Authentication App", fontWeight = FontWeight.Bold, fontSize = 22.sp)

                //sub label
                Text("Login with your credentials...", fontWeight = FontWeight.Normal, fontStyle = FontStyle.Italic)

                Spacer(modifier = Modifier.padding(16.dp))

                //username
                TextField(
                    value = "gparap",
                    onValueChange = { },
                    label = { Text("USERNAME") },
                    placeholder = { Text("Please, enter your username here...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                //password
                TextField(
                    visualTransformation = PasswordVisualTransformation(),
                    value = "123456",
                    onValueChange = { },
                    label = { Text("PASSWORD") },
                    placeholder = { Text("Please, enter your password here...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                //missed credentials
                Text("Forgot your password?", fontStyle = FontStyle.Italic)

                Spacer(modifier = Modifier.padding(16.dp))

                //login button
                Button(onClick = {}) {
                    Text("Login")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginFormPreview() {
    MyAuthenticationAppTheme {
        //screen background
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.inverseOnSurface)
        {
            //screen layout
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                //logo
                Image(
                    painter = painterResource(R.drawable.logo), contentDescription = "Logo",
                    modifier = Modifier
                        .width(196.dp)
                        .height(196.dp)
                )

                Spacer(modifier = Modifier.padding(16.dp))

                //main label
                Text("My Authentication App", fontWeight = FontWeight.Bold, fontSize = 22.sp)

                //sub label
                Text("Login with your credentials...", fontWeight = FontWeight.Normal, fontStyle = FontStyle.Italic)

                Spacer(modifier = Modifier.padding(16.dp))

                //username
                TextField(
                    value = "gparap",
                    onValueChange = { },
                    label = { Text("USERNAME") },
                    placeholder = { Text("Please, enter your username here...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                //password
                TextField(
                    visualTransformation = PasswordVisualTransformation(),
                    value = "123456",
                    onValueChange = { },
                    label = { Text("PASSWORD") },
                    placeholder = { Text("Please, enter your password here...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                //missed credentials
                Text("Forgot your password?", fontStyle = FontStyle.Italic)

                Spacer(modifier = Modifier.padding(16.dp))

                //login button
                Button(onClick = {}) {
                    Text("Login")
                }
            }
        }
    }
}