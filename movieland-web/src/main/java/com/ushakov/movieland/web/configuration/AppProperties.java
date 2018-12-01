package com.ushakov.movieland.web.configuration;

import org.springframework.beans.factory.annotation.Value;

public class AppProperties {
    @Value("${my.job.executor.queue.capacity}")
    private int executorQueueCapacity;
    @Value("${my.job.executor.pool.size}")
    private int executorPoolSize;
    @Value("${my.job.scheduler.pool.size}")
    private int schedulerPoolSize;

    public int getExecutorQueueCapacity() {
        return executorQueueCapacity;
    }

    public int getExecutorPoolSize() {
        return executorPoolSize;
    }

    public int getSchedulerPoolSize() {
        return schedulerPoolSize;
    }
}
