package com.society.management.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer billId;

    private Integer flatId;

    private String billMonth;

    private Double electricityBill;
    private Double waterBill;
    private Double maintenanceFee;
    private Double propertyTax;

    private Double lateFee;

    private Double totalAmount;

    private String paymentStatus;

    private LocalDate paymentDate;

    private String transactionId;

    private String receiptNumber;

    private LocalDate generatedOn;

    private LocalDate dueDate;

    private String reminderStatus;

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public Integer getFlatId() {
        return flatId;
    }

    public void setFlatId(Integer flatId) {
        this.flatId = flatId;
    }

    public String getBillMonth() {
        return billMonth;
    }

    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }

    public Double getElectricityBill() {
        return electricityBill;
    }

    public void setElectricityBill(Double electricityBill) {
        this.electricityBill = electricityBill;
    }

    public Double getWaterBill() {
        return waterBill;
    }

    public void setWaterBill(Double waterBill) {
        this.waterBill = waterBill;
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

    public Double getLateFee() {
        return lateFee;
    }

    public void setLateFee(Double lateFee) {
        this.lateFee = lateFee;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public LocalDate getGeneratedOn() {
        return generatedOn;
    }

    public void setGeneratedOn(LocalDate generatedOn) {
        this.generatedOn = generatedOn;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getReminderStatus() {
        return reminderStatus;
    }

    public void setReminderStatus(String reminderStatus) {
        this.reminderStatus = reminderStatus;
    }
}
