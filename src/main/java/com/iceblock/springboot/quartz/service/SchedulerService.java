package com.iceblock.springboot.quartz.service;

import com.iceblock.springboot.quartz.factory.TriggerFactory;
import com.iceblock.springboot.quartz.model.JobDto;
import com.iceblock.springboot.quartz.model.TriggerModel;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定时任务
 *
 * @author yan.liang@ustcinfo.com
 * @date 2017/7/17
 */
@Service
public class SchedulerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerService.class);

    @Autowired
    private Scheduler scheduler;

    public List<JobDto> listJobDto() throws SchedulerException {
        List<JobDto> jobDtoList = new ArrayList<>();
        for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(Scheduler.DEFAULT_GROUP))) {

            String jobName = jobKey.getName();
            String jobGroup = jobKey.getGroup();

            //get job's trigger
            List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
            if (triggers != null && triggers.size() > 0) {
                Date previousFireTime = triggers.get(0).getPreviousFireTime();
                Date nextFireTime = triggers.get(0).getNextFireTime();
                JobDto jobDto = new JobDto();
                jobDto.setJobGroup(jobGroup);
                jobDto.setJobName(jobName);
                jobDto.setNextFireTime(nextFireTime);
                jobDto.setPreviousFireTime(previousFireTime);
                jobDtoList.add(jobDto);
            }

        }
        return jobDtoList;
    }

    public JobDto getJobDto(String name) throws SchedulerException {
        List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(new JobKey(name, Scheduler.DEFAULT_GROUP));
        if (triggers != null && triggers.size() > 0) {
            Date previousFireTime = triggers.get(0).getPreviousFireTime();
            Date nextFireTime = triggers.get(0).getNextFireTime();
            JobDto jobDto = new JobDto();
            jobDto.setJobGroup(Scheduler.DEFAULT_GROUP);
            jobDto.setJobName(name);
            jobDto.setNextFireTime(nextFireTime);
            jobDto.setPreviousFireTime(previousFireTime);
            return jobDto;
        } else {
            return null;
        }
    }

    public Trigger.TriggerState getTriggerState(String name) throws SchedulerException {
        return scheduler.getTriggerState(new TriggerKey(name, Scheduler.DEFAULT_GROUP));
    }

    /**
     * 创建定时任务
     */
    public void createScheduler(JobDetail jobDetail, TriggerModel triggerModel) throws SchedulerException, ParseException {
        Trigger.TriggerState triggerState = getTriggerState(jobDetail.getKey().getName());
        Trigger trigger = TriggerFactory.createTrigger(triggerModel, jobDetail);
        if (triggerState.equals(Trigger.TriggerState.NORMAL)) {
            LOGGER.info("{} has create yet", jobDetail.getKey().getName());
        } else if (triggerState.equals(Trigger.TriggerState.PAUSED)) {
            scheduler.resumeJob(jobDetail.getKey());
        } else {
            scheduler.deleteJob(jobDetail.getKey());
            scheduler.scheduleJob(jobDetail, trigger);
            LOGGER.info("create scheduler {}!", jobDetail);
        }
    }

    /**
     * 删除定时任务
     */
    public Boolean removeScheduler(String name) throws SchedulerException {
        return scheduler.unscheduleJob(new TriggerKey(name, Scheduler.DEFAULT_GROUP));
    }
}
