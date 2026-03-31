package spendreport;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class DetailedFraudDetectionJob {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Changed from TransactionSource to DetailedTransactionSource
        // DetailedTransactionSource generates DetailedTransactions with zip code information
        // instead of using static iterator-based Transaction generation
        DataStream<DetailedTransaction> transactions = env
                .addSource(new DetailedTransactionSource())
                .name("transactions");

        // Changed from DataStream<Alert> to DataStream<DetailedAlert>
        // DetailedAlert includes accountId, timestamp, zipCode, and amount 
        DataStream<DetailedAlert> alerts = transactions
                .keyBy(DetailedTransaction::getAccountId)
                // Changed from FraudDetector to DetailedFraudDetector
                // DetailedFraudDetector validates zip code matching in addition to amount patterns
                .process(new DetailedFraudDetector())
                .name("fraud-detector");

        // Changed from AlertSink to DetailedAlertSink
        // DetailedAlertSink handles DetailedAlert objects with enhanced logging
        alerts.addSink(new DetailedAlertSink())
                .name("send-alerts");

        env.execute("Fraud Detection");
    }
}
