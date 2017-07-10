package com.threadmanager.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.threadmanager.R;
import com.threadmanager.data.BaseData;
import com.threadmanager.sort.SortList;

public class SortResultAdapter extends RecyclerView.Adapter<SortResultAdapter.SortResultViewHolder> {
    private SparseArray<SortList> list = new SparseArray<>();

    public SortResultAdapter(SparseArray<SortList> list) {
        this.list = list.clone();
    }

    @Override
    public SortResultAdapter.SortResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sort_result, parent, false);
        return new SortResultViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SortResultViewHolder holder, int position) {
        holder.tvState.setText(list.get(position).getState());
        holder.tvListName.setText(list.get(position).getListName());
        holder.tvDate.setText(list.get(position).getDate());
        holder.tvMethod.setText(list.get(position).getSortType());
        String result = "";
        SortList<BaseData> data = list.get(position);
        for (int i = 0; i < data.size(); i++) {
            result += "\t" + data.get(i).getName() + "\n";
        }
        holder.tvResult.setText(result);
    }

    public void addItem(SortList sortList) {
        list.put(list.size(), sortList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SortResultViewHolder extends RecyclerView.ViewHolder {
        private TextView tvState, tvDate, tvListName, tvResult, tvMethod;

        SortResultViewHolder(View itemView) {
            super(itemView);
            tvState = (TextView) itemView.findViewById(R.id.tv_state);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvListName = (TextView) itemView.findViewById(R.id.tv_list_name);
            tvResult = (TextView) itemView.findViewById(R.id.tv_list_container);
            tvMethod = (TextView) itemView.findViewById(R.id.tv_method);
        }
    }
}
