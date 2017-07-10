package com.threadmanager.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import com.threadmanager.ActMain;
import com.threadmanager.R;
import com.threadmanager.data.BaseData;
import com.threadmanager.sort.SortList;
import com.threadmanager.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class SortService<T extends BaseData> extends Service {
    private static final int MAX_POOL_SIZE = 3;
    private IBinder binder = new SortServiceBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void init(ActMain actMain, SparseArray<SortList> toSort) {
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(MAX_POOL_SIZE, MAX_POOL_SIZE, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3), threadFactory);
        List<Future<SortList<T>>> future = new ArrayList<>();
        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            final SortList<T> sortList = new SortList<>();
            sortList.addAll(toSort.get(i));
            future.add(executorPool.submit(new Callable<SortList<T>>() {
                @Override
                public SortList<T> call() throws Exception {
                    sortList.sort();
                    sortList.setDate(Utils.getFormattedTime(System.currentTimeMillis()));
                    sortList.setState(getString(R.string.end_sort));
                    return sortList;
                }
            }));
        }
        for (Future<SortList<T>> result : future) {
            try {
                actMain.notifyActivity(result.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorPool.shutdown();
        actMain.taskCompleted();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

    public class SortServiceBinder extends Binder {
        public SortService getService() {
            return SortService.this;
        }
    }
}
