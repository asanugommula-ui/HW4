package spendreport;

import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DetailedAlertSink implements SinkFunction<DetailedAlert> {

    private static final Logger LOG = LoggerFactory.getLogger(DetailedAlertSink.class);

    @Override
    public void invoke(DetailedAlert value, Context context) {
        LOG.info(value.toString());
    }
}