package alexvargas.quickjobs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import alexvargas.quickjobs.MYSQL.Downloader;

public class ListsActivity extends AppCompatActivity {
    final static String urladdress = "http://192.156.214.78/avargas/JobsSelect.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ListView listView = (ListView) findViewById(R.id.listviewItems);
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        new Downloader(ListsActivity.this,urladdress,listView,swipeRefreshLayout).execute();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Downloader(ListsActivity.this,urladdress,listView,swipeRefreshLayout).execute();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_list,menu);
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
