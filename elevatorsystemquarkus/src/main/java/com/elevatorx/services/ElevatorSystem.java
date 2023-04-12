package com.elevatorx.services;

import com.elevatorx.enums.FloorButton;

import java.util.List;

public interface ElevatorSystem {

    public void pickUp(Integer floor, FloorButton button);

    public String getStatuses();

    public void step();
    public void clickElevatorButtons(Integer id, List<Integer> floors);
}
