package alexvargas.quickjobs.MYSQL;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import alexvargas.quickjobs.Jobs.Jobs;
import alexvargas.quickjobs.m_UI.CustomAdapter;

/**
 * Created by alexvargas1 on 3/9/17.
 */

public class DataParser extends AsyncTask<Void,Void,Boolean> {
    Context context;
    String jsonData;
    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;

    //ProgressDialog progressDialog;
    ArrayList<Jobs> job1 = new ArrayList<>();

    public DataParser(Context context, String jsonData, ListView listView,SwipeRefreshLayout swipeRefreshLayout) {
        this.context = context;
        this.jsonData = jsonData;
        this.listView = listView;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (swipeRefreshLayout.isRefreshing())
        {
            swipeRefreshLayout.setRefreshing(false);
        }

        //progressDialog = new ProgressDialog(context);
        //progressDialog.setTitle("Parse");
        //progressDialog.setMessage("Parsing ... Please Wait");
        // progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return this.parseData();
    }


    @Override
    protected void onPostExecute(Boolean parse) {
        super.onPostExecute(parse);


        //progressDialog.dismiss();

        if (parse)
        {
            listView.setAdapter(new CustomAdapter(context,job1));

        }

    }


    private Boolean parseData(){
        try
        {
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONObject jsonObject = null;

            job1.clear();
            Jobs job;
            for (int i=0;i<jsonArray.length();i++)
            {
                jsonObject = jsonArray.getJSONObject(i);

                int id = jsonObject.getInt("idJobs");
                String title = jsonObject.getString("Title");
                String phone = jsonObject.getString("Phone");
                String description = jsonObject.getString("Description");
                String location = jsonObject.getString("Location");


                job = new Jobs();

                job.setId(id);
                job.setTitle(title);
                job.setPhone(phone);
                job.setLocation(location);
                job.setDescription(description);


                job1.add(job);
            }

            return true;


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}

