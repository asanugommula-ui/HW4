package spendreport;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.Random;

public class DetailedTransactionSource implements SourceFunction<DetailedTransaction> {

    private volatile boolean running = true;

    @Override
    public void run(SourceContext<DetailedTransaction> ctx) throws Exception {
        Random random = new Random();

        long[] accountIds = {1, 2, 3, 4, 5};
        String[] zipCodes = {"01003", "02115", "78712"};

        long timestamp = 0L;

        while (running) {
            long accountId = accountIds[random.nextInt(accountIds.length)];
            String zipCode = zipCodes[random.nextInt(zipCodes.length)];

            double amount = 0.01 + (1000.0 - 0.01) * random.nextDouble();

            DetailedTransaction transaction =
                    new DetailedTransaction(accountId, timestamp, amount, zipCode);

            ctx.collect(transaction);

            timestamp += 1000; // increment by 1 second in milliseconds
            Thread.sleep(1000); // emit one transaction per second
        }
    }

    @Override
    public void cancel() {
        running = false;
    }
}