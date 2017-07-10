package com.threadmanager.sort;

import com.threadmanager.data.BaseData;

import java.util.List;

class CocktailSort extends BaseSortedClass {

    @Override
    public <T extends BaseData> void sort(List<T> data) {
        boolean swapped = true;
        int i = 0;
        int j = data.size() - 1;
        while (i < j && swapped) {
            swapped = false;
            for (int k = i; k < j; k++) {
                if (data.get(k).getName().compareToIgnoreCase(data.get(k + 1).getName()) > 0) {
                    T temp = data.get(k);
                    data.set(k, data.get(k + 1));
                    data.set(k + 1, temp);
                    swapped = true;
                }
            }
            j--;
            if (swapped) {
                swapped = false;
                for (int k = j; k > i; k--) {
                    if (data.get(k).getName().compareToIgnoreCase(data.get(k - 1).getName()) < 0) {
                        T temp = data.get(k);
                        data.set(k, data.get(k - 1));
                        data.set(k - 1, temp);
                        swapped = true;
                    }
                }
            }
            i++;
        }
    }
}
