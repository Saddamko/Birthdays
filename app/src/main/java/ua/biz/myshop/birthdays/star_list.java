package ua.biz.myshop.birthdays;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class star_list extends Activity {

    private String TAG = star_list.class.getSimpleName();
    private ListView lv;
    public static final String EXTRA_RESPONSE = "EXTRA_RESPONSE";
    private static final int REQUEST_RESPONSE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stars_list);
        lv = (ListView) findViewById(R.id.list);
        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        StarsListItem tableRow = (StarsListItem) parent.getItemAtPosition(position);
                        String canonical =  tableRow.getCanonical();
                        //StarsListItem Item  = String.valueOf( parent.getItemAtPosition(position);
                        Toast.makeText(getApplicationContext(),
                                canonical,
                                Toast.LENGTH_LONG).show();
                        Intent myIntent = new Intent(view.getContext(), wiki_person_info.class);
                        //myIntent.putExtra(EXTRA_RESPONSE, 1);
                        myIntent.putExtra(EXTRA_RESPONSE, canonical);
                        startActivityForResult(myIntent, REQUEST_RESPONSE);
                        //setResult(RESULT_OK, myIntent);
                    }
                });

        //registerForContextMenu(lv);
        new GetUrlContentTask() {
            @Override
            protected String doInBackground(String... strings) {
                ArrayList <StarsListItem> listData = getListData();
                final ListView listView = (ListView) findViewById(R.id.list);
                listView.setAdapter(new StarsCustomListAdapter(star_list.this, listData));
                return null;
            }
        }.execute();

    }

    private ArrayList<StarsListItem> getListData() {
        ArrayList<StarsListItem> listMockData = new ArrayList<StarsListItem>();

        HttpHandler sh = new HttpHandler();
        // Making a request to url and getting response
        String url = "http://myshop.biz.ua:3000/stars";
        String jsonStr = sh.makeServiceCall(url);

        Log.e(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("data");

                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    String canonical = c.getString("CANONICAL");
                    String birthday = c.getString("BD");
                    String band = c.getString("BAND");

                    SimpleDateFormat formatter1 = new SimpleDateFormat("dd.MM.yyyy");
                    try {
                        Date date1 = formatter1.parse(birthday);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        birthday = formatter.format(date1);
                        Date date2 = new Date();
                        //Period period = Period.between(date1, date2);

                        int years = (int) getDateDiff(date1, date2, TimeUnit.DAYS);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // tmp hash map for single contact
                    HashMap<String, String> contact = new HashMap<>();
                    StarsListItem newsData = new StarsListItem();
                    newsData.setCanonical(canonical);
                    newsData.setBand(band);
                    newsData.setBirthday(birthday);
                    listMockData.add(newsData);

                    // adding each child node to HashMap key => value
                    contact.put("canonical", canonical);
                    contact.put("birthday", birthday);
                    contact.put("band", band);

                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

            }

        } else {
            Log.e(TAG, "Couldn't get json from server.");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG).show();
                }
            });
        }

        return listMockData;
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
    private abstract class GetUrlContentTask extends AsyncTask<String, Integer, String> {

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(String result) {
            // this is executed on the main thread after the process is over
            // update your UI here
            //displayMessage(result);
        }
    }

}