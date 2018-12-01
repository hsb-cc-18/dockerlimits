package com.cloudcomputing.docker.limits.services.stats;

import com.github.dockerjava.api.model.Statistics;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class SingleStatCallback extends StatsCallback {

    public SingleStatCallback() {
        super(new CountDownLatch(1));
    }

    public Optional<Statistics> getLatestStatsWithTimeout(int timeout) throws InterruptedException {

        countDownLatch.await(timeout, TimeUnit.SECONDS);
        if(!gotStats()) {
            return Optional.empty();
        }
        return Optional.ofNullable(getStatistics().get(Math.max(0, getStatistics().size() - 1)));
    }
}
