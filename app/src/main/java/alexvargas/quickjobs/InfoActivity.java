package alexvargas.quickjobs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class InfoActivity extends AppCompatActivity {
    TextView titleTxt;
    TextView descriptionTxt;
    TextView locationTxt;
    TextView phoneTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titleTxt = (TextView) findViewById(R.id.textViewTitle1);
        descriptionTxt = (TextView) findViewById(R.id.textViewDescription1);
        locationTxt = (TextView) findViewById(R.id.textViewLocation1);
        phoneTxt = (TextView) findViewById(R.id.textViewPhone1);

        // Reviceve data
        Intent i = getIntent();
        String title = i.getExtras().getString("TITLE_KEY");
        String description = i.getExtras().getString("DESC_KEY");
        String location = i.getExtras().getString("LOC_KEY");
        String phone = i.getExtras().getString("PHONE_KEY");

        //Bind
        titleTxt.setText(title);
        descriptionTxt.setText(description);
        locationTxt.setText(location);
        phoneTxt.setText(phone);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_info,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {

            case R.id.action_add:
                startActivity(new Intent(this,AddActivity.class));
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
