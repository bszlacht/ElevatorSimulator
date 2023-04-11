package com.elevatorx.services;

import com.elevatorx.enums.FloorButton;
import com.elevatorx.models.Elevator;
import com.elevatorx.repositories.ElevatorRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ElevatorService implements ElevatorSystem {
    @Inject
    ElevatorRepository elevatorRepository;


    @Override
    public String getStatuses() {
        return elevatorRepository.getStatus();
    }

    @Override
    public void step() {
        elevatorRepository.moveAll();
    }

    @Override
    public void pickUp(Integer floor, FloorButton button) {
        // request income from floor
        //  1. Get the nearest stationary elevator
        //  2. If there aren't any, choose one that moves in your direction
        //  3. Else wait.
        Elevator elevator = elevatorRepository.getSingleElevatorWithGoodDirection(floor, button);

        if (elevator == null) {
            elevator = elevatorRepository.getSingleStationaryElevator();
            if (elevator == null){
                elevator = elevatorRepository.getNearestElevator(floor);
            }
        }
        elevator.addFloor(floor);
    }

    public void clickElevatorButtons(Integer id, List<Integer> floors) {
        // todo maybe add this to interface?
        floors.forEach(floor -> elevatorRepository.addDirectionToElevator(id, floor));
    }
}
