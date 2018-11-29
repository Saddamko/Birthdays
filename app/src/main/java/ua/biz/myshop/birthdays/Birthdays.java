package ua.biz.myshop.birthdays;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
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

public class Birthdays extends Activity {

    private String TAG = Birthdays.class.getSimpleName();
    private ListView lv;
    private ImageView imageView;


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        CharSequence message;
        Intent intent;
        switch (item.getItemId())
        {
            case R.id.action_birthdays:
                message = "Birthdays";
                //textview2.setBackgroundColor(Color.RED);
                intent = new Intent(Birthdays.this, Birthdays.class);
                startActivity(intent);
                break;
            case R.id.action_central:
                message = "Central";
                //textview2.setBackgroundColor(Color.RED);
                intent = new Intent(Birthdays.this, Central.class);
                startActivity(intent);
                break;
            case R.id.action_rockstar:
                message = "Rockstar";
                //textview2.setBackgroundColor(Color.RED);
                intent = new Intent(Birthdays.this, wiki_person_info.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
                message = "Settings";
                //lv.setTextSize(18);
                break;

            default:
                return super.onContextItemSelected(item);
        }
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.list);
        //registerForContextMenu(lv);
        new GetUrlContentTask() {
            @Override
            protected String doInBackground(String... strings) {
                ArrayList<ListItem> listData = getListData();
                final ListView listView = (ListView) findViewById(R.id.list);
                listView.setAdapter(new CustomListAdapter(Birthdays.this, listData));
                return null;
            }
        }.execute();

    }

    private ArrayList<ListItem> getListData() {
        ArrayList<ListItem> listMockData = new ArrayList<ListItem>();

        HttpHandler sh = new HttpHandler();
        // Making a request to url and getting response
        String url = "http://myshop.biz.ua:3000/nearestbirthdays";
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
                    String id = c.getString("ID");
                    String name = c.getString("FULL_NAME");
                    String email = c.getString("EMAIL");
                    String job = c.getString("JOB");
                    String mobile = c.getString("MOBILE");
                    String birthday = c.getString("BIRTHDAY");
                    String photo = "http://blog.myshop.biz.ua/pic/" + c.getString("PHOTO");

                    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
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
                    ListItem newsData = new ListItem();
                    newsData.setUrl(photo);
                    newsData.setName(name);
                    newsData.setEmail(email);
                    newsData.setMobile(mobile);
                    newsData.setBirthday(birthday);
                    listMockData.add(newsData);

                    // adding each child node to HashMap key => value
                    contact.put("id", id);
                    contact.put("name", name);
                    contact.put("email", email);
                    contact.put("mobile", mobile);
                    contact.put("birthday", birthday);
                    contact.put("job", job);
                    contact.put("photo", photo);

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