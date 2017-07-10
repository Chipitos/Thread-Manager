package com.threadmanager.sort;


import com.threadmanager.R;
import com.threadmanager.data.BaseData;
import com.threadmanager.data.Car;
import com.threadmanager.data.Ship;
import com.threadmanager.utils.Utils;

import java.util.ArrayList;

public class SortList<T extends BaseData> extends ArrayList<T> {
    private String date = Utils.getFormattedTime(System.currentTimeMillis());
    private String state = Utils.getString(R.string.start_sort);

    public void sort() {
        if (this.size() == 0)
            return;
        BaseSortedClass sortedClass;
        if (this.get(0) instanceof Car) {
            sortedClass = new BubbleSort();
        } else if (this.get(0) instanceof Ship) {
            sortedClass = new CocktailSort();
        } else {
            sortedClass = new CollectionSort();
        }
        sortedClass.sort(this);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getListName() {
        if (this.size() == 0)
            return "";
        return this.get(0).getClass().getSimpleName();
    }

    public String getSortType() {
        if (this.size() == 0)
            return "";
        if (this.get(0) instanceof Car) {
            return Utils.getString(R.string.bubble);
        } else if (this.get(0) instanceof Ship) {
            return Utils.getString(R.string.cocktail);
        } else {
            return Utils.getString(R.string.collection_sort);
        }
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
