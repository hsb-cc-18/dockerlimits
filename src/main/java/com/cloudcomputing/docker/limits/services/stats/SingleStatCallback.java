package com.cloudcomputing.docker.limits.services.stats;

import com.github.dockerjava.api.model.Statistics;
import com.google.common.collect.Iterables;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class SingleStatCallback extends StatsCallback {

    public SingleStatCallback() {
        super(new CountDownLatch(1));
    }

    public Optional<Statistics> getLatestStatsWithTimeout(int timeout) throws InterruptedException {

        final boolean await = countDownLatch.await(timeout, TimeUnit.SECONDS);
        if(!await) {
            timeout(timeout);
        }
        if(!gotStats()) {
            return noResult();
        }
        return latestStat();
    }

    private void timeout(int timeout) {
        throw new IllegalStateException("Did not get stats within " + timeout + "s");
    }

    private Optional<Statistics> noResult() {
        return Optional.empty();
    }

    private Optional<Statistics> latestStat() {
        return Optional.ofNullable(Iterables.getLast(getStatistics()));
    }
}
