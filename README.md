# ElevatorSimulator
## Project Description
This project consists of two parts:
* **Frontend** written in REACT. It is a simple web interface created so I can visualize how my algorithm works.
* **Backend** written in Java QUARKUS. It consists of 3 layers and an elevator scheduling algorithm implementation.
This project runs a simulation on 16 elevators with 20 floors.
## How to run locally
You can run the frontend by going into it's main directory and running:
```
> npm start
```
Running backend is a little more complicated and is explained in backend quarkus Readme.
## Frontend
Frontend is written in REACT. Consists of one App.js which is a simple function with hooks and ElevatorDisplay.js that contains elevators that will be displayed on our screen.
### Interface
Two main parts are:
1. Forms that allow user to:
  * Perform next step of simulation and view results.
  * Click elevator button on a floor.
  * Click floor button inside the elevator.
2. Elevators visualization.
### Strong and weak parts
+ I am new in React, the code is ugly ...
+ ... but it does it's job.
## Backend
Backend is written in Java with Quarkus.
### Project Structure + Architecture
So the project structure is divided into 3 layers:
+ API
+ SERVICE
+ REPOSITORY

Plus two additional directories:
+ MODELS
+ ENUMS

This makes it easy to see the code flow. I used to programm like this when I was still working in a tech company.

### REST API
Domain is **http://localhost:8080/elevator**. Than after that we can invoke:
```
@POST
/floorButtonPress?floor=${requestedFloor}&button=${direction}
```

```
@POST
/elevatorButtonPress?elevatorId=${requestedId}&floor=${requesteFloorElevator}
```

```
@GET
/step
```

```GET
@GET
/status
```

### Elevator algorithm

The algorithm of choosing the right elevator:
```java
@Override
    public void pickUp(Integer floor, FloorButton button) {
        Elevator elevator = elevatorRepository.getNearestElevatorWithCorrectDirection(floor, button);
        if (elevator == null) {
            elevator = elevatorRepository.getNearestElevator(floor);
        }
        elevator.addFloor(floor);
    }
```

The idea is to choose the elevator that will be the closest to the floor that the request is from, but it also has to be moving in the direction or the choosen elevator can be stationary (but also hast to be the closest one). If noone is present, we just choose the closest one.

```java
public Elevator getNearestElevatorWithCorrectDirection(Integer floor, FloorButton button) {
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
```
This approach optimizes the use of energy and time. First of all you choose only the closest elevator and by assuring the direction you safe time of the passangers.
## Overall strong and weak parts
### What went right:
1. The backend is java is clear.
2. The code does it's job.
3. Rest API simulates real life scenario.
### What went wrong:
1. React code coud be written better.
2. Maybe using Rest isn't a good idea. I think running the simulation on backend with while loop + web socket could be better.
3. In API layer I coud use DTO object, but this is a simple app so I stayed with simple arguments.
4. Working on null on backend as a return value is not a good practice. It is better to work with Optional.
