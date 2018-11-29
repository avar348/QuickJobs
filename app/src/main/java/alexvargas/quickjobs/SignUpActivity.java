package alexvargas.quickjobs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends Activity implements View.OnClickListener {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignUp;

    private TextView textViewSignIn;

    private ProgressDialog progressDialog;

    // defining firebase objt
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance();


        if (firebaseAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),ListsActivity.class));

        }

        // initialize views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonSignUp = (Button) findViewById(R.id.buttonSignIn);
        textViewSignIn = (TextView) findViewById(R.id.textViewSignUp);

        progressDialog = new ProgressDialog(this);


        buttonSignUp.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);

    }
    private void registerUser(){

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Enter in email",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Enter in password",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Registering ");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(),ListsActivity.class));

                }
                else {
                    Toast.makeText(SignUpActivity.this,"Registration Error", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });




    }


    @Override
    public void onClick(View view) {
        if (view == buttonSignUp)
        {
            registerUser();
        }

        if (view == textViewSignIn)
        {
            startActivity(new Intent(this,LoginActivity.class));
        }

    }
}
