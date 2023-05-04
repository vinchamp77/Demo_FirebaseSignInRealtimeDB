package vtsen.hashnode.dev.signindemoapp.ui.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import vtsen.hashnode.dev.signindemoapp.ui.theme.NewEmptyComposeAppTheme

@Composable
fun MainScreen() {

    var showSignIn by remember {mutableStateOf(false)}
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(onClick = {
            showSignIn = true
        }) {
            Text("Sign In")
        }
    }

    if (showSignIn) {
        SignInScreen { result ->
            val response = result.idpResponse
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
            showSignIn = false
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewEmptyComposeAppTheme(useSystemUIController = false) {
        MainScreen()
    }
}