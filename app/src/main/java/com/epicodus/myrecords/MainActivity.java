package com.epicodus.myrecords;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.loginButton) Button mLogInButton;
    @Bind(R.id.signupButton) Button mSignUpButton;
    @Bind(R.id.editUsername) EditText mEditUsername;
    @Bind(R.id.myRecordsHeader) TextView mMyRecordsHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Typeface headerFont = Typeface.createFromAsset(getAssets(), "fonts/header.ttf");
        mMyRecordsHeader.setTypeface(headerFont);

        mLogInButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == mLogInButton) {
            String username = mEditUsername.getText().toString();
            Intent intent = new Intent(MainActivity.this, MyCollection.class);
            intent.putExtra("username", username);
            startActivity(intent);
        } else if (v == mSignUpButton) {
            Toast.makeText(MainActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();
        }
    }


}
