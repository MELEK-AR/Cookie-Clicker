package com.melekkaraarslan.cookie_clicker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.AsyncTask;

public class MainActivity extends AppCompatActivity {

    ImageButton btn_star;
    Button btn_Submit;
    Button btn_Upgrade;
    Button btn_Reset;
    TextView tv_title;
    TextView tv_counter;
    int counter;
    int clickWorth = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_star = findViewById(R.id.btn_star);
        btn_Upgrade = findViewById(R.id.btn_Upgrade);
        btn_Submit = findViewById(R.id.btn_Submit);
        btn_Reset = findViewById(R.id.btn_Reset);
        tv_title = findViewById(R.id.tv_title);
        tv_counter = findViewById(R.id.tv_counter);
        EditText punktee = findViewById(R.id.punkte);
        TextView tv_ausgabe = findViewById(R.id.punkte);

        final AppDatabase database = AppDatabase.getDatabase(this);
        final UserDao userDao = database.userDao();

        btn_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter += clickWorth;
                tv_counter.setText(String.valueOf(counter));
            }
        });

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String punkt = punktee.getText().toString();
                User user = new User(punkt);
                new InsertAsyncTask(userDao).execute(user);
            }
        });

        btn_Upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter >= 10) {
                    clickWorth++;
                    counter -= 10;
                    tv_counter.setText(String.valueOf(counter));
                }
            }
        });

        btn_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 0;
                clickWorth = 1;
                tv_counter.setText(String.valueOf(counter));
            }
        });
    }

    private static class InsertAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao asyncUserDao;

        InsertAsyncTask(UserDao userDao) {
            asyncUserDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            asyncUserDao.insert(users[0]);
            return null;
        }
    }
}
