package com.example.firstapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapplication.R;
import com.example.firstapplication.entity.MsgEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
//https://blog.csdn.net/bskfnvjtlyzmv867/article/details/71308343
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<MsgEntity> msgList;


    public ChatAdapter(Context context, List<MsgEntity> msgList) {
        this.context = context;
        this.msgList = msgList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == MsgEntity.RECV_MSG){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chat_left,parent,false);
            return new LeftViewHolder(view);
        }else if(viewType == MsgEntity.SEND_MSG){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chat_right,parent,false);
            return new RightViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof LeftViewHolder){
            ((LeftViewHolder) holder).msg.setText(msgList.get(position).getContent());
        }else{
            ((RightViewHolder) holder).msg.setText(msgList.get(position).getContent());
        }


    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return msgList.get(position).getType();
    }

    static class LeftViewHolder extends RecyclerView.ViewHolder{
//        @BindView(R.id.chat_left_msg)
        public TextView msg;

        public LeftViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this,itemView);
            msg = itemView.findViewById(R.id.chat_left_msg);
        }
    }
    static class RightViewHolder extends RecyclerView.ViewHolder{
//        @BindView(R.id.chat_right_msg)
        public TextView msg;

        public RightViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this,itemView);
            msg = itemView.findViewById(R.id.chat_right_msg);
        }
    }
}
