package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class RoomCategory {
    private int id;
    private String name;
    private String description;
    private BigDecimal pricePerMonth;
    private BigDecimal PricePerMonthForInternet;
    private BigDecimal pricePerMonthForExtraTenant;
    private int maxTenants;
    private List<Furniture> furniture;
    private int leaveNoticeDays;

    private boolean fullObjectRetrieved = false;

    public RoomCategory(int id, String name, String description, BigDecimal pricePerMonth, BigDecimal PricePerMonthForInternet, BigDecimal pricePerMonthForExtraTenant, int maxTenants, int leaveNoticeDays, List<Furniture> furniture) {
        if (pricePerMonth.compareTo(BigDecimal.ZERO) < 0 || PricePerMonthForInternet.compareTo(BigDecimal.ZERO) < 0 || pricePerMonthForExtraTenant.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (leaveNoticeDays < 0) {
            throw new IllegalArgumentException("Leave notice days cannot be negative");
        }
        if (maxTenants < 0) {
            throw new IllegalArgumentException("Max tenants cannot be negative");
        }

        this.id = id;
        this.name = name;
        this.description = description;
        this.pricePerMonth = pricePerMonth;
        this.PricePerMonthForInternet = PricePerMonthForInternet;
        this.pricePerMonthForExtraTenant = pricePerMonthForExtraTenant;
        this.maxTenants = maxTenants;
        this.furniture = furniture;
        this.leaveNoticeDays = leaveNoticeDays;

        this.fullObjectRetrieved = true;
    }

    public RoomCategory(int id, String name, String description, BigDecimal pricePerMonth, BigDecimal PricePerMonthForInternet, BigDecimal pricePerMonthForExtraTenant, int maxTenants, int leaveNoticeDays) {
        this(id, name, description, pricePerMonth, PricePerMonthForInternet, pricePerMonthForExtraTenant, maxTenants, leaveNoticeDays, null);
    }

    public boolean isfullObjectRetrieved() {
        return fullObjectRetrieved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(BigDecimal pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }

    public BigDecimal getPricePerMonthForInternet() {
        return PricePerMonthForInternet;
    }

    public void setPricePerMonthForInternet(BigDecimal pricePerMonthForInternet) {
        PricePerMonthForInternet = pricePerMonthForInternet;
    }

    public BigDecimal getPricePerMonthForExtraTenant() {
        return pricePerMonthForExtraTenant;
    }

    public void setPricePerMonthForExtraTenant(BigDecimal pricePerMonthForExtraTenant) {
        this.pricePerMonthForExtraTenant = pricePerMonthForExtraTenant;
    }

    public int getMaxTenants() {
        return maxTenants;
    }

    public void setMaxTenants(int maxTenants) {
        this.maxTenants = maxTenants;
    }

    public List<Furniture> getFurniture() {
        return furniture;
    }

    public void setFurniture(List<Furniture> furniture) {
        this.furniture = furniture;
    }

    public int getLeaveNoticeDays() {
        return leaveNoticeDays;
    }

    public void setLeaveNoticeDays(int leaveNoticeDays) {
        this.leaveNoticeDays = leaveNoticeDays;
    }

    @Override
    public String toString() {
        return "RoomCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", pricePerMonth=" + pricePerMonth +
                ", PricePerMonthForInternet=" + PricePerMonthForInternet +
                ", pricePerMonthForExtraTenant=" + pricePerMonthForExtraTenant +
                ", maxTenants=" + maxTenants +
                ", furniture=" + furniture +
                ", leaveNoticeDays=" + leaveNoticeDays +
                '}';
    }
}
