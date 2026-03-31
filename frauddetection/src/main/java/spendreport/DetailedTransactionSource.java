package spendreport;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.Random;

public class DetailedTransactionSource implements SourceFunction<DetailedTransaction> {

    // Changed from extending FromIteratorFunction to implementing SourceFunction
    // to allow for real-time random generation of transactions

    private volatile boolean running = true;

    @Override
    public void run(SourceContext<DetailedTransaction> ctx) throws Exception {
        // Changed from iterator-based approach to direct random generation
        Random random = new Random();

        // account IDs are the same as base class but selected randomly
        long[] accountIds = {1, 2, 3, 4, 5};
        
        // zip codes are randomly selected for each transaction
        String[] zipCodes = {"01003", "02115", "78712"};

        long timestamp = 0L;

        while (running) {
            // Uniformly randomly chosen from the account id set
            long accountId = accountIds[random.nextInt(accountIds.length)];
            
            // Uniformly randomly chosen from the zip code set
            String zipCode = zipCodes[random.nextInt(zipCodes.length)];

            // amount is uniformly randomly chosen from $0 to $1000
            // instead of being statically generated
            double amount = 0.01 + (1000.0 - 0.01) * random.nextDouble();

            // creating DetailedTransaction instead of Transaction
            DetailedTransaction transaction =
                    new DetailedTransaction(accountId, timestamp, amount, zipCode);

            ctx.collect(transaction);

            // timestamp increments by 1 second in milliseconds
            timestamp += 1000;
            
            // sleep 1 second instead of 100ms 
            Thread.sleep(1000); // emit one transaction per second
        }
    }

    // override cancel() to implement SourceFunction interface
    @Override
    public void cancel() {
        running = false;
    }
}
