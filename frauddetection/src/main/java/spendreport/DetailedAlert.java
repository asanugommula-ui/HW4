package spendreport;

public class DetailedAlert {

    private long accountId;
    private long timestamp;
    private String zipCode;
    private double amount;

    public DetailedAlert() {
    }

    public DetailedAlert(long accountId, long timestamp, String zipCode, double amount) {
        this.accountId = accountId;
        this.timestamp = timestamp;
        this.zipCode = zipCode;
        this.amount = amount;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "DetailedAlert{" +
                "accountId=" + accountId +
                ", timestamp=" + timestamp +
                ", zipCode='" + zipCode + '\'' +
                ", amount=" + amount +
                '}';
    }
}