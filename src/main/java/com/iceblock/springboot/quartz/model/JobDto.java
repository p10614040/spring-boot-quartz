package com.iceblock.springboot.quartz.model;

import java.util.Date;

/**
 * JOB DTO
 *
 * @author yan.liang@ustcinfo.com
 * @date 2017/7/17
 */
public class JobDto {

    private String jobName;
    private String jobGroup;
    private Date previousFireTime;
    private Date nextFireTime;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public Date getPreviousFireTime() {
        return previousFireTime;
    }

    public void setPreviousFireTime(Date previousFireTime) {
        this.previousFireTime = previousFireTime;
    }

    public Date getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    @Override
    public String toString() {
        return "JobDto{" +
                "jobName='" + jobName + '\'' +
                ", jobGroup='" + jobGroup + '\'' +
                ", previousFireTime=" + previousFireTime +
                ", nextFireTime=" + nextFireTime +
                '}';
    }
}
