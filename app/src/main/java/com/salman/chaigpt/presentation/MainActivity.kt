package com.salman.chaigpt.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.salman.chaigpt.common.onAssistantMessage
import com.salman.chaigpt.common.onUserMessage
import com.salman.chaigpt.domain.model.chat.Message
import com.salman.chaigpt.domain.repository.ChatRepository
import com.salman.chaigpt.presentation.theme.ChaiGPTTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val repository: ChatRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChaiGPTTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val messages by repository.messages.collectAsState(
                        initial = emptyList(),
                        lifecycleScope.coroutineContext
                    )
                    MessagesList(
                        messages = messages,
                        scope = lifecycleScope,
                    ) {
                        lifecycleScope.launch {
                            repository.sendMessage(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MessagesList(
    modifier: Modifier = Modifier,
    messages: List<Message>,
    scope: CoroutineScope,
    onMessageSubmit: (String) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        val state = rememberLazyListState()
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .align(Alignment.TopCenter),
            state = state
        ) {
            items(messages) {
                it.onAssistantMessage { msg ->
                    AssistantMessage(message = msg)

                }
                it.onUserMessage { msg ->
                    UserMessage(message = msg)
                }
            }
        }
        MessageBox(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp),
            onMessageSubmit = onMessageSubmit
        )
    }
}

@Composable
fun UserMessage(
    modifier: Modifier = Modifier,
    message: String,
) {
    val color = MaterialTheme.colorScheme.primary
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth(0.7f)
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color = color)
                .padding(8.dp)
        ) {
            Text(text = message, color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Composable
fun AssistantMessage(message: String) {
    val color = MaterialTheme.colorScheme.secondary
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color = color)
                .padding(8.dp)
        ) {
            // I don't want to show the string all at once, show it letter by letter
            val (text, setText) = remember { mutableStateOf("") }
            LaunchedEffect(message) {
                message.forEachIndexed { index, _ ->
                    setText(message.substring(0, index + 1))
                    delay(50)
                }
            }
            Text(text = text, color = MaterialTheme.colorScheme.onSecondary)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MessageBox(
    modifier: Modifier = Modifier,
    onMessageSubmit: (String) -> Unit,
) {
    val imm = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    var inputFieldState by remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = inputFieldState,
        onValueChange = {
            inputFieldState = it
        },
        trailingIcon = {
            IconButton(onClick = {
                onMessageSubmit(inputFieldState)
                inputFieldState = ""
                imm?.hide()
                focusManager.clearFocus()
            }) {
                Text(text = "Send")
            }
        }
    )
}