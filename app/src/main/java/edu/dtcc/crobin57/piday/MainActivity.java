package edu.dtcc.crobin57.piday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.os.Handler;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    private TextView numTextView;
    private Integer index;
    private String stringPi;
    private static final int DIGITS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Get reference
        numTextView = (TextView) findViewById(R.id.textView);

        numTextView.setMovementMethod(new ScrollingMovementMethod());

        index = 0;

        BigDecimal piValue = Pi.pi(DIGITS);
        stringPi = piValue.toString();

        Thread thread = new Thread (countNumbers);
        thread.start();

    }

    @Override
    protected void onStart() {
        super.onStart();
        index = 0;
    }

    private Runnable countNumbers = new Runnable() {
        private static final int DELAY = 50;
        @Override
        public void run() {
            try {
                while (index < stringPi.length() - 1) {
                    //index++;
                    Thread.sleep(DELAY);
                    threadHandler.sendEmptyMessage(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public Handler threadHandler = new Handler() {
        public void handleMessage (android.os.Message message) {
            numTextView.append(String.valueOf(stringPi.charAt(index++)));
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
