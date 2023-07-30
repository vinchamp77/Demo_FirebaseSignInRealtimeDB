package vtsen.hashnode.dev.signindemoapp.ui.screens

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import vtsen.hashnode.dev.signindemoapp.ui.data.UserData
import vtsen.hashnode.dev.signindemoapp.ui.data.createUserData

class MainViewModel: ViewModel() {
    private val _signInStatus = MutableStateFlow("Not Signed-in")
    val signInStatus = _signInStatus.asStateFlow()

    private val _signedInUser = MutableStateFlow<FirebaseUser?>(null)
    val signedInUser = _signedInUser.asStateFlow()

    private val _userDataFromDB = MutableStateFlow<UserData?>(null)
    val userDataFromDB = _userDataFromDB.asStateFlow()

    private val _userDataFlowFromDB = MutableStateFlow<UserData?>(null)
    val userDataFlowFromDB = _userDataFlowFromDB.asStateFlow()

    fun onSignOut() {
        _signInStatus.value = "Not Signed-in"
        _signedInUser.value = null

        _userDataFromDB.value = null
        _userDataFlowFromDB.value = null
    }

    fun onSignedIn() {
        _signInStatus.value = "Signed In"
        _signedInUser.value = FirebaseAuth.getInstance().currentUser

        val user = _signedInUser.value
        val userIdReference = Firebase.database.reference
            .child("users").child(user!!.uid)

        userIdReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userData = dataSnapshot.getValue<UserData?>()
                _userDataFlowFromDB.value = userData
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun onSignInCancel() {
        onSignOut()
    }

    fun onSignInError(errorCode: Int?) {
        _signInStatus.value = "Failed - Error Code: $errorCode"
        _signedInUser.value = null
    }

    fun writeToDatabase() {
        val user = _signedInUser.value
        user?.run {
            val userIdReference = Firebase.database.reference
                .child("users").child(uid)
            val userData = createUserData(displayName!!)
            userIdReference.setValue(userData)
        }
    }

    fun readFromDatabase() {
        val user = _signedInUser.value
        user?.run {
            val userIdReference = Firebase.database.reference
                .child("users").child(uid)

            userIdReference.get().addOnSuccessListener { dataSnapShot ->
                val userData = dataSnapShot.getValue<UserData?>()
                _userDataFromDB.value = userData
            }
        }
    }

}