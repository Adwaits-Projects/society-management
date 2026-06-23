package com.society.management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "utility")
public class Utility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "utility_id")
    private Integer utilityId;

    @Column(name = "flat_id")
    private Integer flatId;

    @Column(name = "electricity_usage")
    private Integer electricityUsage;

    @Column(name = "electricity_bill_amt")
    private Double electricityBillAmt;

    @Column(name = "water_usage")
    private Integer waterUsage;

    @Column(name = "water_bill_amt")
    private Double waterBillAmt;

    @Column(name = "maintenance_fee")
    private Double maintenanceFee;

    @Column(name = "property_tax")
    private Double propertyTax;

    @Column(name = "pending_amt")
    private Double pendingAmt;

    public Integer getUtilityId() {
        return utilityId;
    }

    public void setUtilityId(Integer utilityId) {
        this.utilityId = utilityId;
    }

    public Integer getFlatId() {
        return flatId;
    }

    public void setFlatId(Integer flatId) {
        this.flatId = flatId;
    }

    public Integer getElectricityUsage() {
        return electricityUsage;
    }

    public void setElectricityUsage(Integer electricityUsage) {
        this.electricityUsage = electricityUsage;
    }

    public Double getElectricityBillAmt() {
        return electricityBillAmt;
    }

    public void setElectricityBillAmt(Double electricityBillAmt) {
        this.electricityBillAmt = electricityBillAmt;
    }

    public Integer getWaterUsage() {
        return waterUsage;
    }

    public void setWaterUsage(Integer waterUsage) {
        this.waterUsage = waterUsage;
    }

    public Double getWaterBillAmt() {
        return waterBillAmt;
    }

    public void setWaterBillAmt(Double waterBillAmt) {
        this.waterBillAmt = waterBillAmt;
    }

    public Double getMaintenanceFee() {
        return maintenanceFee;
    }

    public void setMaintenanceFee(Double maintenanceFee) {
        this.maintenanceFee = maintenanceFee;
    }

    public Double getPropertyTax() {
        return propertyTax;
    }

    public void setPropertyTax(Double propertyTax) {
        this.propertyTax = propertyTax;
    }

    public Double getPendingAmt() {
        return pendingAmt;
    }

    public void setPendingAmt(Double pendingAmt) {
        this.pendingAmt = pendingAmt;
    }
}