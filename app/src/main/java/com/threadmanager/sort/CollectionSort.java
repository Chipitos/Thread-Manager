package com.threadmanager.sort;

import com.threadmanager.data.BaseData;

import java.util.Collections;
import java.util.List;

class CollectionSort extends BaseSortedClass {

    @Override
    public <T extends BaseData> void sort(List<T> data) {
        Collections.sort(data);
    }
}
