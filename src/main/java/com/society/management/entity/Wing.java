package com.society.management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "wing")
public class Wing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wing_id")
    private Integer wingId;

    @Column(name = "wing_name")
    private String wingName;

    public Integer getWingId() {
        return wingId;
    }

    public void setWingId(Integer wingId) {
        this.wingId = wingId;
    }

    public String getWingName() {
        return wingName;
    }

    public void setWingName(String wingName) {
        this.wingName = wingName;
    }
}