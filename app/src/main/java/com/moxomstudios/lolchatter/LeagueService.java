package com.moxomstudios.lolchatter;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.jivesoftware.smack.roster.Roster;

import java.util.Random;

public class LeagueService extends Service {
    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    // Random number generator
    private final Random mGenerator = new Random();

    private int set_num = -1;
    private LeagueConnection league_client = new LeagueConnection();
    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        LeagueService getService() {
            // Return this instance of LocalService so clients can call public methods
            return LeagueService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /** method for clients */
    public int getRandomNumber() {
        if(set_num < 0)
        {
            set_num = mGenerator.nextInt(100);;
            Log.i("LOLSERVICE", "SetNum to: " + set_num);
        }
        else
        {
            Log.i("LOLSERVICE", "SetNumAlready set to " + set_num);
        }

        return mGenerator.nextInt(100);
    }

    public Roster getContacts()
    {
        return league_client.getContacts();
    }
    public boolean login(String username, String password)
    {
       return league_client.loginRiot(username,password);
    }
}
