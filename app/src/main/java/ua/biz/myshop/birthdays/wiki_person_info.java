package ua.biz.myshop.birthdays;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;


public class wiki_person_info extends Activity {
    Intent intent;
    private String TAG = wiki_person_info.class.getSimpleName();
    static String code;
    static HashMap<String, String> rockstar;
    public static final String EXTRA_RESPONSE = "EXTRA_RESPONSE";
    private static final int REQUEST_RESPONSE = 1;
    //String country = getIntent().getStringExtra(star_list.EXTRA_COUNTRY);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wiki_person_info);
        code = getIntent().getStringExtra(star_list.EXTRA_RESPONSE);

        final AsyncTask<String, Integer, String> execute = new GetUrlContentTask() {
            @Override
            protected String doInBackground(String... strings) {
                rockstar = getData();
                return null;
            }
        }.execute();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_central);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Go to Central!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                intent = new Intent(wiki_person_info.this, Central.class);
                startActivity(intent);
            }
        });
        FloatingActionButton fab_birthday = (FloatingActionButton) findViewById(R.id.fab_birthday);
        fab_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Go to Birthday!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                intent = new Intent(wiki_person_info.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_RESPONSE) {
                Toast.makeText(this, data.getStringExtra(wiki_person_info.EXTRA_RESPONSE), Toast.LENGTH_SHORT).show();
            }
        }
    }

   /* public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        CharSequence message;
        switch (item.getItemId())
        {
            case R.id.action_birthdays:
                message = "Rockstar";
                Intent intent = new Intent(wiki_person_info.this, wiki_person_info.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
                message = "Settings";
                break;

            default:
                return super.onContextItemSelected(item);
        }
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        return true;
    }
    */
    private HashMap<String, String> getData() {
        HashMap<String, String> contact = new HashMap<>();
        HttpHandler sh = new HttpHandler();
        // Making a request to url and getting response
        //https://en.wikipedia.org/w/api.php?action=query&format=json&list=search&srsearch=chocolate
        String url = "https://en.wikipedia.org/api/rest_v1/page/summary/"+ code;
        //String url = "https://en.wikipedia.org/w/api.php?action=query&format=json&list=search&srsearch=chocolate";
        String jsonStr = sh.makeServiceCall(url);

        Log.e(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String description = jsonObj.getString("extract");
                JSONObject c = jsonObj.getJSONObject("titles");

                String canonical = c.getString("canonical");
                String normalized = c.getString("normalized");
                String display = c.getString("display");
                c = jsonObj.getJSONObject("thumbnail");
                String source = c.getString("source");
                String width = c.getString("width");
                String height = c.getString("height");

                // adding each child node to HashMap key => value
                contact.put("canonical", canonical);
                contact.put("description", description);
                contact.put("normalized", normalized);
                contact.put("display", display);
                contact.put("source", source);
                contact.put("width", width);
                contact.put("height", height);

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

        return contact;
    }
    private abstract class GetUrlContentTask extends AsyncTask<String, Integer, String> {

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(String result) {
            TextView tv1 = (TextView) findViewById(R.id.text_displaytitle);
            //TextView tv2 = (TextView) findViewById(R.id.editText_description);
            TextView tv3 = (TextView) findViewById(R.id.editDescription);
            tv3.setMovementMethod(new ScrollingMovementMethod());
            ImageView imageView = (ImageView) findViewById(R.id.imageView_thumbnail);
            String display = rockstar.get("display");
            //String canonical = rockstar.get("canonical");
            String description = rockstar.get("description");
            String URL = rockstar.get("source");
            tv1.setText(String.valueOf(display));
            //tv2.setText(String.valueOf(canonical));
            tv3.setText(String.valueOf(description));
            //int count = getIntent().getIntExtra(REQUEST_RESPONSE, 0);

            new ImageDownloaderTask(imageView).execute(URL);

            imageView.setImageURI(null);
        }

    }

    private Bitmap getImageBitmap(String URL) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(URL);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "Error getting bitmap", e);
        }
        return bm;
    }
}