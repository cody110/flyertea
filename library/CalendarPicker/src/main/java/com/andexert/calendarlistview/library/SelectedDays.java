package com.andexert.calendarlistview.library;

import com.baoyz.pg.Parcelable;

/**
 * Created by fly on 2016/9/6.
 */
@Parcelable
public class SelectedDays<K> {

    private K first;
    private K last;

    public K getFirst()
    {
        return first;
    }

    public void setFirst(K first)
    {
        this.first = first;
    }

    public K getLast()
    {
        return last;
    }

    public void setLast(K last)
    {
        this.last = last;
    }

}
