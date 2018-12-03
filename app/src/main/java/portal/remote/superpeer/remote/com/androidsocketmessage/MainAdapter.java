package portal.remote.superpeer.remote.com.androidsocketmessage;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import portal.remote.superpeer.remote.com.androidsocketmessage.model.SendMessage;

/**
 * * ============================================================================
 * * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * * Unauthorized copying of this file, via any medium is strictly prohibited
 * * Proprietary and confidential
 * * ----------------------------------------------------------------------------
 * * Created by: Mimo Saha on [03-Dec-2018 at 1:59 PM].
 * * Email: mimosaha@w3engineers.com
 * * ----------------------------------------------------------------------------
 * * Project: AndroidSocketMessage.
 * * Code Responsibility: <Purpose of code>
 * * ----------------------------------------------------------------------------
 * * Edited by :
 * * --> <First Editor> on [03-Dec-2018 at 1:59 PM].
 * * --> <Second Editor> on [03-Dec-2018 at 1:59 PM].
 * * ----------------------------------------------------------------------------
 * * Reviewed by :
 * * --> <First Reviewer> on [03-Dec-2018 at 1:59 PM].
 * * --> <Second Reviewer> on [03-Dec-2018 at 1:59 PM].
 * * ============================================================================
 **/
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SendMessage> messages;
    public static final int MESSAGE_IN = 1, MESSAGE_OUT = 2;

    public MainAdapter(List<SendMessage> messages) {
        this.messages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        switch (messages.get(position).getMessageType()) {
            case MESSAGE_IN:
                return MESSAGE_IN;
            case MESSAGE_OUT:
                return MESSAGE_OUT;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case MESSAGE_IN:
                return new MessageInViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.message_in, viewGroup, false));

            case MESSAGE_OUT:
                return new MessageOutViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.message_out, viewGroup, false));

            default:
                return new MessageOutViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.message_out, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        SendMessage sendMessage = messages.get(i);

        switch (viewHolder.getItemViewType()) {
            case MESSAGE_IN:
                MessageInViewHolder messageInViewHolder = (MessageInViewHolder) viewHolder;
                messageInViewHolder.messageShow.setText(sendMessage.getListMessage());
                break;

            case MESSAGE_OUT:
                MessageOutViewHolder messageOutViewHolder = (MessageOutViewHolder) viewHolder;
                messageOutViewHolder.messageShow.setText(sendMessage.getListMessage());
                break;
        }
    }

    public void addItem(SendMessage sendMessage) {
        messages.add(0, sendMessage);
        notifyItemInserted(0);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    private class MessageInViewHolder extends RecyclerView.ViewHolder {

        public TextView messageShow;

        public MessageInViewHolder(@NonNull View itemView) {
            super(itemView);

            messageShow = itemView.findViewById(R.id.message_show);
        }
    }

    private class MessageOutViewHolder extends RecyclerView.ViewHolder {

        public TextView messageShow;

        public MessageOutViewHolder(@NonNull View itemView) {
            super(itemView);

            messageShow = itemView.findViewById(R.id.message_show);
        }
    }

}
