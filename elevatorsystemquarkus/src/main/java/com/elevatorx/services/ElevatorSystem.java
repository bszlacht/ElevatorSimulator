package com.elevatorx.services;

import com.elevatorx.enums.FloorButton;

public interface ElevatorSystem {

    public void pickUp(Integer floor, FloorButton button);

    public String getStatuses();

    public void step();
}
