package com.example.mywhatsapp.Models;

public class MessagesModel {

    String uId, message, messageId;
    Long timeStamp;

//    For Recycler View
    public MessagesModel(String uId, String message, Long timeStamp) {
        this.uId = uId;
        this.message = message;
        this.timeStamp = timeStamp;
    }

//    For last message.
    public MessagesModel(String uId, String message) {
        this.uId = uId;
        this.message = message;
    }

//    When using firebase we need an empty constructor always.
    public MessagesModel(){}

    public String getuId() {
        return uId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
