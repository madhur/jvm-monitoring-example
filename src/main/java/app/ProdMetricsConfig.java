package app;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ProdMetricsConfig extends MetricsConfig {

    private static final String GRAPHITE_PREFIX =
            "collectd/graphite-monitoring-example/production";


    @Override
    protected void configureReporters() {
        configureReporters(GRAPHITE_PREFIX);
    }

    @PostConstruct()
    public void init() {
        configureReporters();
    }
}
