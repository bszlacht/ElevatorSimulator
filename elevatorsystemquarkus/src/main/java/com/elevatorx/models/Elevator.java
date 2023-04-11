package com.elevatorx.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.PriorityQueue;

import static java.lang.Math.abs;

@Builder
@ToString
@Getter
public class Elevator {
    Integer id;
    @Builder.Default
    Integer floor = 0;
    @Builder.Default
    PriorityQueue<Integer> destinationFloors = new PriorityQueue<>();

    public String getStatus() {
        return this.toString();
    }

    public Integer getDirection() {
        if (destinationFloors.isEmpty()) {
            return 0;
        }
        return destinationFloors.peek() - floor;
    }

    public Integer getDistance(Integer floor) {
        return abs(this.floor - floor);
    }

    public Integer getDirectionFloor() {
        if (destinationFloors.isEmpty()) {
            return null;
        }
        return destinationFloors.peek();
    }

    public void move() {
        if (!destinationFloors.isEmpty()) {
            int floorDifference = destinationFloors.peek() - floor;
            if (floorDifference < 0) {
                this.floor--;
            } else if (floorDifference > 0) {
                this.floor++;
            } else {
                // open
                destinationFloors.remove();
            }
        }
    }

    public void addFloor(Integer id) {
        if (this.destinationFloors.contains(id)) {
            return;
        }
        this.destinationFloors.add(id);
    }

    public String convertToJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null; // or throw an exception as needed
        }
    }
}
