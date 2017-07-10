package com.threadmanager.sort;

import com.threadmanager.data.BaseData;

import java.util.List;

abstract class BaseSortedClass {

    public abstract <T extends BaseData> void sort(List<T> data);
}
