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
package gparap.apps.todo_list.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gparap.apps.todo_list.data.ToDoModel

class MainActivityViewModel : ViewModel() {
    private val todoList = MutableLiveData<List<ToDoModel>>(emptyList())
    fun getToDoList(): MutableLiveData<List<ToDoModel>> {
        return todoList
    }

    fun setToToList(todoList: List<ToDoModel>) {
        this.todoList.value = todoList
    }

    fun deleteToDoItem(position: Int) {
        val currentList = todoList.value ?: return
        val updatedList = currentList.toMutableList().apply {
            removeAt(position)
        }
        //update the positions of the items after the removed one
        updatedList.forEach { item ->
            run {
                if (item.position > position) {
                    item.position -= 1
                }
            }
        }
        //update the MutableLiveData
        todoList.value = updatedList
    }
}