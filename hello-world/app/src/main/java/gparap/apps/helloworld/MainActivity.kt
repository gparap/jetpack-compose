package gparap.apps.helloworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gparap.apps.helloworld.ui.theme.HelloWorldTheme

class MainActivity : ComponentActivity() {
    //create a list with messages to display
    private val messages = mutableListOf<String>(
        "Hello World!", "from Jetpack Compose app..."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CenteredCard(R.drawable.logo, messages)
        }
    }
}

/** A custom card that consists of an image and a list of messages. */
@Composable
fun CenteredCard(drawableId: Int, messages: List<String>) {
    //center card in the center of the device
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        HelloWorldTheme {
            Surface(modifier = Modifier.fillMaxWidth()) {
                //display the messages alongside the image
                Row(
                    modifier = Modifier
                        .padding(Dp(8.0f))
                        .fillMaxWidth()
                ) {
                    //display the image
                    Image(
                        painter = painterResource(drawableId),
                        contentDescription = "Image",
                        modifier = Modifier.size(72.dp)
                    )

                    //use an empty space between the image and the messages
                    Spacer(modifier = Modifier.width(16.dp))

                    //display the messages one on top of the other
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Column {
                            messages.forEach { msg -> Text(msg) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewCenteredCard() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        HelloWorldTheme {
            Surface(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .padding(Dp(8.0f))
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = "Image",
                        modifier = Modifier.size(72.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Column {
                            Text("Hello World!")
                            Text("from Jetpack Compose app...")
                        }
                    }
                }
            }
        }
    }
}