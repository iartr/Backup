package com.artrblog.backup

import android.app.backup.BackupManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.artrblog.backup.ui.theme.BackupTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BackupTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        Text(text = "Autobackup")

                        val scope = rememberCoroutineScope()
                        val context = LocalContext.current
                        val repository = remember { AuthRepository(context.applicationContext) }
                        var fileData by remember { mutableStateOf("Загрузка...") }
                        var prefsData by remember { mutableStateOf("Загрузка...") }

                        LaunchedEffect(Unit) {
                            scope.launch(Dispatchers.IO) {
                                fileData = repository.readFile()
                                prefsData = repository.readPrefs()
                            }
                        }

                        Text(
                            text = "Значение из файла для бэкапа: $fileData",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Text(
                            text = "Значение из префов для бэкапа: $prefsData",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 16.dp)
                        )

                        Button(
                            onClick = {
                                scope.launch(Dispatchers.IO) {
                                    repository.updateFileAndPrefs()
                                    fileData = repository.readFile()
                                    prefsData = repository.readPrefs()

                                    BackupManager(context).dataChanged()
                                }
                            },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text(text = "Поменять данные в файле для бэкапа")
                        }
                    }
                }
            }
        }
    }
}
