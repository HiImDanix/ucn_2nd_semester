package model;

import java.math.BigDecimal;

public class RoomCategory {
    private int id;
    private String name;
    private String description;
    private BigDecimal pricePerMonth;
    private BigDecimal PricePerMonthForInternet;
    private BigDecimal pricePerMonthForExtraTenant;
    private int maxTenants;
    private List<Furniture> furniture;

    public RoomCategory(int id, String name, String description, BigDecimal pricePerMonth, BigDecimal PricePerMonthForInternet, BigDecimal pricePerMonthForExtraTenant, int maxTenants, List<Furniture> furniture) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pricePerMonth = pricePerMonth;
        this.PricePerMonthForInternet = PricePerMonthForInternet;
        this.pricePerMonthForExtraTenant = pricePerMonthForExtraTenant;
        this.maxTenants = maxTenants;
        this.furniture = furniture;
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
}
