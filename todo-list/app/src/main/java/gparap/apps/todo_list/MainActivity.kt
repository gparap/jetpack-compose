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
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import gparap.apps.todo_list.data.ToDoModel
import gparap.apps.todo_list.ui.theme.MyTODOListAppTheme
import gparap.apps.todo_list.viewmodels.MainActivityViewModel

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //get the view model for this activity
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.setToToList(viewModel.getToDoList())

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
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .fillMaxSize()
                        ) {
                            AppNav(viewModel)
                        }
                    }
                }
            }
        }
    }
}

/**
 * Main application navigation between ToDoItems list screen & Add new ToDoItem screen.
 */
@Composable
fun AppNav(viewModel: MainActivityViewModel) {
    //create a NavController
    val navController = rememberNavController()

    //define the navigation graph
    NavHost(navController, startDestination = "navRoute_ToDoListScreen") {
        //1st screen: ToDoList
        composable("navRoute_ToDoListScreen") { ToDoList(viewModel, navController) }
        //2nd screen: AddToDo
        composable("navRoute_AddToDoItemScreen") { AddToDoItem(viewModel, navController) }
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
fun ToDoList(viewModel: MainActivityViewModel, navController: NavController) {
    //observe the todoList items LiveData from the ViewModel
    val todoListItems by viewModel.getToDoList().observeAsState(emptyList())

    //display todoList items & the button to add new
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        //display todoList items
        todoListItems.forEach { todoListItem ->
            ToDoItem(todoListItem, viewModel)
        }

        //add a new ToDoItem button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    //navigate to AddToDo item screen
                    navController.navigate("navRoute_AddToDoItemScreen")
                },
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp)
            ) {
                Text("Add TODO")
            }
        }
    }
}

@Composable
fun AddToDoItem(viewModel: MainActivityViewModel, navController: NavController) {
    var todoName by remember { mutableStateOf("") }

    //add todoItem screen
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        //input field for the todoItem name
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp), horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = todoName,
                singleLine = false,
                minLines = 2,
                maxLines = 4,
                onValueChange = { todoName = it },
                label = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text("Enter your TODO here", modifier = Modifier.align(Alignment.Center))
                    }
                },
                textStyle = androidx.compose.ui.text.TextStyle(textAlign = TextAlign.Center),
                modifier = Modifier.fillMaxWidth()
            )
        };Spacer(modifier = Modifier.absolutePadding(0.dp, 8.dp, 0.dp, 0.dp))

        //button to add the todoItem
        Button(
            onClick = {
                //validate input & inform user
                if (todoName.isEmpty()) {
                    Toast.makeText(navController.context, "Your TODO is empty!", Toast.LENGTH_SHORT).show()
                    return@Button
                } else {
                    Toast.makeText(navController.context, "Your TODO was added!", Toast.LENGTH_SHORT).show()
                }

                //get the size of the todoList to find the position
                val size = viewModel.getToDoList().value?.size
                val todoPosition = size ?: 0

                //add todoItem
                viewModel.addToDoItem(ToDoModel(todoName, todoPosition))

                //navigate back to the ToDoList
                navController.navigate("navRoute_ToDoListScreen")
            },
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
        ) {
            Text("Add TODO")
        }
    }
}

@Composable
fun ToDoItem(toDoItem: ToDoModel, viewModel: MainActivityViewModel) {
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
        Text("TODO: ${toDoItem.name}")
        Spacer(modifier = Modifier.padding(64.dp, 0.dp, 0.dp, 0.dp))
        Image(painter = painterResource(R.drawable.ic_delete_24px), "Delete TODO item",
            modifier = Modifier
                .size(24.dp, 24.dp)
                .clickable {
                    viewModel.deleteToDoItem(toDoItem.position)
                }
        )
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
                ToDoItemPreview()
                ToDoItemPreview()
                //...ToDoItems...
                ToDoItemPreview()

                //add a new ToDoItem button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {},
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp)
                    ) {
                        Text("Add TODO")
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun ToDoItemPreview() {
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
        Text("TODO: todo #1")
        Spacer(modifier = Modifier.padding(64.dp, 0.dp, 0.dp, 0.dp))
        Image(
            painter = painterResource(R.drawable.ic_delete_24px), "Delete TODO item",
            modifier = Modifier.size(24.dp, 24.dp)
        )
    }
}

@Composable
@Preview
fun AddToDoPreview() {
    var todoName by remember { mutableStateOf("") }

    //always stays at the top
    AppTitle()

    //add todoItem screen
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        //input field for the todoItem name
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp), horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = todoName,
                singleLine = false,
                minLines = 2,
                maxLines = 4,
                onValueChange = { todoName = it },
                label = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text("Enter your TODO here", modifier = Modifier.align(Alignment.Center))
                    }
                },
                textStyle = androidx.compose.ui.text.TextStyle(textAlign = TextAlign.Center),
                modifier = Modifier.fillMaxWidth()
            )
        };Spacer(modifier = Modifier.absolutePadding(0.dp, 8.dp, 0.dp, 0.dp))

        //button to add the todoItem
        Button(
            onClick = {},
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
        ) {
            Text("Add TODO")
        }
    }
}
