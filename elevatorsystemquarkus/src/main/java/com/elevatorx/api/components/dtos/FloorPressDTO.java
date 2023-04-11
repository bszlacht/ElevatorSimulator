package com.elevatorx.api.components.dtos;

import io.smallrye.common.constraint.NotNull;

import javax.ws.rs.QueryParam;

public class FloorPressDTO {
    @NotNull
    private Integer elevatorId;

    @NotNull
    private Integer floor;


    public Integer getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(Integer elevatorId) {
        this.elevatorId = elevatorId;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }
}
