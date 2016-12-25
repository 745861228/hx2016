package com.bawei.hx2016.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bawei.hx2016.R;

/**
 * @author :   郗琛
 * @date :   2016/12/24
 */

public class ChatRecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView chat_recyclerView_tv;

    public ChatRecyclerViewHolder(View itemView) {
        super(itemView);
        chat_recyclerView_tv = (TextView) itemView.findViewById(R.id.chat_recyclerView_tv);

    }
}
