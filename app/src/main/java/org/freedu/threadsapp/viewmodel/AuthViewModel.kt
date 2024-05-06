package org.freedu.threadsapp.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import org.freedu.threadsapp.model.UserModel
import org.freedu.threadsapp.utils.SharedPref
import java.util.UUID

class AuthViewModel : ViewModel() {
    val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    val userRef = db.getReference("users")
    private val storageRef = Firebase.storage.reference
    private val imageRef = storageRef.child("users/${UUID.randomUUID()}.jpg")

    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val firebaseUser: MutableLiveData<FirebaseUser?> = _firebaseUser

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        _firebaseUser.value = auth.currentUser
    }

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _firebaseUser.postValue(auth.currentUser)
                } else {
                    _error.postValue("Something went wrong.")
                }
            }
    }

    fun register(
        email: String,
        password: String,
        name: String,
        bio: String,
        username: String,
        imageUri: Uri,
        context: Context
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _firebaseUser.postValue(auth.currentUser)
                    saveImage(
                        email,
                        password,
                        name,
                        bio,
                        username,
                        imageUri,
                        auth.currentUser!!.uid,
                        context
                    )
                } else {
                    _error.postValue("Something went wrong.")
                }
            }
    }

    private fun saveImage(
        email: String,
        password: String,
        name: String,
        bio: String,
        username: String,
        imageUri: Uri,
        uid: String,
        context: Context
    ) {
        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                saveData(email, password, name, bio, username, it.toString(), uid, context)
            }
        }
    }

    private fun saveData(
        email: String,
        password: String,
        name: String,
        bio: String,
        username: String,
        toString: String,
        uid: String,
        context:Context
    ) {
        val userData = UserModel(email, password, name, bio, username, toString, uid)
        userRef.child(uid).setValue(userData)
            .addOnSuccessListener {
                SharedPref.storeData(name, email, bio, username, toString, context)
            }
            .addOnFailureListener{

            }
    }

    fun logout() {
        auth.signOut()
        _firebaseUser.postValue(null)
    }
}