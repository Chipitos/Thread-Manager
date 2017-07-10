package com.threadmanager.data;

import android.support.annotation.NonNull;

public abstract class BaseData implements Comparable<BaseData> {
    protected String name;

    public BaseData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(@NonNull BaseData data) {
        return name.compareToIgnoreCase(data.name);
    }
}
