package com.iceblock.springboot.quartz.factory;


import com.iceblock.springboot.quartz.model.CronTriggerModel;
import com.iceblock.springboot.quartz.model.SimpleTriggerModel;
import com.iceblock.springboot.quartz.model.TriggerModel;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import java.text.ParseException;
import java.util.Date;

/**
 * Job Trigger Factory
 *
 * @author yan.liang@ustcinfo.com
 * @date 2017/7/17
 */
public class TriggerFactory {

    private TriggerFactory() {
    }

    public static Trigger createTrigger(TriggerModel triggerModel, JobDetail jobDetail) throws ParseException {
        if (TriggerModel.CRON_TRIGGER_TYPE.equals(triggerModel)) {
            return createCronTrigger((CronTriggerModel) triggerModel, jobDetail);
        } else {
            return createSimpleTrigger((SimpleTriggerModel) triggerModel, jobDetail);
        }
    }

    public static SimpleTrigger createSimpleTrigger(SimpleTriggerModel simpleTriggerModel, JobDetail jobDetail) throws ParseException {
        Date startTime = simpleTriggerModel.getStartTime();
        Date endTime = simpleTriggerModel.getEndTime();
        Long repeatInterval = simpleTriggerModel.getRepeatInterval();
        Long repeatCount = simpleTriggerModel.getRepeatCount();
        SimpleTriggerImpl simpleTrigger = new SimpleTriggerImpl();
        simpleTrigger.setName(simpleTriggerModel.getTriggerName());
        simpleTrigger.setGroup(Scheduler.DEFAULT_GROUP);
        simpleTrigger.setJobKey(jobDetail.getKey());
        Date now = new Date();
        if (startTime != null && startTime.getTime() > now.getTime()) {
            simpleTrigger.setStartTime(startTime);
        } else {
            simpleTrigger.setStartTime(new Date(System.currentTimeMillis() + 10 * 1000));
        }
        simpleTrigger.setEndTime(endTime);
        simpleTrigger.setRepeatInterval(repeatInterval * 1000);
        Long repeat = Long.MAX_VALUE;
        if (repeatCount != null && repeatCount > 0) {
            repeat = repeatCount - 1L;
        }
        simpleTrigger.setRepeatCount(repeat.intValue());
        simpleTrigger.setMisfireInstruction(SimpleTriggerImpl.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);//忽略已错过触发时间的任务,
        return simpleTrigger;
    }

    /**
     * 创建 Trigger
     */
    public static CronTriggerImpl createCronTrigger(CronTriggerModel cronTriggerModel, JobDetail jobDetail) throws ParseException {
        Date startTime = cronTriggerModel.getStartTime();
        Date endTime = cronTriggerModel.getEndTime();
        String cronExpression = cronTriggerModel.getCronExpression();
        CronTriggerImpl cronTrigger = new CronTriggerImpl();
        cronTrigger.setName(cronTriggerModel.getTriggerName());
        cronTrigger.setGroup(Scheduler.DEFAULT_GROUP);
        cronTrigger.setJobKey(jobDetail.getKey());
        //cronTrigger.setMisfireInstruction(Trigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        cronTrigger.setMisfireInstruction(CronTriggerImpl.MISFIRE_INSTRUCTION_DO_NOTHING);
        Date now = new Date();
        if (startTime != null && startTime.getTime() > now.getTime()) {
            cronTrigger.setStartTime(startTime);
        } else {
            cronTrigger.setStartTime(new Date(System.currentTimeMillis() + 1 * 10 * 1000));
        }
        cronTrigger.setEndTime(endTime);
        cronTrigger.setCronExpression(cronExpression);
        return cronTrigger;
    }

}
