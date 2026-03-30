package spendreport;

public class DetailedTransaction {

    private long accountId;
    private long timestamp;
    private double amount;
    private String zipCode;

    public DetailedTransaction() {
    }

    public DetailedTransaction(long accountId, long timestamp, double amount, String zipCode) {
        this.accountId = accountId;
        this.timestamp = timestamp;
        this.amount = amount;
        this.zipCode = zipCode;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "DetailedTransaction{" +
                "accountId=" + accountId +
                ", timestamp=" + timestamp +
                ", amount=" + amount +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}