package spendreport;

import java.util.Objects;


@SuppressWarnings("unused")
public final class DetailedAlert {

    // Changed from base Alert's single 'id' field to include detailed transaction information
    private long accountId;
    private long timestamp;
    private String zipCode;
    private double amount;

    public DetailedAlert() {
    }

    // Modified constructor to accept detailed fields instead of just id
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

    // Modified equals to compare all detailed fields instead of just id
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DetailedAlert that = (DetailedAlert) o;
        return accountId == that.accountId
                && timestamp == that.timestamp
                && Double.compare(that.amount, amount) == 0
                && Objects.equals(zipCode, that.zipCode);
    }

    // Modified hashCode to include all detailed fields instead of just id
    @Override
    public int hashCode() {
        return Objects.hash(accountId, timestamp, zipCode, amount);
    }

    // Modified toString to output all detailed fields instead of just id
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
