package vtsen.hashnode.dev.signindemoapp.ui.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import vtsen.hashnode.dev.signindemoapp.ui.theme.NewEmptyComposeAppTheme

@Composable
fun MainScreen() {

    var showSignIn by remember {mutableStateOf(false)}
    var signInStatus by remember{ mutableStateOf("Not Signed-in") }
    var userId by remember{ mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(Modifier.padding(2.dp))
        Text("Sign-in Status: $signInStatus")

        Spacer(Modifier.padding(2.dp))
        Text("User Id: $userId")

        Spacer(Modifier.padding(2.dp))
        Button(onClick = {
            showSignIn = true
        }) {
            Text("Sign In")
        }

        Spacer(Modifier.padding(2.dp))
        Button(onClick = {
            AuthUI.getInstance().signOut(context)
            signInStatus = "Not Signed-in"
            userId = ""
        }) {
            Text("Sign Out")
        }
    }

    if (showSignIn) {
        SignInScreen { result ->
            // (4) Handle the sign-in result callback
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                signInStatus = "Signed In"
                val user = FirebaseAuth.getInstance().currentUser
                userId = user!!.uid

            } else {
                val response = result.idpResponse
                signInStatus = if (response == null) {
                    "Not Signed In"
                } else {
                    val errorCode = response.getError()?.getErrorCode()
                    "Failed - Error Code: $errorCode"
                }
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