package spendreport;

import org.apache.flink.annotation.PublicEvolving;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PublicEvolving
@SuppressWarnings("unused")
public class DetailedAlertSink implements SinkFunction<DetailedAlert> {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(DetailedAlertSink.class);

    // Modified to accept DetailedAlert instead of Alert for richer logging information
    @Override
    public void invoke(DetailedAlert value, Context context) {
        LOG.info(value.toString());
    }
}
