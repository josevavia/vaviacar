package es.josevavia.vaviacar;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("tag ", "onCreate start");

        setContentView(R.layout.activity_main);

        IntentFilter iF = new IntentFilter();
        iF.addAction("com.android.music.metachanged");
        iF.addAction("com.android.music.playstatechanged");
        iF.addAction("com.android.music.playbackcomplete");
        iF.addAction("com.android.music.queuechanged");
        iF.addAction("com.spotify.mobile.android.metadatachanged");
        iF.addAction("com.spotify.mobile.android.playbackstatechanged");
        iF.addAction("com.spotify.mobile.android.queuechanged");

        registerReceiver(musicReceiver, iF);

        Log.v("tag ", "onCreate end");
    }

    private BroadcastReceiver musicReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v("tag ", "onReceive start");
            String action = intent.getAction();
            Log.v("tag ", "action: " + action);

            TextView t = (TextView) findViewById(R.id.textView);
            if (action.equals("com.spotify.mobile.android.metadatachanged")) {
                t.setText("Metadatachanged\n");
            } else if (action.equals("com.spotify.mobile.android.playbackstatechanged")) {

                if (!intent.getBooleanExtra("playing", false)) {
                    t.setText("Nothing playing\n");
                } else {
                    t.setText("Now playing\n");
                }
            } else {
                t.setText("Unknown data: " + action + "\n");
            }

            for(String set : intent.getExtras().keySet()) {
                t.append(set + ":" + intent.getStringExtra(set) + "\n");
                Log.v("tag ", "set: " + intent.getStringExtra(set));
            }

            Log.v("tag ", "onReceive end");
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.action_about:
                launchAbout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onStop() {
        try {
            unregisterReceiver(musicReceiver);
        } catch (Exception e){ }
        super.onStop();
    }

    private void launchAbout() {
        Log.v("tag ", "lauch about start");
        Intent i = new Intent(this, About.class);
        startActivity(i);
        Log.v("tag ", "lauch about end");
    }
}
