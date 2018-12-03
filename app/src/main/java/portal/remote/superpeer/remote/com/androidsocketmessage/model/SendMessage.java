package portal.remote.superpeer.remote.com.androidsocketmessage.model;

/**
 * * ============================================================================
 * * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * * Unauthorized copying of this file, via any medium is strictly prohibited
 * * Proprietary and confidential
 * * ----------------------------------------------------------------------------
 * * Created by: Mimo Saha on [03-Dec-2018 at 12:52 PM].
 * * Email: mimosaha@w3engineers.com
 * * ----------------------------------------------------------------------------
 * * Project: AndroidSocketMessage.
 * * Code Responsibility: <Purpose of code>
 * * ----------------------------------------------------------------------------
 * * Edited by :
 * * --> <First Editor> on [03-Dec-2018 at 12:52 PM].
 * * --> <Second Editor> on [03-Dec-2018 at 12:52 PM].
 * * ----------------------------------------------------------------------------
 * * Reviewed by :
 * * --> <First Reviewer> on [03-Dec-2018 at 12:52 PM].
 * * --> <Second Reviewer> on [03-Dec-2018 at 12:52 PM].
 * * ============================================================================
 **/
public class SendMessage {

    private long messageId;
    private String listMessage;
    private int messageType;

    public long getMessageId() {
        return messageId;
    }

    public SendMessage setMessageId(long messageId) {
        this.messageId = messageId;
        return this;
    }

    public String getListMessage() {
        return listMessage;
    }

    public SendMessage setListMessage(String listMessage) {
        this.listMessage = listMessage;
        return this;
    }

    public int getMessageType() {
        return messageType;
    }

    public SendMessage setMessageType(int messageType) {
        this.messageType = messageType;
        return this;
    }
}
