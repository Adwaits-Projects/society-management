package com.society.management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "floor")
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "floor_id")
    private Integer floorId;

    @Column(name = "floor_num")
    private Integer floorNum;

    @Column(name = "wing_id")
    private Integer wingId;

    public Integer getFloorId() {
        return floorId;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

    public Integer getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(Integer floorNum) {
        this.floorNum = floorNum;
    }

    public Integer getWingId() {
        return wingId;
    }

    public void setWingId(Integer wingId) {
        this.wingId = wingId;
    }
}