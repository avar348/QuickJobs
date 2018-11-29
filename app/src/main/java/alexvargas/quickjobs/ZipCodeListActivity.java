package alexvargas.quickjobs;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import alexvargas.quickjobs.Jobs.Jobs;
import alexvargas.quickjobs.MYSQL.Downloader;
import alexvargas.quickjobs.m_UI.CustomAdapter;

public class ZipCodeListActivity extends AppCompatActivity {


    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    ListView listView;
    SearchView searchView;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip_code_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        searchView = (SearchView) findViewById(R.id.sv);
        final ListView listView = (ListView) findViewById(R.id.listviewItems1);
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() <= 6)
                {
                    searchView.setVisibility(View.VISIBLE);
                    new myAsyncFetch(newText).execute();

                }
                else
                {
                    searchView.setVisibility(View.INVISIBLE);
                }

                return false;
            }
        });











    }

    private class myAsyncFetch extends AsyncTask<String,String,String>

    {

        ProgressDialog pdLoading = new ProgressDialog(ZipCodeListActivity.this);
        HttpURLConnection conn;
        URL url = null;
        String searchQuery;
        SwipeRefreshLayout swipeRefreshLayout;

        public myAsyncFetch(String searchQuery) {
            this.searchQuery = searchQuery;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("Loading");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }


        @Override
        protected String doInBackground(String... strings)
        {
            try {
                url = new URL("http://192.156.214.78/avargas/JobsZip.php");
            }catch (MalformedURLException e){
                e.printStackTrace();
                return e.toString();
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("Query", searchQuery);
                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();



            } catch (IOException e1) {
                e1.printStackTrace();
                return e1.toString();

            }
            try {
                int response_code = conn.getResponseCode();

                if (response_code == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder results = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null)
                    {
                        results.append(line);

                    }
                    return (results.toString());

                }else {
                    return ("Connection error");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String results) {
            pdLoading.dismiss();

            final ArrayList<Jobs> data = new ArrayList<>();

            pdLoading.dismiss();

            if (results.equals("No rows"))
            {
                Toast.makeText(ZipCodeListActivity.this,"No results",Toast.LENGTH_LONG).show();
            }else {

                //Make it display list of Jobs avaibable
                try
                {
                    JSONArray jsonArray = new JSONArray(results);


                    for (int i=0;i<jsonArray.length();i++)
                    {


                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Jobs jobsData = new Jobs();
                        jobsData.id = jsonObject.getInt("idJobs");
                        jobsData.Title = jsonObject.getString("Title");
                        jobsData.Description = jsonObject.getString("Description");
                        jobsData.Location = jsonObject.getString("Location");
                        jobsData.Phone = jsonObject.getString("Phone");
                        data.add(jobsData);
                    }



                    listView = (ListView) findViewById(R.id.listviewItems1);
                    listView.setAdapter(new CustomAdapter(ZipCodeListActivity.this,data));






                } catch (JSONException e) {
                    //Toast.makeText(ZipCodeListActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                    //Toast.makeText(ZipCodeListActivity.this, results.toString(), Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_ziplist,menu);
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
            case R.id.list:
                startActivity(new Intent(this,ListsActivity.class));
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
