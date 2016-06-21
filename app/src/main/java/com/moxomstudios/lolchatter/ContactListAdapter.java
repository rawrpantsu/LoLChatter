package com.moxomstudios.lolchatter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mox on 6/20/2016.
 */

class ContactListAdapter extends ArrayAdapter<LeagueContact> {
    private TextView nameText;
    private TextView statusText;
    private List<LeagueContact> leagueContactList = new ArrayList<LeagueContact>();
    private Context context;

    @Override
    public void add(LeagueContact object) {
        leagueContactList.add(object);
        super.add(object);
    }

    public ContactListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    public int getCount() {
        return this.leagueContactList.size();
    }

    public LeagueContact getItem(int index) {
        return this.leagueContactList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LeagueContact contactObj = getItem(position);
        View row = convertView;


        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        row = inflater.inflate(R.layout.league_contact_row, parent, false);

        nameText = (TextView) row.findViewById(R.id.textViewContactName);
        nameText.setText(contactObj.name);

        statusText = (TextView) row.findViewById(R.id.textViewContactStatus);
        statusText.setText(contactObj.status);
        return row;

    }

    public void sortName()
    {
        Collections.sort(leagueContactList, new LeagueNameComparator());
    }
    public void sortStatus()
    {
        Collections.sort(leagueContactList, new LeagueStatusComparator());
    }
}