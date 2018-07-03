package com.criteo.nosql.mewpoke.memcached;

import com.criteo.nosql.mewpoke.config.Config;
import com.criteo.nosql.mewpoke.discovery.IDiscovery;

public class MemcachedRunnerInjector extends MemcachedRunnerAbstract {
    private static final long rangeLength = 1000000; // 1M
    private static int rangeBegin = 0;

    public MemcachedRunnerInjector(final Config cfg, final IDiscovery discovery) {
        super(cfg, discovery);
    }

    public void poke() {
        this.monitors.forEach((service, monitor) -> {
            monitor.ifPresent( mon -> mon.injectSet(rangeBegin * rangeLength, (rangeBegin * rangeLength) + rangeLength));
            monitor.ifPresent( mon -> mon.injectGet(rangeLength * 720, 720 * 2));

            rangeBegin = (rangeBegin + 1) % 720;
        });
    }
}
