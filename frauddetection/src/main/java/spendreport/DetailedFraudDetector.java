package spendreport;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

public class DetailedFraudDetector extends KeyedProcessFunction<Long, DetailedTransaction, DetailedAlert> {

    private static final long serialVersionUID = 1L;

    // SMALL_AMOUNT changed from 1.00 to 10.0 
    private static final double SMALL_AMOUNT = 10.0;
    private static final double LARGE_AMOUNT = 500.00;
    private static final long ONE_MINUTE = 60 * 1000;

    private transient ValueState<Boolean> flagState;
    private transient ValueState<Long> timerState;
    
    // Added state variable to track the zip code from the small transaction
    private transient ValueState<String> zipCodeState;

    @Override
    public void open(Configuration parameters) {
        ValueStateDescriptor<Boolean> flagDescriptor = new ValueStateDescriptor<>(
                "flag",
                Types.BOOLEAN);
        flagState = getRuntimeContext().getState(flagDescriptor);

        ValueStateDescriptor<Long> timerDescriptor = new ValueStateDescriptor<>(
                "timer-state",
                Types.LONG);
        timerState = getRuntimeContext().getState(timerDescriptor);

        // Initialize the zip code state descriptor to store the zip code
        // from the small transaction for comparison with the large transaction
        ValueStateDescriptor<String> zipCodeDescriptor = new ValueStateDescriptor<>(
                "zip-code",
                Types.STRING);
        zipCodeState = getRuntimeContext().getState(zipCodeDescriptor);
    }

    @Override
    public void processElement(
            // Changed parameter type from Transaction to DetailedTransaction
            // to access the new zipCode field
            DetailedTransaction detailedTransaction,
            Context context,
            // Changed return type from Alert to DetailedAlert
            Collector<DetailedAlert> collector) throws Exception {

        // Get the current state for the current key
        Boolean lastTransactionWasSmall = flagState.value();
        // Retrieve the zip code from the previous small transaction
        String lastZipCode = zipCodeState.value();

        // Check if the flag is set
        if (lastTransactionWasSmall != null) {
            // Added zip code validation to the alert condition
            // Now alert is only triggered if amount >= LARGE_AMOUNT AND 
            // the zip code matches the small transaction's zip code
            if (detailedTransaction.getAmount() >= LARGE_AMOUNT && lastZipCode != null
                    && lastZipCode.equals(detailedTransaction.getZipCode())) {
                // Output an alert downstream with detailed transaction information
                DetailedAlert alert = new DetailedAlert();
                alert.setAccountId(detailedTransaction.getAccountId());
                alert.setTimestamp(detailedTransaction.getTimestamp());
                // Set the zip code in the alert to document where the fraud occurred
                alert.setZipCode(detailedTransaction.getZipCode());
                alert.setAmount(detailedTransaction.getAmount());

                collector.collect(alert);
            }

            // Clean up our state
            cleanUp(context);
        }

        if (detailedTransaction.getAmount() < SMALL_AMOUNT) {
            // set the flag to true
            flagState.update(true);
            // Store the zip code from this small transaction for later comparison
            zipCodeState.update(detailedTransaction.getZipCode());

            long timer = context.timerService().currentProcessingTime() + ONE_MINUTE;
            context.timerService().registerProcessingTimeTimer(timer);

            timerState.update(timer);
        }
    }

    @Override
    public void onTimer(long timestamp, OnTimerContext ctx, Collector<DetailedAlert> out) {
        // remove flag after 1 minute
        timerState.clear();
        flagState.clear();
        // Also clear the stored zip code when the 1-minute window expires
        zipCodeState.clear();
    }

    private void cleanUp(Context ctx) throws Exception {
        // delete timer
        Long timer = timerState.value();
        if (timer != null) {
            ctx.timerService().deleteProcessingTimeTimer(timer);
        }

        // clean up all state
        timerState.clear();
        flagState.clear();
        // Clear the zip code state along with other state variables
        zipCodeState.clear();
    }
}
