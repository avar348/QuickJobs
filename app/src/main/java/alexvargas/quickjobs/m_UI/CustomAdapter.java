package alexvargas.quickjobs.m_UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import alexvargas.quickjobs.InfoActivity;
import alexvargas.quickjobs.Jobs.Jobs;
import alexvargas.quickjobs.R;

/**
 * Created by alexvargas1 on 3/9/17.
 */

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<Jobs> jobs;
    LayoutInflater inflater;

    public CustomAdapter(Context context, ArrayList<Jobs> jobs) {
        this.context = context;
        this.jobs = jobs;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return jobs.size();
    }

    @Override
    public Object getItem(int position) {
        return jobs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.model,parent,false);
        }
        TextView title = (TextView) convertView.findViewById(R.id.textViewTitle);
        TextView location = (TextView) convertView.findViewById(R.id.textViewPhone);

        final Jobs job= jobs.get(position);
        title.setText(job.getTitle());
        location.setText(job.getLocation());

        //Item click
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open detail activity
                openDetailActivity(job.getTitle(),job.getDescription(),job.getLocation(),job.getPhone());
            }
        });

        return convertView;
    }
    //Open detail Activity
    private void openDetailActivity(String title, String description, String location, String phone)
    {
        Intent i = new Intent(context, InfoActivity.class);


        i.putExtra("TITLE_KEY",title);
        i.putExtra("DESC_KEY",description);
        i.putExtra("LOC_KEY",location);
        i.putExtra("PHONE_KEY",phone);


        context.startActivity(i);

    }
}
