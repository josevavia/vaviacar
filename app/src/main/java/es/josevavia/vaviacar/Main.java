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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("vaviacar", "onCreate start");

        setContentView(R.layout.activity_main);

        IntentFilter btCommunicationFilter = new IntentFilter();
        btCommunicationFilter.setPriority(1000);
        btCommunicationFilter.addAction("es.josevavia.vaviacar.MUSIC_PLAYER");
        BroadcastReceiver txtMusicReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean playing = intent.getBooleanExtra("playing", false);

                if (playing) {
                    setMusicPlaying(
                            intent.getStringExtra("artist"),
                            intent.getStringExtra("album"),
                            intent.getStringExtra("track")
                    );
                } else {
                    setMusicStopped();
                }
            }
        };
        registerReceiver(txtMusicReceiver, btCommunicationFilter);

        Log.v("vaviacar", "onCreate end");
    }

    private MusicReceiver musicReceiver = new MusicReceiver();

    protected void onStop() {
        try {
            unregisterReceiver(musicReceiver);
        } catch (Exception e) {
        }
        super.onStop();
    }


    public void setTxtArtist(String text) {
        TextView txtArtist = (TextView) findViewById(R.id.txtArtist);
        txtArtist.setText(text);
    }

    public void setTxtAlbum(String text) {
        TextView txtAlbum = (TextView) findViewById(R.id.txtAlbum);
        txtAlbum.setText(text);
    }

    public void setTxtTrack(String text) {
        TextView txtTrack = (TextView) findViewById(R.id.txtTrack);
        txtTrack.setText(text);
    }

    public void setMusicStopped() {
        TextView t = (TextView) findViewById(R.id.txtMusicPlayer);
        t.setText("Music player stopped");

        setPlayPauseText("Play");

        findViewById(R.id.lyMusicInfo).setVisibility(View.GONE);
    }

    public void setMusicPlaying(String artist, String album, String track) {
        TextView t = (TextView) findViewById(R.id.txtMusicPlayer);
        t.setText("Now playing");
        setTxtArtist(artist);
        setTxtAlbum(album);
        setTxtTrack(track);

        setPlayPauseText("Pause");

        findViewById(R.id.lyMusicInfo).setVisibility(View.VISIBLE);
    }

    public void setPlayPauseText(String text) {
        Button b = (Button) findViewById(R.id.btPlayPause);
        b.setText(text);
    }



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

    private void launchAbout() {
        Log.v("vaviacar", "lauch about start");
        Intent i = new Intent(this, About.class);
        startActivity(i);
        Log.v("vaviacar", "lauch about end");
    }

}
