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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.firebase.ui.auth.AuthUI
import vtsen.hashnode.dev.signindemoapp.ui.theme.NewEmptyComposeAppTheme

@Composable
fun MainScreen() {

    val viewModel: MainViewModel = viewModel()

    var showSignIn by remember {mutableStateOf(false)}

    val signInStatus by viewModel.signInStatus.collectAsStateWithLifecycle()
    val signedInUser by viewModel.signedInUser.collectAsStateWithLifecycle()
    val userDataFromDB by viewModel.userDataFromDB.collectAsStateWithLifecycle()
    val userDataFlowFromDB by viewModel.userDataFlowFromDB.collectAsStateWithLifecycle()

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(Modifier.padding(2.dp))
        Text("Sign-in Status: $signInStatus")

        Spacer(Modifier.padding(2.dp))
        Text("User Id: ${signedInUser?.uid}")

        Spacer(Modifier.padding(2.dp))
        Button(onClick = {
            showSignIn = true
        }) {
            Text("Sign In")
        }

        Spacer(Modifier.padding(2.dp))
        Button(onClick = {
            AuthUI.getInstance().signOut(context)
            viewModel.onSignOut()
        }) {
            Text("Sign Out")
        }

        Spacer(Modifier.padding(2.dp))
        Button(onClick = {
            viewModel.writeToDatabase()
        }) {
            Text("Write Data")
        }

        Spacer(Modifier.padding(2.dp))
        Button(onClick = {
            viewModel.readFromDatabase()
        }) {
            Text("Read Data")
        }

        Spacer(Modifier.padding(2.dp))
        Text("User Data (One-time Read)")
        Text("----------------------------------------------")
        Text("Name: ${userDataFromDB?.name}")
        Text("Age: ${userDataFromDB?.age}")
        Text("Fruits: ${userDataFromDB?.favoriteFood}")

        Spacer(Modifier.padding(2.dp))
        Text("User Data (Continuous Read - Flow)")
        Text("----------------------------------------------")
        Text("Name: ${userDataFlowFromDB?.name}")
        Text("Age: ${userDataFlowFromDB?.age}")
        Text("Fruits: ${userDataFlowFromDB?.favoriteFood}")
    }

    if (showSignIn) {
        SignInScreen { result ->
            // (4) Handle the sign-in result callback
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                viewModel.onSignedIn()
            } else {
                val response = result.idpResponse
                if (response == null) {
                    viewModel.onSignInCancel()
                } else {
                    val errorCode = response.getError()?.getErrorCode()
                    viewModel.onSignInError(errorCode)
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