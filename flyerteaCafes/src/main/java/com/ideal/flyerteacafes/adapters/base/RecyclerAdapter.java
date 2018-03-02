package com.ideal.flyerteacafes.adapters.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by fly on 2015/11/18.
 */
public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    protected List<T> mDatas;
    private int layoutId;

    public RecyclerAdapter(List<T> datas, int layoutId) {
        this.mDatas = datas;
        this.layoutId = layoutId;
    }


    private OnClickListenerByRecycler onClickListenerByRecycler;
    private OnLongClickListenerByRecycler onLongClickListenerByRecycler;


    public interface OnClickListenerByRecycler {
        void onClickByRecycler(View v);
    }

    public interface OnLongClickListenerByRecycler {
        boolean OnLongClickByRecycler(View v);
    }

    public void setOnClickListenerByRecycler(OnClickListenerByRecycler l) {
        this.onClickListenerByRecycler = l;
    }

    public void setOnLongClickByRecycler(OnLongClickListenerByRecycler l) {
        this.onLongClickListenerByRecycler = l;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), layoutId, null);
        // 创建一个ViewHolder
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListenerByRecycler != null) {
                    onClickListenerByRecycler.onClickByRecycler(v);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onLongClickListenerByRecycler != null) {
                    return onLongClickListenerByRecycler.OnLongClickByRecycler(v);
                }
                return false;
            }
        });
        convert(holder, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    public abstract void convert(RecyclerViewHolder holder, T t);

}
