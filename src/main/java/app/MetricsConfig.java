package app;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public abstract class MetricsConfig extends MetricsConfigurerAdapter{

    @Autowired
    private MetricRegistry metrics;

    Meter publishMeter;
    Counter publishCounter;
    Timer publishTimer ;
    Meter secondTimer;

    @Value("${graphite.host}")
    private String graphiteHost;

    @Value("${graphite.port}")
    private int graphitePort;

    @Value("${graphite.amount.of.time.between.polls}")
    private long graphiteAmountOfTimeBetweenPolls;

    @Value("${graphite.prefix}")
    private String graphitePrefix;

    @PostConstruct
    public void run() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);

    }


    protected void configureReporters() {
        configureReporters(metrics);
    }


    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        registerReporter(JmxReporter.forRegistry(metricRegistry)
                .build()).start();
        GraphiteReporter graphiteReporter =
                getGraphiteReporterBuilder(metricRegistry)
                        .build(getGraphite());
        registerReporter(graphiteReporter);
        graphiteReporter.start(graphiteAmountOfTimeBetweenPolls,
                TimeUnit.MILLISECONDS);
    }

    private GraphiteReporter.Builder getGraphiteReporterBuilder(MetricRegistry
                                                       metricRegistry) {
        publishTimer = metricRegistry.timer("publish.timer");
        publishCounter = metricRegistry.counter("publish.counter");
        publishMeter = metricRegistry.meter("publish.meter");
        secondTimer = metricRegistry.meter("publish.second.meter");
        metricRegistry.register("gc", new GarbageCollectorMetricSet());
        metricRegistry.register("memory", new MemoryUsageGaugeSet());
        metricRegistry.register("threads", new ThreadStatesGaugeSet());
        return GraphiteReporter.forRegistry(metricRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .prefixedWith(graphitePrefix);
    }

    private Graphite getGraphite() {
        return new Graphite(new InetSocketAddress(graphiteHost,
                graphitePort));
    }
}
