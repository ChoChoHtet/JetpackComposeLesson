package com.example.jetpackcomposepratice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposepratice.ui.theme.JetpackComposePraticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposePraticeTheme() {
                Surface(modifier = Modifier.fillMaxSize()) {
                    //MessageCard(Message("Cho", "Lesson2"))
                    Conversation(messages = SampleData.conversationSample)
                }
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(message: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.profile_picture),
            contentDescription = "android icon ",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )
        Spacer(modifier = Modifier.size(10.dp))
        // We keep track if the message is expanded or not in this
        //Composable functions can store local state in memory by using remember, and track changes to the value passed to mutableStateOf
        var isExpanded by remember { mutableStateOf(false) }
        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(
            if (isExpanded) Color(0xFFBB86FC) else Color(0xFF000000)
        )
        Surface(
            shape = MaterialTheme.shapes.medium, elevation = 2.dp,
            modifier = Modifier
                .animateContentSize()
                .padding(1.dp)
        ) {
            Column(modifier = Modifier.clickable {
                isExpanded = !isExpanded
            }) {
                Text(
                    text = message.author, color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message.body, style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(all = 8.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    color = surfaceColor,
                )
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        messages.map {
            item { MessageCard(Message(it.author, it.body)) }

        }
    }
}

/*@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewMessageCard() {
    JetpackComposePraticeTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MessageCard(Message("Cho", "Lesson2"))
        }
    }
}*/

@Preview
@Composable
fun PreviewConversation() {
    JetpackComposePraticeTheme {
        Conversation(messages = SampleData.conversationSample)
    }
}

