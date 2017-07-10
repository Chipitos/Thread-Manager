package com.threadmanager.sort;

import com.threadmanager.data.BaseData;

import java.util.List;

class BubbleSort extends BaseSortedClass {

    @Override
    public <T extends BaseData> void sort(List<T> data) {
        T temp;
        if (data.size() > 1) {
            for (int x = 0; x < data.size(); x++) {
                for (int i = 0; i < data.size() - x - 1; i++) {
                    if (data.get(i).getName().compareToIgnoreCase(data.get(i + 1).getName()) > 0) {
                        temp = data.get(i);
                        data.set(i, data.get(i + 1));
                        data.set(i + 1, temp);
                    }
                }
            }
        }
    }


}
