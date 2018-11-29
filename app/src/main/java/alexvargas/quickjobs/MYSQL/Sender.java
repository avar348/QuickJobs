package alexvargas.quickjobs.MYSQL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import alexvargas.quickjobs.Jobs.Jobs;

/**
 * Created by alexvargas1 on 3/9/17.
 */

public class Sender extends AsyncTask<Void,Void,String> {

    Context context;
    String urlAddress;
    String query;
    EditText title,description,location,phone;

    Jobs jobs;

    ProgressDialog progressDialog;

    public Sender(Context context, String urlAddress, EditText title, EditText description, EditText location, EditText phone) {
        this.context = context;
        this.urlAddress = urlAddress;
        this.title = title;
        this.description = description;
        this.location = location;
        this.phone = phone;

        jobs= new Jobs();
        jobs.setTitle(title.getText().toString());
        jobs.setDescription(description.getText().toString());
        jobs.setLocation(location.getText().toString());
        jobs.setPhone(phone.getText().toString());


    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Send");
        progressDialog.setMessage("Sending... Please Wait");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        return this.send();
    }


    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);

        progressDialog.dismiss();

        if (s == null)
        {
            Toast.makeText(context,"Unsuccessful",Toast.LENGTH_SHORT).show();
        }else
        {
            if (s=="Bad Response"){

                Toast.makeText(context,"Unsuccessful Bad Response",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context,"Successful",Toast.LENGTH_SHORT).show();

                //Clear
                title.setText("");
                description.setText("");
                location.setText("");
                phone.setText("");

            }
        }



    }


    private String send(){
        HttpURLConnection connection = Connector.connection(urlAddress);
        if (connection == null){
            return null;

        }
        try {



            OutputStream outputStream = connection.getOutputStream();
            //Write
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            bufferedWriter.write(new DataPackager(jobs).packdata());

            bufferedWriter.flush();

            //Release
            bufferedWriter.close();
            outputStream.close();


            //Succesful or not
            int responseCode = connection.getResponseCode();
            if (responseCode == connection.HTTP_OK)
            {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer response = new StringBuffer();


                String line = null;

                while ((line=bufferedReader.readLine()) !=null){
                    response.append(line);

                }
                bufferedReader.close();
                return response.toString();
            }else
            {
                return "Bad Response";

            }


        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
