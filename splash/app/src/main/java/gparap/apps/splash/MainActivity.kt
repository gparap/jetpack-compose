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
package gparap.apps.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import gparap.apps.splash.ui.theme.MySplashScreenAppTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppContent()
        }
    }
}

@Composable
fun AppContent() {
    var isAnimationFinished by remember { mutableStateOf(false) }
    if (isAnimationFinished) {
        MainContent()
    } else {
        SplashContent(onAnimationComplete = { isAnimationFinished = true })
    }
}

@Composable
fun SplashContent(onAnimationComplete: () -> Unit) {
    //animation state var
    var animationState by remember { mutableStateOf(false) }

    //animation tween spec
    val animationSpec = tween<Float>(2000, 0, FastOutSlowInEasing)

    //using scaling for animation
    val animationScale = animateFloatAsState(
        targetValue = if (animationState) 1.5f else 0.5f,
        animationSpec = animationSpec,
        label = "scaling animation"
    )

    //launch the animation
    LaunchedEffect(Unit) {
        animationState = true
        delay(2000L)
        onAnimationComplete()   //callback to inform that the animation finished
    }

    //display the animated image
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Image(
            painterResource(R.drawable.logo), "Logo", Modifier.scale(animationScale.value)
        )
    }
}

@Composable
fun MainContent() {
    MySplashScreenAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Greeting(
                name = "Android",
                modifier = Modifier.padding(innerPadding)
            )
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
    MySplashScreenAppTheme {
        Greeting("Android")
    }
}