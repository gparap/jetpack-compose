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
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import gparap.apps.todo_list.data.ToDoModel
import gparap.apps.todo_list.ui.theme.MyTODOListAppTheme
import gparap.apps.todo_list.viewmodels.MainActivityViewModel

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainActivityViewModel
    private var todoList = mutableListOf(   //TODO: remove test list after adding todos functionality
        ToDoModel("todo #1", 0),
        ToDoModel("todo #2", 1),
        ToDoModel("todo #3", 2),
        ToDoModel("todo #4", 3),
        ToDoModel("todo #5", 4),
        ToDoModel("todo #6", 5),
        ToDoModel("todo #7", 6),
        ToDoModel("todo #8", 7),
        ToDoModel("todo #9", 8),
        ToDoModel("todo #10", 9),
        ToDoModel("todo #11", 10),
        ToDoModel("todo #12", 11),
        ToDoModel("todo #13", 12),
        ToDoModel("todo #14", 13),
        ToDoModel("todo #15", 14),
        ToDoModel("todo #16", 15)
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
            ToDoItem(todoListItem, viewModel)
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
                Text("todo #1")
                Text("todo #2")
                //..more ToDoItems
                Text("todo #3")
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
