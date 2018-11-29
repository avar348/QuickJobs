package alexvargas.quickjobs.MYSQL;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by alexvargas1 on 3/9/17.
 */

public class Downloader extends AsyncTask<Void,Void,String> {
    Context context;
    String urlAddress;
    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    public Downloader(Context context, String urlAddress, ListView listView,SwipeRefreshLayout swipeRefreshLayout) {
        this.context = context;
        this.urlAddress = urlAddress;
        this.listView = listView;
        this.swipeRefreshLayout = swipeRefreshLayout;

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if(swipeRefreshLayout.isRefreshing())
        {
            swipeRefreshLayout.setRefreshing(false);
        }



    }

    @Override
    protected String doInBackground(Void... params) {
        return downloadData();
    }

    @Override
    protected void onPostExecute(String JsonData) {
        super.onPostExecute(JsonData);

        if (JsonData==null)
        {
            swipeRefreshLayout.setRefreshing(true);
            Toast.makeText(context,"Unsuccesful,No data",Toast.LENGTH_SHORT).show();
        }else {
            DataParser parser = new DataParser(context,JsonData,listView,swipeRefreshLayout);
            parser.execute();

        }

    }

    private String downloadData() {
        HttpURLConnection connection = Connector.connection(urlAddress);
        if (connection == null)
        {
            return null;
        }
        try
        {


            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            StringBuffer jsonData = new StringBuffer();

            while ((line = bufferedReader.readLine()) !=null)
            {
                jsonData.append(line+"\n");
            }

            bufferedReader.close();
            inputStream.close();

            return jsonData.toString();



        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}