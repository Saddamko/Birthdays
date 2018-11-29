package ua.biz.myshop.birthdays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Central extends Activity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central);

        FloatingActionButton fab_birthday = (FloatingActionButton) findViewById(R.id.fab_birthday);
        fab_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                intent = new Intent(Central.this, Birthdays.class);
            }
        });
        FloatingActionButton fab_rockstar = (FloatingActionButton) findViewById(R.id.fab_central);
        fab_rockstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                intent = new Intent(Central.this, wiki_person_info.class);
            }
        });
    }

}
