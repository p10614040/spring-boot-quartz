package com.iceblock.springboot.quartz.controller;

import com.iceblock.springboot.quartz.service.SchedulerService;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 定时任务
 *
 * @author yan.liang@ustcinfo.com
 * @date 2017/7/17
 */
@RestController
@RequestMapping("/scheduler")
public class SchedulerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerController.class);

    @Autowired
    private SchedulerService schedulerService;

    @GetMapping(value = "/jobs")
    public ResponseEntity ListJobDto() {
        try {
            return new ResponseEntity<>(schedulerService.listJobDto(), HttpStatus.OK);
        } catch (SchedulerException e) {
            LOGGER.error("获取运行 JOB 异常", e);
            return new ResponseEntity<>("获取运行 JOB 异常", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping(value = "/jobs/{jobName}")
    public ResponseEntity getJobDto(@PathVariable("jobName") String jobName) {
        try {
            return new ResponseEntity<>(schedulerService.getJobDto(jobName), HttpStatus.OK);
        } catch (SchedulerException e) {
            LOGGER.error("获取运行 JOB 异常", e);
            return new ResponseEntity<>("获取运行 JOB 异常", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(value = "/jobs/{jobName}")
    public ResponseEntity createScheduler() {
        try {
            return new ResponseEntity<>(schedulerService.listJobDto(), HttpStatus.OK);
        } catch (SchedulerException e) {
            LOGGER.error("启动 JOB 异常", e);
            return new ResponseEntity<>("启动 JOB 异常", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping(value = "/jobs/{jobName}")
    public ResponseEntity removeScheduler() {
        try {
            return new ResponseEntity<>(schedulerService.listJobDto(), HttpStatus.OK);
        } catch (SchedulerException e) {
            LOGGER.error("停止 JOB 异常", e);
            return new ResponseEntity<>("停止 JOB 异常", HttpStatus.EXPECTATION_FAILED);
        }
    }
}
