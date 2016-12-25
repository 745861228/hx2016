package com.bawei.hx2016.adapater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bawei.hx2016.R;
import com.bawei.hx2016.holder.ChatRecyclerViewHolder;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :   郗琛
 * @date :   2016/12/24
 */

public class ChatRecyalerViewAdapater extends RecyclerView.Adapter<ChatRecyclerViewHolder> {

    private Context context;
    private ArrayList<EMMessage> arrayList;

    public ChatRecyalerViewAdapater(Context context, List<EMMessage> arrayList) {
        this.context = context;
        this.arrayList = (ArrayList<EMMessage>) arrayList;
    }

    @Override
    public ChatRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.chatrecyclerview_item, null);
        ChatRecyclerViewHolder holder = new ChatRecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ChatRecyclerViewHolder holder, int position) {
        holder.chat_recyclerView_tv.setText(arrayList.get(position).getFrom() + " ： " + arrayList.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
