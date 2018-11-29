package alexvargas.quickjobs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import alexvargas.quickjobs.MYSQL.Sender;

public class AddActivity  extends AppCompatActivity implements View.OnClickListener {

    String urlAddress = "http://192.156.214.78/avargas/Jobs.php";


    private EditText title;
    private EditText descpriton;
    private EditText location;
    private EditText phone;
    private Button save;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = (EditText) findViewById(R.id.editTextTitle);
        descpriton = (EditText) findViewById(R.id.editTextDesc);
        location = (EditText) findViewById(R.id.editTextLoc);
        phone = (EditText) findViewById(R.id.editTextPhone);
        save = (Button) findViewById(R.id.buttonSave);

        save.setOnClickListener(this);


    }
    private void userSaved(){

        String Title = title.getText().toString().trim();
        String Desc = descpriton.getText().toString().trim();
        String Loc = location.getText().toString().trim();
        String Phone = phone.getText().toString().trim();

        if (TextUtils.isEmpty(Title)){
            Toast.makeText(this,"Missing Title",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(Desc)){
            Toast.makeText(this,"Missing Description",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(Loc)){
            Toast.makeText(this,"Missing City",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(Phone)){
            Toast.makeText(this,"Missing Phone Number",Toast.LENGTH_LONG).show();
            return;
        }


        Sender s = new Sender(AddActivity.this, urlAddress, title, descpriton, location, phone);
        s.execute();
        startActivity(new Intent(getApplicationContext(), ListsActivity.class));
    }


    @Override
    public void onClick(View view) {
        if(view==save)
        {
            userSaved();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {

            case R.id.action_list:
                startActivity(new Intent(this,ListsActivity.class));
                return true;


            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this,LoginActivity.class));
                return true;
            case R.id.location:
                startActivity(new Intent(this,ZipCodeListActivity.class));
                return true;
            case R.id.piracy:
                startActivity(new Intent(this,PiracyPolicyActivity.class));
                return true;
            case R.id.terms:
                startActivity(new Intent(this,TermUseActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
