package dev.tsquaredapps.doit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import dev.tsquaredapps.doit.data.AppDatabase
import dev.tsquaredapps.doit.data.Task
import dev.tsquaredapps.doit.ui.theme.DoItTheme
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoItTheme {
                // A surface container using the 'background' color from the theme
                val coroutineScope = rememberCoroutineScope()
                val tasks by appDatabase.taskDao().getAllFLow()
                    .collectAsState(initial = emptyList())

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { paddingValues ->
                    LazyColumn(contentPadding = paddingValues) {
                        items(tasks) { task ->
                            Card {
                                Text(text = task.title)
                            }
                        }

                        item {
                            Column {
                                var text by remember {
                                    mutableStateOf("")
                                }
                                TextField(
                                    value = text,
                                    onValueChange = {
                                        text = it
                                    })
                                Button(onClick = {
                                    coroutineScope.launch {
                                        appDatabase.taskDao().upsert(Task(title = text))
                                    }
                                }) {
                                    Text(text = "ADD")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DoItTheme {
        Greeting("Android")
    }
}