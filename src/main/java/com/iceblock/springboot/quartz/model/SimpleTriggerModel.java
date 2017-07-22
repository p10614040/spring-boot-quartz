package com.iceblock.springboot.quartz.model;

public class SimpleTriggerModel extends TriggerModel {

    private Long repeatInterval;
    private Long repeatCount;

    public Long getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(Long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public Long getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(Long repeatCount) {
        this.repeatCount = repeatCount;
    }


    @Override
    protected String getTaskTriggerType() {
        return TriggerModel.SIMPLE_TRIGGER_TYPE;
    }

    @Override
    public String toString() {
        return "SimpleTriggerModel{" +
                "repeatInterval=" + repeatInterval +
                ", repeatCount=" + repeatCount +
                "} " + super.toString();
    }
}
