package com.miraclemessages.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.miraclemessages.R;
import com.miraclemessages.ui.activities.PreCameraActivity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MessagesListAdapter extends BaseAdapter{
    Context context;
    ArrayList<HashMap<String,String>> myMessagesList;
    private static LayoutInflater inflater=null;
    public MessagesListAdapter(PreCameraActivity mainActivity, ArrayList<HashMap<String,String>> myList) {
        myMessagesList = myList;
        context=mainActivity;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return myMessagesList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

//    public class Holder
//    {
//        TextView tv;
//        ImageView img;
//    }
//    static class ViewHolder {
//        TextView name;
//        ImageView image;
////        ImageView icon;
//    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView;
        rowView = inflater.inflate(R.layout.messages_list, null);
        TextView name =(TextView) rowView.findViewById(R.id.name);
        ImageView image = (ImageView) rowView.findViewById(R.id.image);
        TextView messageStatus = (TextView) rowView.findViewById(R.id.messageStatus);
        TextView caseStatus = (TextView) rowView.findViewById(R.id.caseStatus);
        TextView nextStep = (TextView) rowView.findViewById(R.id.nextStep);
        String caseName = "No Name";
        HashMap<String, String> caseDict = myMessagesList.get(position);
        if(caseDict.containsKey("caseStatus"))
            caseStatus.setText("Case Status: " + caseDict.get("caseStatus"));
        if(caseDict.containsKey("messageStatus"))
            messageStatus.setText(caseDict.get("messageStatus"));
        if(caseDict.containsKey("nextStep"))
            nextStep.setText(caseDict.get("nextStep"));
        if(caseDict.containsKey("firstName") && caseDict.containsKey("lastName"))
            caseName = caseDict.get("firstName") + " " + caseDict.get("lastName");
        else if(caseDict.containsKey("firstName"))
            caseName = caseDict.get("firstName");
        else if(caseDict.containsKey("lastName"))
            caseName = caseDict.get("lastName");
        name.setText(caseName);
        if(caseDict.get("photo") != null) {
            new DownloadImageTask(image)
                    .execute(caseDict.get("photo"));
        }
        else {
            image.setImageResource(R.drawable.blank_profile);
        }
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "You Clicked "+myMessagesList.get(position).get("firstName"), Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://my.miraclemessages.org/#!/cases/" + myMessagesList.get(position).get("caseID")));
                context.startActivity(i);
            }
        });
        return rowView;
    }

}

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
        this.cancel(true);
    }
}