package ua.biz.myshop.birthdays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central);
    }

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
                intent = new Intent(MainActivity.this, Birthdays.class);
                startActivity(intent);
                break;
            case R.id.action_central:
                message = "Central";
                intent = new Intent(MainActivity.this, Central.class);
                startActivity(intent);
                break;
            case R.id.action_rockstar:
                message = "Rockstar";
                intent = new Intent(MainActivity.this, wiki_person_info.class);
                startActivity(intent);
                break;
            case R.id.action_stars:
                message = "Stars";
                intent = new Intent(MainActivity.this, star_list.class);
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

}