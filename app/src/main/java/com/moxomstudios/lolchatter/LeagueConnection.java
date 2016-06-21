package com.moxomstudios.lolchatter;

import android.util.Log;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.parsing.ParsingExceptionCallback;
import org.jivesoftware.smack.parsing.UnparsablePacket;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.util.TLSUtils;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;


/**
 * Created by mox on 6/9/2016.
 */
public class LeagueConnection {


    XMPPTCPConnection connection;
    //TODO PUT INTO FILE
    private String PARAM_HOST = "";
    private int    PARAM_PORT = 0;
    private String PARAM_SERVICE = "";
    private String PARAM_RESOURCE = "";
    //TODO DYNAMIC USER/PASS
    private String PARAM_USERNAME = "";
    private String PARAM_PASSWORD = "";

    public LeagueConnection()
    {

    }



    protected boolean loginRiot(String mUsername, String mPassword)
    {

        XMPPTCPConnectionConfiguration.Builder conf = XMPPTCPConnectionConfiguration.builder();

        SmackConfiguration.setDefaultParsingExceptionCallback(new ParsingExceptionCallback() {
            @Override
            public void handleUnparsablePacket(UnparsablePacket stanzaData) throws Exception {
                Log.i("HANDLE", stanzaData.toString());
            }
        });



        conf.setHost(PARAM_HOST);
        conf.setPort(PARAM_PORT);
        conf.setServiceName(PARAM_SERVICE);
        conf.setDebuggerEnabled(true); // with true is crashing
        conf.setUsernameAndPassword(PARAM_USERNAME, "AIR_" + PARAM_PASSWORD);
        conf.setResource(PARAM_RESOURCE);
        conf.setSocketFactory(SSLSocketFactory.getDefault());
        conf.setSendPresence(true);


        conf.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);

        //LegacySSL
        try {
            TLSUtils.acceptAllCertificates(conf);
        } catch (NoSuchAlgorithmException e) {
        } catch (KeyManagementException e) {
        }

        // verify all hostname - just for testing
        conf.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });


       connection = new XMPPTCPConnection(conf.build());

        try {
            connection.connect();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        }

        try {
            connection.login(); // throw exception SASLError using DIGEST-MD5: not-authorized

        } catch (XMPPException e) {
            e.printStackTrace();
            return false;
        } catch (SmackException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return true;

        // TODO: register the new account here.
    }

    public Roster getContacts()
    {
        Roster roster = Roster.getInstanceFor(connection);
        //Collection<RosterEntry> entries = roster.getEntries();
      //  Presence presence;

        return roster;
    }

    public void rosterOnlineStatus(){
        Roster roster = Roster.getInstanceFor(connection);
        Presence status = new Presence(Presence.Type.available);
        status.setStatus("lel");
        roster.addRosterListener(new RosterListener() {

            @Override
            public void presenceChanged(Presence presence) {

                Log.i("LOL", presence.getFrom() + "is " + presence + " " + presence.getStatus());
                presence.getStatus();


            }

            @Override
            public void entriesUpdated(Collection<String> addresses) {}

            @Override
            public void entriesDeleted(Collection<String> addresses) {}

            @Override
            public void entriesAdded(Collection<String> addresses) {}
        });
    }
}
