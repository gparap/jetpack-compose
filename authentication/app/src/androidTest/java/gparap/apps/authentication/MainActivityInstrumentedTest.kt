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
package gparap.apps.authentication

import android.content.Context
import android.view.View
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {
    private lateinit var activityScenario: ActivityScenario<MainActivity>
    private lateinit var rootView: View
    private lateinit var context: Context

    companion object {
        const val USERNAME = "gparap"
        const val PASSWORD = "123456"
        const val USERNAME_TAG = "usernameTextField"
        const val PASSWORD_TAG = "passwordTextField"
        const val LOGIN_BUTTON_TAG = "loginButton"
    }

    //create an implementation of ComposeContentTestRule
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        //launch activity
        activityScenario = ActivityScenario.launch(MainActivity::class.java)

        //get root view
        activityScenario.onActivity { it -> rootView = it.window.decorView }

        //get target context
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun isLoginSuccessful_displayToast() {
        //clear fields first
        clearInput()

        //enter the correct credentials and press the login button
        fillInput(USERNAME, PASSWORD)
        closeSoftKeyboard()
        composeTestRule.onNodeWithTag("loginButton").performScrollTo().performClick()

        //test & wait for previous toasts to disappear
        checkToastMsg(context.getString(R.string.toast_authentication_succeed))
        Thread.sleep(2000L)
    }

    @Test
    fun isLoginFailed_displayToast() {
        //clear fields first
        clearInput()

        //enter the wrong credentials and press the login button
        fillInput("USERNAME", "PASSWORD")
        closeSoftKeyboard()
        composeTestRule.onNodeWithTag(LOGIN_BUTTON_TAG).performScrollTo().performClick()

        //test & wait for previous toasts to disappear
        checkToastMsg(context.getString(R.string.toast_authentication_failed))
        Thread.sleep(2000L)
    }

    private fun clearInput() {
        composeTestRule.onNodeWithTag(USERNAME_TAG).performTextClearance()
        composeTestRule.onNodeWithTag(PASSWORD_TAG).performTextClearance()
    }

    private fun fillInput(username: String, password: String) {
        composeTestRule.onNodeWithTag(USERNAME_TAG).performTextInput(username)
        composeTestRule.onNodeWithTag(PASSWORD_TAG).performTextInput(password)
    }

    private fun checkToastMsg(msg: String) {
        onView(withText(msg))
            .inRoot(withDecorView(not(rootView)))
            .check(matches(isDisplayed()))
    }
}