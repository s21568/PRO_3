package pl.edu.pja.pro_3.activities

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import pl.edu.pja.pro_3.database.FeedDb
import pl.edu.pja.pro_3.database.UserDto
import pl.edu.pja.pro_3.databinding.ActivityRegisterNewUserBinding
import java.util.concurrent.Executors

private val executor by lazy { Executors.newSingleThreadExecutor() }

class RegisterNewUserActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private val view by lazy { ActivityRegisterNewUserBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        setContentView(view.root)
        view.saveButton.setOnClickListener { createAccount() }
        view.cancelButton.setOnClickListener { finish() }
    }

    private fun createAccount() {
        mAuth.createUserWithEmailAndPassword(
            view.emailInputField.text.toString(),
            view.passwordRegisterInputField.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    mAuth.currentUser?.reload()
                    executor.submit {
                        val db = FeedDb.open(this)
                        db.user.insert(UserDto(0, view.emailInputField.text.toString(), ""))
                        db.close()
                    }
                    finish()
                } else {
                    Log.d(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}