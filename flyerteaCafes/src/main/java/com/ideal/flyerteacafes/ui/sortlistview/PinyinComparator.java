package com.ideal.flyerteacafes.ui.sortlistview;

import java.util.Comparator;

import com.ideal.flyerteacafes.model.entity.FriendsInfo;


/**
 * @author xiaanming
 */
public class PinyinComparator implements Comparator<FriendsInfo> {

/*	public int compare(SortModel o1, SortModel o2) {
        if (o1.getPinyin().equals("@")
				|| o2.getPinyin().equals("#")) {
			return -1;
		} else if (o1.getPinyin().equals("#")
				|| o2.getPinyin().equals("@")) {
			return 1;
		} else {
			return o1.getPinyin().compareTo(o2.getPinyin());
		}
	}*/

    public int compare(FriendsInfo o1, FriendsInfo o2) {
        if (o1.getSortLetters().equals("@")
                || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")
                || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }

}
