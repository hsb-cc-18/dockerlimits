package com.cloudcomputing.docker.limits.services.stats;

import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.core.async.ResultCallbackTemplate;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;

class StatsCallback extends ResultCallbackTemplate<StatsCallback, Statistics> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private final List<Statistics> statistics = Lists.newArrayList();
    protected final CountDownLatch countDownLatch;

    private Boolean gotStats = false;

    public StatsCallback(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void onNext(Statistics stats) {

        logger.debug("Received stats #{} : {}", countDownLatch.getCount(), stats);

        if (stats != null) {
            gotStats = true;
            statistics.add(stats);
        }

        countDownLatch.countDown();
    }

    public Boolean gotStats() {
        return gotStats;
    }

    public List<Statistics> getStatistics() {
        return statistics;
    }
}
