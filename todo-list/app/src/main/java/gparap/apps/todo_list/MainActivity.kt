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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import gparap.apps.todo_list.ui.theme.MyTODOListAppTheme
import gparap.apps.todo_list.viewmodels.MainActivityViewModel

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainActivityViewModel
    private val todoList = mutableListOf(
        "todo #1", "todo #2", "todo #3", "todo #4", "todo #5", "todo #6", "todo #7", "todo #8",
        "todo #9", "todo #10", "todo #11", "todo #12", "todo #13", "todo #14", "todo #15", "todo #16"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //get the view model for this activity
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.setToToList(todoList)

        enableEdgeToEdge()
        setContent {
            MyTODOListAppTheme {
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
                            ToDoList(viewModel)
                        }
                    }
                }
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
fun ToDoList(viewModel: MainActivityViewModel) {
    //observe the todoList items LiveData from the ViewModel
    val todoListItems by viewModel.getToDoList().observeAsState(emptyList())

    //display todoList items
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        todoListItems.forEach { todoListItem ->
            ToDoItem(todoListItem)
        }
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
                ToDoItem("todo #1")
                ToDoItem("todo #2")
                ToDoItem("todo #3")
                //..more ToDoItems
                ToDoItem("todo #n")
            }
        }
    }
}
