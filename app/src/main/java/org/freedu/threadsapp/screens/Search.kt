package org.freedu.threadsapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import org.freedu.threadsapp.item_view.ThreadItem
import org.freedu.threadsapp.item_view.UserItem
import org.freedu.threadsapp.viewmodel.HomeViewModel
import org.freedu.threadsapp.viewmodel.SearchViewModel

@Composable
fun Search(navHostController: NavHostController) {
    val searchViewModel: SearchViewModel = viewModel()
    val userList by searchViewModel.usersList.observeAsState(null)
    var search by remember {
        mutableStateOf("")
    }
    Column {
        Text(text = "Search", style = TextStyle(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp
        ), modifier = Modifier.padding(top = 16.dp, start = 16.dp))
        OutlinedTextField(
            value = search, onValueChange = {
                search = it
            },
            label = {
                Text(text = "Search User")
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ), singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search , contentDescription = "Search Icon")
            }
        )
        Box(modifier = Modifier.height(20.dp))
        LazyColumn {
            if(userList!=null && userList!!.isNotEmpty()) {
                val filterItems = userList?.filter {
                    it.name.contains(search, ignoreCase = true)
                }
                items(filterItems ?: emptyList()) { pairs ->
                    UserItem(
                        users = pairs,
                        navHostController = navHostController,

                        )
                }
            }

        }
    }

}