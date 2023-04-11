package com.elevatorx.repositories;

import com.elevatorx.enums.FloorButton;
import com.elevatorx.models.Elevator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.util.*;

@ApplicationScoped
public class ElevatorRepository {
    Map<Integer, Elevator> elevators = new HashMap<>();

    void onStart(@Observes StartupEvent ev) {
        this.generateElevators();
    }

    private void generateElevators() {
        for (int i = 0; i < 16; i++) {
            elevators.put(i, Elevator.builder().id(i).build());
        }

    }

    public String getStatus() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<String> elevatorsInString = elevators.values().stream().map(Elevator::convertToJson).toList();
            Map<Integer, JsonNode> res = new HashMap<>();
            int i = 0;
            for (String ele : elevatorsInString) {
                res.put(i, objectMapper.readTree(ele));
                i++;
            }
            return objectMapper.writeValueAsString(res);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public void moveAll() {
        elevators.values().forEach(Elevator::move);
    }

    public void addDirectionToElevator(Integer id, Integer floor) {
        elevators.get(id).addFloor(floor);
    }

    public Elevator getSingleStationaryElevator() {
        // todo fix into Optional.Empty etc.
        return elevators.values().stream().filter(el -> el.getDirection() == 0).findFirst().orElse(null);
    }

    public Elevator getNearestElevator(Integer floor) {
        Elevator bestElevator = null;
        int minDistance = Integer.MAX_VALUE;
        for (Elevator elevator : elevators.values()) {
            if (elevator.getDistance(floor) < minDistance) {
                bestElevator = elevator;
            }
        }
        return bestElevator;
    }

    public Elevator getSingleElevatorWithGoodDirection(Integer floor, FloorButton button) {
        //  here I think stream is not so readable
        Elevator bestElevator = null;
        int minDistance = Integer.MAX_VALUE;
        for (Elevator elevator : elevators.values()) {
            if (elevator.getDirection() == 0 || elevator.getDirection() >= 0 && elevator.getFloor() < floor && button == FloorButton.UP || elevator.getDirection() <= 0 && elevator.getFloor() > floor && button == FloorButton.DOWN) {
                int distance = elevator.getDistance(floor);
                if (distance < minDistance) {
                    minDistance = distance;
                    bestElevator = elevator;
                }
            }
        }
        return bestElevator;
    }
}
