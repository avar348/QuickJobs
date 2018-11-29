package alexvargas.quickjobs.MYSQL;

/**
 * Created by alexvargas1 on 3/9/17.
 */

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Connector {


    public static HttpURLConnection connection(String urlAddress)
    {
        try {
            URL url = new URL(urlAddress);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();


            // Set Properties
            con.setRequestMethod("POST");
            con.setConnectTimeout(20000);
            con.setReadTimeout(20000);
            con.setDoInput(true);
            con.setDoOutput(true);


            con.setRequestMethod("GET");
            con.setConnectTimeout(20000);
            con.setReadTimeout(20000);
            con.setDoInput(true);
            con.setDoOutput(true);







            return con;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }





}
