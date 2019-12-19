package com.haddronix.urgent;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View.OnClickListener;

public class Menu extends Activity {

    private Button start, about;
    private MediaPlayer bgn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        start = (Button) findViewById(R.id.start);
        about = (Button) findViewById(R.id.about);
        bgn = MediaPlayer.create(Menu.this, R.raw.punchwhip);
        start.setOnClickListener(begin);
        about.setOnClickListener(abt);

    }

    OnClickListener begin = new OnClickListener() {
        @Override
        public void onClick(View v) {
            bgn.start();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            //setContentView(R.layout.act_main);
        }
    };

    OnClickListener abt = new OnClickListener() {
        @Override
        public void onClick(View v) {
            bgn.start();
            Intent intent = new Intent(getApplicationContext(), AboutUs.class);
            startActivity(intent);
        }
    };

}
