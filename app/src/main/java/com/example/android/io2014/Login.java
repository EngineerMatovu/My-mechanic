package com.example.android.io2014;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.io2014.fire.*;
import com.example.android.io2014.fire.MainActivity;
import com.example.android.io2014.login.AlertDialogManager;
import com.example.android.io2014.login.SessionManager;

public class Login extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    public static android.content.SharedPreferences SharedPreferences = null;

    private static final String PREFER_NAME = "Reg";

    Button _loginButton;
    EditText _emailText,_passwordText;
    TextView _signupLink;
    AlertDialogManager alert = new AlertDialogManager();
    String passz,emailz;
    // Session Manager Class
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new SessionManager(getApplicationContext());

        _loginButton=(Button)findViewById(R.id.btn_login);
        _emailText=(EditText)findViewById(R.id.input_email);
        _passwordText=(EditText)findViewById(R.id.input_password);
        _signupLink=(TextView)findViewById(R.id.link_signup);

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            login();
            }
        });
        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
        SharedPreferences = getSharedPreferences(PREFER_NAME, 0);
    }
    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(Login.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.
        if(email.trim().length() > 0 && password.trim().length() > 0){
            // For testing puspose username, password is checked with sample data
            // username = test
            // password = test
            if(email.equals("mechanics@gmail.com") && password.equals("mechanics")){

                // Creating user login session
                // For testing i am stroing name, email as follow
                // Use user real data
                session.createLoginSession("Android Hive", "mechanics@gmail.com");

                // Staring MainActivity
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                onLoginSuccess();

            }else{
                // username / password doesn't match
                alert.showAlertDialog(Login.this, "Login failed..", "Email/Password is incorrect", false);
            }
        }else{
            // user didn't entered username or password
            // Show alert asking him to enter the details
            alert.showAlertDialog(Login.this, "Login failed..", "Please enter username and password", false);
        }

//        if(email.trim().length() > 0 && password.trim().length() > 0){
//
//            if (SharedPreferences.contains("txtPassword"))
//            {
//                passz = (SharedPreferences).getString("txtPassword", "");
//
//            }
//
//            if (SharedPreferences.contains("email"))
//            {
//                emailz = (SharedPreferences).getString("email", "");
//
//            }
//
//
//            if(password.equals(passz) && email.equals(emailz)){
//
//                session.createLoginSession("Android Hive", emailz);
//
//                // Staring MainActivity
//                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(i);
//                onLoginSuccess();
//                 progressDialog.dismiss();
//            }else{
//
//                // username / password doesn't match&
//                Toast.makeText(getApplicationContext(),
//                        "Username/Password is incorrect",
//                        Toast.LENGTH_LONG).show();
//
//            }
//        }else{
//
//            // user didn't entered username or password
//            Toast.makeText(getApplicationContext(),
//                    "Please enter username and password",
//                    Toast.LENGTH_LONG).show();
//            progressDialog.dismiss();
//        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
