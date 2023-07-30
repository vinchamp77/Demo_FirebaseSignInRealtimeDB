package vtsen.hashnode.dev.signindemoapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vtsen.hashnode.dev.signindemoapp.ui.screens.MainScreen
import vtsen.hashnode.dev.signindemoapp.ui.theme.NewEmptyComposeAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        setupSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {
            NewEmptyComposeAppTheme {
                MainScreen()
            }
        }

        //test()

        val database = Firebase.database
        val myRef = database.getReference("message")

        //myRef.setValue("Hello, World! xxx")

// Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>()
                Log.d("tag", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("tag", "Failed to read value.", error.toException())
            }
        })

//        myRef.get().addOnSuccessListener {
//            val value = it.getValue<String>()
//            Log.d("tag", "Value is: $value")
//        }

    }

//    private fun test() {
//        val database = Firebase.database
//        val ref = database.reference
//        var childRef = ref.child("users").child("3foc1sLjBTXcIIh2oTRfvdcpL5E3")
//
//        val data = User("Vincent23")
//        val task = childRef.setValue(data)
//
//        task.addOnSuccessListener {
//            Log.d("tag", "success")
//        }
//        task.addOnFailureListener{
//            Log.d("tag", "Fail")
//
//        }
//    }

    private fun setupSplashScreen() {
        var keepSplashScreenOn = true
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(2000)
                keepSplashScreenOn = false
            }
        }

        installSplashScreen().setKeepOnScreenCondition {
            keepSplashScreenOn
        }
    }
}
