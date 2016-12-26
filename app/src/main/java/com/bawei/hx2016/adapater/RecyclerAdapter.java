package com.bawei.hx2016.adapater;

/**
 * @author :   郗琛
 * @date :   2016/12/4
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.hx2016.holde.RecyclerHolder;
import com.bawei.hx2016.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 橘子桑 on 2016/1/2.
 */
public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerHolder> {

    private Context mContext;
    private List<T> mDatas;
    private int mLayoutId;
    private LayoutInflater mInflater;

    public RecyclerAdapter(Context mContext, List<T> mDatas, int mLayoutId) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mLayoutId = mLayoutId;
        mInflater = LayoutInflater.from(mContext);
    }


    public RecyclerAdapter(Context mContext, T[] mDatas, int mLayoutId) {
        this.mContext = mContext;
        this.mDatas = new ArrayList<>();
        for (int i = 0; i < mDatas.length; i++) {
            this.mDatas.add(mDatas[i]);
        }
        this.mLayoutId = mLayoutId;
        mInflater = LayoutInflater.from(mContext);
    }

    public RecyclerAdapter() {

    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //这里是创建ViewHolder的地方，RecyclerAdapter内部已经实现了ViewHolder的重用
        //这里我们直接new就好了
        return new RecyclerHolder(mInflater.inflate(mLayoutId, parent, false));
    }


    public void onBindViewHolder(RecyclerHolder holder, int position) {
        convert(holder, mDatas.get(position), position);
    }


    public abstract void convert(RecyclerHolder holder, T data, int position);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
