package com.moxomstudios.lolchatter;

import java.util.Comparator;

/**
 * Created by mox on 6/20/2016.
 */
public class LeagueContact {


    public String name;
    public String status;

    public LeagueContact( String cName, String cStatus) {
        name = cName;
        status = cStatus;

    }
}


class LeagueNameComparator implements Comparator<LeagueContact> {
    public int compare(LeagueContact contact1, LeagueContact contact2) {
        return contact1.name.compareTo(contact2.name);
    }
}

class LeagueStatusComparator implements Comparator<LeagueContact> {
    public int compare(LeagueContact contact1, LeagueContact contact2) {

        if(contact1.status == null && contact2.status == null)
        {
            return 0;
        }
        else if(contact1.status == null && contact2.status != null)
        {
            return -1;
        }
        else if(contact1.status != null && contact2.status == null)
        {
            return 1;
        }


        return contact1.status.compareTo(contact2.status);
    }
}
