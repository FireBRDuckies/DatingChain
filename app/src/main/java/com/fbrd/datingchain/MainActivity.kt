package com.fbrd.datingchain

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var mCurrentUser: FirebaseUser? = null
    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var mAuthStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFirebaseAuth = FirebaseAuth.getInstance()
        mAuthStateListener = FirebaseAuth.AuthStateListener {
            mCurrentUser = mFirebaseAuth.currentUser

            if (mCurrentUser == null) {
                // User not logged in

                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(
                            arrayListOf(
                                AuthUI.IdpConfig.GoogleBuilder().build()
                            )
                        )
                        .build(),
                    RC_LOGIN
                )

            } else {
                // User logged in

                text.text = "${mCurrentUser?.displayName}"
            }
        }

        mFirebaseAuth.addAuthStateListener(mAuthStateListener)
    }

    companion object {
        const val RC_LOGIN = 5
    }
}
