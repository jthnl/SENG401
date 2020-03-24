package com.nhl.view;

public class MessageView {
    public boolean errFlag;
    public String errMessage;
    public boolean msgFlag;
    public String normalMessage;
    public MsgObjectView object;

    public MessageView(boolean errFlag, String errMessage, boolean msgFlag, String normalMessage, MsgObjectView object){
        this.errFlag = errFlag;
        this.errMessage = errMessage;
        this.msgFlag = msgFlag;
        this.normalMessage = normalMessage;
        this.object = object;
    }

}
