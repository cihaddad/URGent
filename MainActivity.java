package com.haddronix.urgent;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

@TargetApi(16)
public class MainActivity extends Activity {
    private EditText choices;
    private MediaPlayer mediaPlayer, addsound;
    private Drawable close, draw;
    private TextView decision;
    private TextView emoji;
    private int[] emoticons = {0x1F600, 0x1F601, 0x1F602, 0x1F923, 0x1F603, 0x1F604, 0x1F605, 0x1F606, 0x1F609, 0x1F60A, 0x1F60B, 0x1F60E, 0x1F60D, 0x1F618, 0x1F617, 0x1F619, 0x1F61A, 0x263A, 0x1F642, 0x1F917, 0x1F914, 0x1F610, 0x1F611, 0x1F636, 0x1F644, 0x1F60F, 0x1F623, 0x1F625, 0x1F62E, 0x1F910, 0x1F62F, 0x1F62A, 0x1F634, 0x1F60C, 0x1F913, 0x1F61B, 0x1F61C, 0x1F61D, 0x1F924, 0x1F612, 0x1F613, 0x1F614, 0x1F615, 0x1F643, 0x1F911, 0x1F632, 0x2639, 0x1F641, 0x1F616, 0x1F61E, 0x1F61F, 0x1F624, 0x1F622, 0x1F62D, 0x1F626, 0x1F627, 0x1F628, 0x1F629, 0x1F62C, 0x1F630, 0x1F631, 0x1F633, 0x1F635, 0x1F621, 0x1F620, 0x1F607, 0x1F920, 0x1F921, 0x1F925, 0x1F637, 0x1F912, 0x1F915, 0x1F922, 0x1F927, 0x1F608, 0x1F47F, 0x1F479, 0x1F47A, 0x1F480, 0x1F47B, 0x1F47D, 0x1F47E, 0x1F916, 0x1F4A9, 0x1F648, 0x1F649, 0x1F64A, 0x1F483, 0x1F4AA, 0x1F595, 0x1F44C, 0x1F44D, 0x1F44A, 0x1F64F, 0x1F440, 0x1F445, 0x1F48B, 0x1F4A3, 0x1F4A5, 0x1F4A6, 0x1F435, 0x1F98D, 0x1F984, 0x1F340, 0x1F34C, 0x1F351, 0x1F346, 0x1F37B, 0x1F37D, 0x1F31E, 0x1F31A, 0x1F308, 0x2614, 0x1F525, 0x1F30A, 0x1F389, 0x1F3C0, 0x1F48A, 0x1F4AF};
    private Button add, clear, remove, decide;
    private LinearLayout main;
    private int num_fields, position, count, choice, rand, id, index, numgen, myInt;
    private List<EditText> edit;
    private ArrayList items;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private SharedPreferences howmuch;
    boolean rng;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        //memory; primitive-data value for how many times user clicks decide
        howmuch = getSharedPreferences("howmany", MainActivity.MODE_PRIVATE);

        //layouts, buttons, onClickListener

        //ADS
        //Banner
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5744678520283045~5119457216");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Interstitial
        mInterstitialAd = new InterstitialAd(MainActivity.this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5744678520283045/7939996015");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();

            }
        });
        requestNewInterstitial();


        mediaPlayer = MediaPlayer.create(this, R.raw.boxing);
        addsound = MediaPlayer.create(this, R.raw.flappybird);
        //draw = (Drawable) findViewById(R.drawable.editbox_background);
        //TextViews
        decision = (TextView) findViewById(R.id.decision);
        emoji = (TextView) findViewById(R.id.emoji);
        main = (LinearLayout) findViewById(R.id.act_main);
        //Buttons
        add = (Button) findViewById(R.id.add);
        clear = (Button) findViewById(R.id.clear);
        decide = (Button) findViewById(R.id.decide);
        remove = (Button) findViewById(R.id.remove);
        //OnClickListeners
        add.setOnClickListener(generate);
        decide.setOnClickListener(choose);
        clear.setOnClickListener(wipeout);
        remove.setOnClickListener(rem);
        edit = new ArrayList<EditText>();
        edit.add((EditText) findViewById(R.id.et1));

        num_fields = 1;
        position = 4;
        count = 0;
        index = 0;
        numgen = 0;
        myInt = howmuch.getInt("howmany", -1);
        rng = howmuch.getBoolean("firsttime", true);
        //boolean terminate = true;
        items = new ArrayList();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);

    }

    OnClickListener generate = new OnClickListener() {
        @Override
        public void onClick(View v) {

            if (addsound.isPlaying()) {
                addsound.reset();
                addsound = MediaPlayer.create(MainActivity.this, R.raw.flappybird);
            }
            addsound.start();
            num_fields++;
            LinearLayout l = new LinearLayout(MainActivity.this);
            EditText et = new EditText(MainActivity.this);

            et.setHint("Choice #" + num_fields);
            et.setBackground(findViewById(R.id.et1).getBackground());
            et.setInputType(InputType.TYPE_CLASS_TEXT);

            l.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            l.addView(et, lp);
            main.addView(l, position);
            edit.add(et);

            position++;
            index++;


            //et.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            //layout.addView(et);
            //final LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            //final EditText edit_text = new EditText(this);
            //edit_text.setLayoutParams(lparams);
            //edit_text.setText("New text: " + text);


        }
    };

    OnClickListener choose = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mInterstitialAd.isLoaded() && myInt%4 == 0 && !rng) {
                mediaPlayer.stop();
                mInterstitialAd.show();
                numgen++;
                SharedPreferences.Editor editor = howmuch.edit();
                editor.putInt("howmany", numgen);
                editor.commit();
                myInt = howmuch.getInt("howmany", -1);
                //requestNewInterstitial();
            } else {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer = MediaPlayer.create(MainActivity.this, id);
                }

                int rand;
                rand = (int) (Math.random() * 10);

                if (!mediaPlayer.isPlaying()) {
                    switch (rand) {
                        case 0:
                            id = R.raw.flappybird;
                            break;
                        case 1:
                            id = R.raw.boxing;
                            break;
                        case 2:
                            id = R.raw.cartoon_mouse_laughter_1;
                            break;
                        case 3:
                            id = R.raw.comedy_kiss_001;
                            break;
                        case 4:
                            id = R.raw.musical_heavenly_choir_with_harp;
                            break;
                        case 5:
                            id = R.raw.musical_heavenly_choir_001;
                            break;
                        case 6:
                            id = R.raw.felix_blume_explosion_dynamite_mountains_long_tail_off;
                            break;
                        case 7:
                            id = R.raw.human_crowd_soccer_x150_cheer_clap_whistle;
                            break;
                        case 8:
                            id = R.raw.punchwhip;
                            break;
                        case 9:
                            id = R.raw.musical_heavenly_choir_001;
                            break;


                        default:
                            id = R.raw.musical_heavenly_choir_001;
                    }
                    mediaPlayer.reset();
                    mediaPlayer = MediaPlayer.create(MainActivity.this, id);
                }

                mediaPlayer.start();


                //Log.d("Duration", "" + mediaPlayer.getDuration());

                int choice = (int) (Math.random() * num_fields);
                int emotichoice = (int) (Math.random() * emoticons.length);
                decision.setText((String) edit.get(choice).getText().toString());
                String emo = "";
                for (int i = 0; i <= 2; i++) {
                    String temp = new String(Character.toChars(emoticons[emotichoice]));
                    emo += temp;
                }

                emoji.setText(emo);
                numgen++;
                SharedPreferences.Editor editor = howmuch.edit();
                editor.putInt("howmany", numgen);
                editor.commit();

                editor.putBoolean("firsttime", false);
                editor.commit();

                myInt = howmuch.getInt("howmany", -1);
                rng = howmuch.getBoolean("firsttime", true);



            }
        }
    };

    /*
    private boolean isOver(MediaPlayer mp){
       while(mp.getCurrentPosition() < 5500){
            continue;
        }

        return true;
    }
    */

    OnClickListener rem = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(num_fields > 1) {
                //main.removeView((View) edit.get(index));
                //main.removeViewInLayout(edit.get(index));
                main.removeViewAt(position-1);
                edit.remove(index);
                index--;
                num_fields--;
                position--;


            }

        }
    };


    OnClickListener wipeout = new OnClickListener() {
        @Override
        public void onClick(View v) {
            edit.clear();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    };
}