package dto;

public class AdminSummary {
    private double totalEarnings;  // changed from BigDecimal to double
    private int pendingOrders;
    private int confirmedOrders;
    private int completedOrders;
    private int declinedOrders;
    private int totalCustomers;

    // Getters and Setters
    public double getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(double totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public int getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(int pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

    public int getConfirmedOrders() {
        return confirmedOrders;
    }

    public void setConfirmedOrders(int confirmedOrders) {
        this.confirmedOrders = confirmedOrders;
    }

    public int getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(int completedOrders) {
        this.completedOrders = completedOrders;
    }

    public int getDeclinedOrders() {
        return declinedOrders;
    }

    public void setDeclinedOrders(int declinedOrders) {
        this.declinedOrders = declinedOrders;
    }

    public int getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(int totalCustomers) {
        this.totalCustomers = totalCustomers;
    }
}
