package alexvargas.quickjobs.MYSQL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

import alexvargas.quickjobs.Jobs.Jobs;

/**
 * Created by alexvargas1 on 3/9/17.
 */

public class DataPackager {


    Jobs jobs;

    public DataPackager(Jobs jobs) {
        this.jobs = jobs;
    }

    public String packdata()
    {
        JSONObject jsonObject = new JSONObject();
        StringBuffer stringBuffer = new StringBuffer();

        try {
            jsonObject.put("Title",jobs.getTitle());
            jsonObject.put("Description",jobs.getDescription());
            jsonObject.put("Location",jobs.getLocation());
            jsonObject.put("Phone",jobs.getPhone());


            Boolean firstvalue = true;
            Iterator iterator = jsonObject.keys();

            do {
                String key= iterator.next().toString();
                String value = jsonObject.get(key).toString();


                if (firstvalue)
                {
                    firstvalue = false;
                }else
                {
                    stringBuffer.append("&");
                }

                stringBuffer.append(URLEncoder.encode(key,"UTF-8"));
                stringBuffer.append("=");
                stringBuffer.append(URLEncoder.encode(value,"UTF-8"));

            }while (iterator.hasNext());


            return stringBuffer.toString();


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
