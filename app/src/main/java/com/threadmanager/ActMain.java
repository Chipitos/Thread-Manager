package com.threadmanager;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;

import com.threadmanager.adapters.SortResultAdapter;
import com.threadmanager.data.BaseData;
import com.threadmanager.data.Car;
import com.threadmanager.data.Plane;
import com.threadmanager.data.Ship;
import com.threadmanager.services.SortService;
import com.threadmanager.sort.SortList;

public class ActMain extends AppCompatActivity {
    private static final int SORT_TIMEOUT = 5000;

    private SortService sortService;
    private boolean isRun;
    private SparseArray<SortList> toSort = new SparseArray<>();
    private SortResultAdapter adapter;
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            SortService.SortServiceBinder binder = (SortService.SortServiceBinder) service;
            sortService = binder.getService();
            sortService.init(ActMain.this, toSort);
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            isRun = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        initData();
        initRecycler();
        Intent serviceIntent = new Intent(this, SortService.class);
        bindService(serviceIntent, connection, BIND_AUTO_CREATE);
        isRun = true;
    }

    public <T extends BaseData> void notifyActivity(SortList<T> result) {
        adapter.addItem(result);
    }

    public void taskCompleted() {
        initData();
        for (int i = 0; i < toSort.size(); i++) {
            adapter.addItem(toSort.get(i));
        }
        if (isRun) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sortService.init(ActMain.this, toSort);
                }
            }, SORT_TIMEOUT);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isRun) {
            isRun = false;
            unbindService(connection);
        }
    }

    private void initData() {
        SortList<BaseData> shipList = new SortList<>();
        SortList<BaseData> carList = new SortList<>();
        SortList<BaseData> planeList = new SortList<>();
        String[] cars = App.getContext().getResources().getStringArray(R.array.cars_array);
        for (String car : cars) {
            carList.add(new Car(car));
        }
        String[] ships = App.getContext().getResources().getStringArray(R.array.ships_array);
        for (String ship : ships) {
            shipList.add(new Ship(ship));
        }
        String[] planes = App.getContext().getResources().getStringArray(R.array.planes_array);
        for (String plane : planes) {
            planeList.add(new Plane(plane));
        }
        toSort.append(0, carList);
        toSort.append(1, shipList);
        toSort.append(2, planeList);
    }

    private void initRecycler() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_results);
        recyclerView.setLayoutManager(new GridLayoutManager(App.getContext(), 3));
        if (adapter == null)
            adapter = new SortResultAdapter(toSort);
        recyclerView.setAdapter(adapter);
    }
}
