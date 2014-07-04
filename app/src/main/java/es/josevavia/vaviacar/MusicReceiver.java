package es.josevavia.vaviacar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MusicReceiver extends BroadcastReceiver {

    public MusicReceiver() {
        Log.v("vaviacar", "MusicReceiver start");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("vaviacar", "onReceive start");
        setMusic(context, intent);
        Log.v("vaviacar", "onReceive end");
    }

    private void setMusic(Context context, Intent intent) {

        String action = intent.getAction();
        Log.v("vaviacar", "action: " + action);

        if (action.equals("com.spotify.mobile.android.metadatachanged")) {

            Intent i = new Intent();
            i.setAction("es.josevavia.vaviacar.MUSIC_PLAYER");
            i.putExtra("playing", true);
            i.putExtra("artist", intent.getStringExtra("artist"));
            i.putExtra("album", intent.getStringExtra("album"));
            i.putExtra("track", intent.getStringExtra("track"));
            context.sendBroadcast(i);

        }

        if (action.equals("com.spotify.mobile.android.playbackstatechanged")) {

            boolean playing = intent.getBooleanExtra("playing", false);
            Intent i = new Intent();
            i.setAction("es.josevavia.vaviacar.MUSIC_PLAYER");
            i.putExtra("playing", playing);
            context.sendBroadcast(i);

        }

        for (String set : intent.getExtras().keySet()) {
            Log.v("vaviacar", set + ": " + intent.getStringExtra(set));
        }
    }
}
