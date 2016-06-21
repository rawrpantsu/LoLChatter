package com.moxomstudios.lolchatter;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;

import java.util.Collection;

public class ContactListActivity extends AppCompatActivity {

    LeagueService mService;
    boolean mBound = false;



    private ListView listActivity;
    private ContactListAdapter contactListAdapter;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Log.i("LOL","sort by name");
                contactListAdapter.sortName();

                contactListAdapter.notifyDataSetChanged();


            }
        });

        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("LOL","sort by status");
                contactListAdapter.sortStatus();

                contactListAdapter.notifyDataSetChanged();


            }
        });


        listActivity = (ListView) findViewById(R.id.listView);
        contactListAdapter = new ContactListAdapter(getApplicationContext(), R.layout.activity_contact_list);
        listActivity.setAdapter(contactListAdapter);








    }



    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, LeagueService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
      //      unbindService(mConnection);
        //    mBound = false;



        }
    }
    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LeagueService.LocalBinder binder = (LeagueService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;

            Roster roster = mService.getContacts();
            Collection<RosterEntry> entries = roster.getEntries();
            Presence presence;


            Log.i("LOL", "GET;" + roster.getGroupCount());
            for (RosterEntry entry : entries) {
                presence = roster.getPresence(entry.getUser());

                String status = presence.getStatus();
                contactListAdapter.add(new LeagueContact(entry.getName(), ((status != null)? status : "offline")));

                contactListAdapter.sortName();


                contactListAdapter.notifyDataSetChanged();
                    /*
                    Log.i("LOL", "USER: " + entry.getUser());
                    Log.i("LOL", "name: " + presence.getType().name());
                    Log.i("LOL", "status: " + presence.getStatus());
                    Log.i("LOL", "name2: " + entry.getName());
                    */

            }

            listActivity.setAdapter(contactListAdapter);
            contactListAdapter.notifyDataSetChanged();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

}
