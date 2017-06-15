package com.epicodus.myrecords.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.myrecords.Constants;
import com.epicodus.myrecords.R;
//import com.epicodus.myrecords.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.enterUserName) EditText mEnterUsername;
    @BindView(R.id.enterEmail) EditText mEnterEmail;
    @BindView(R.id.enterPassword) EditText mEnterPassword;
    @BindView(R.id.confirmPassword) EditText mConfirmPassword;
    @BindView(R.id.createAccountButton) Button mCreateAccountButton;
    @BindView(R.id.loginTextView) TextView mLogin;
    @BindView(R.id.createAccountHeader) TextView mCreateAccountHeader;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mAuthProgressDialog;
    private String mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);
        mCreateAccountButton.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

        Typeface headerFont = Typeface.createFromAsset(getAssets(), "fonts/header.ttf");
        mCreateAccountHeader.setTypeface(headerFont);

        createAuthStateListener();
        createAuthProgressDialog();
    }

    @Override
    public void onClick(View v) {
        if (v == mLogin) {
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        if (v == mCreateAccountButton) {
            createNewUser();
        }

    }

    private void createAuthStateListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Spinning...");
        mAuthProgressDialog.setCancelable(false);
    }

    private void createNewUser() {
        mUserName = mEnterUsername.getText().toString().trim();
        String email = mEnterEmail.getText().toString().trim();
        String password = mEnterPassword.getText().toString().trim();
        String confirmPassword = mConfirmPassword.getText().toString().trim();

        boolean validEmail = isValidEmail(email);
        boolean validName = isValidName(mUserName);
        boolean validPassword = isValidPassword(password, confirmPassword);
        if (!validEmail || !validName || !validPassword) return;
        mAuthProgressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mAuthProgressDialog.dismiss();
                if (task.isSuccessful()) {
                    createFirebaseUserProfile(task.getResult().getUser());
                } else {
                    Toast.makeText(CreateAccountActivity.this, "Account Creation Failed - Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createFirebaseUserProfile(final FirebaseUser user) {
        UserProfileChangeRequest addProfileName = new UserProfileChangeRequest.Builder()
                .setDisplayName(mUserName)
//                .setPhotoUri(Uri.parse(Constants.defaultImgUrl))
                .build();
        user.updateProfile(addProfileName).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
//                    User newUser = new User(user.getDisplayName(), user.getPhotoUrl().toString());
//                    newUser.setId(user.getUid());
//
//                    DatabaseReference userRef = FirebaseDatabase.getInstance()
//                            .getReference(Constants.FIREBASE_USER_REF)
//                            .child(user.getUid());
//
//                    userRef.setValue(newUser);
//
//                    Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                    finish();
                }
            }
        });
    }

    private boolean isValidEmail(String email) {
        boolean isGoodEmail = (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) mEnterEmail.setError("Enter Vaild Email Address");
        return isGoodEmail;
    }

    private boolean isValidName(String name) {
        boolean isGoodName = !name.equals("");
        if (!isGoodName) mEnterUsername.setError("Enter Username");
        return isGoodName;
    }

    private boolean isValidPassword(String password, String confirmPassword) {
        if (password.length() < 6) {
            mEnterPassword.setError("Passwords Must Be At Least 6 Characters");
            return false;
        } else if (!password.equals(confirmPassword)) {
            mEnterPassword.setError("Passwords Do Not Match");
            return false;
        }
        return true;
    }

}