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
package gparap.apps.todo_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gparap.apps.todo_list.ui.theme.MyTODOListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyTODOListAppTheme {
                AppPreview()
            }
        }
    }
}

@Composable
fun AppTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Gray)
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text("My TODO List", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ToDoList() {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        //create a test list of ToDoItems
        ToDoItem("todo #1")
        ToDoItem("todo #2")
        ToDoItem("todo #3")
        ToDoItem("todo #4")
        ToDoItem("todo #5")
        ToDoItem("todo #6")
        ToDoItem("todo #7")
        ToDoItem("todo #8")
        ToDoItem("todo #9")
        ToDoItem("todo #11")
        ToDoItem("todo #12")
        ToDoItem("todo #13")
        ToDoItem("todo #14")
        ToDoItem("todo #15")
        ToDoItem("todo #16")
    }
}

@Composable
fun ToDoItem(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(color = Color.LightGray),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground), "TODO image",
            modifier = Modifier.size(48.dp, 48.dp)
        )
        Text("TODO: $name")
    }
}

@Composable
@Preview
fun AppPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 0.dp, 0.dp, 0.dp)
    ) {
        //always stays at the top
        AppTitle()

        //scrollable ToDoList
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                ToDoList()
            }
        }
    }
}
