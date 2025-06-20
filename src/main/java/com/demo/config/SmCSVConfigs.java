package com.demo.config;


public class SmCSVConfigs {

    private String id;
    private String state;
    private String event;
    private String nextState;
    private String action;

    public SmCSVConfigs() {
        super();
    }

    public SmCSVConfigs(String id, String state, String event, String nextState, String action) {
        super();
        this.id = id;
        this.state = state;
        this.event = event;
        this.nextState = nextState;
        this.action = action;        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getNextState() {
        return nextState;
    }

    public void setNextState(String nextState) {
        this.nextState = nextState;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }   
}
