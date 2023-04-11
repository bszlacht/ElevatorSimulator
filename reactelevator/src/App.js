import React, { useState } from "react";
import ElevatorDisplay from "./ElevatorDisplay";

const App = () => {
  const [direction, setDirection] = useState("UP");
  const [requestedFloor, setRequestedFloor] = useState(0);
  const [requestedId, setRequestedId] = useState(0);
  const [requesteFloorElevator, setrequesteFloorElevator] = useState(0);

  const [floors, setFloors] = useState([
    { floor: 0, direction: "UP" },
    { floor: 0, direction: "UP" },
    { floor: 0, direction: "UP" },
    { floor: 0, direction: "UP" },
    { floor: 0, direction: "UP" },
    { floor: 0, direction: "UP" },
    { floor: 0, direction: "UP" },
    { floor: 0, direction: "UP" },
    { floor: 0, direction: "UP" },
    { floor: 0, direction: "UP" },
    { floor: 0, direction: "UP" },
    { floor: 0, direction: "UP" },
    { floor: 0, direction: "UP" },
    { floor: 0, direction: "UP" },
    { floor: 0, direction: "UP" },
    { floor: 0, direction: "UP" },
  ]);

  const handleIdChange = (event) => {
    const newId = parseInt(event.target.value);
    setRequestedId(newId);
  };

  const handleFloorElevatorChange = (event) => {
    const newFloor = parseInt(event.target.value);
    setrequesteFloorElevator(newFloor);
  };

  const handleFloorChange = (event) => {
    const newFloor = parseInt(event.target.value);
    setRequestedFloor(newFloor);
  };

  const handleDirectionChange = (event) => {
    const newDirection = event.target.value;
    setDirection(newDirection);
  };

  const handleRequestElevator = (event) => {
    event.preventDefault(); // Prevent form submission and page refresh

    fetch(`http://localhost:8080/elevator/floorButtonPress?floor=${requestedFloor}&button=${direction}`, {
      method: "POST",
      mode: "no-cors", // Adding this line to disable CORS
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((data) => {
      })
      .catch((error) => {
        console.error(error);
      })
  }

  const handleRequestElevatorFromElevator = (event) => {
    event.preventDefault(); // Prevent form submission and page refresh

    fetch(`http://localhost:8080/elevator/elevatorButtonPress?elevatorId=${requestedId}&floor=${requesteFloorElevator}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((data) => {
      })
      .catch((error) => {
        console.error(error);
      })
  }

  const handleStepAndVisualize = async () => {
    try {
      const stepResponse = await fetch("http://localhost:8080/elevator/step");
      if (stepResponse.ok) {
        console.log("Step success");
        const statusResponse = await fetch("http://localhost:8080/elevator/status");
        if (statusResponse.ok) {
          console.log("Status success");
          const parsedJson = await statusResponse.json();
          // console.log(parsedJson);
          var newJson = [...floors];
          for (let i = 0; i < 16; i++) {
            const innerObject = parsedJson[i];
            var floor_ = innerObject.floor;
            var dir = innerObject.direction;
            if (dir === 0) {
              dir = "STAT"
            }
            if (dir > 0) {
              dir = "UP"
            }
            if (dir < 0) {
              dir = "DOWN"
            }
            newJson[i] = { floor: floor_, direction: dir };
          }
          setFloors(newJson);
        } else {
          console.error("Status failed");
        }
      } else {
        console.error("Step failed");
      }
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div>
      <h1>Elevator App</h1>
      <form onSubmit={handleRequestElevator}>
        <label htmlFor="floor">Floor:</label>
        <input
          type="number"
          id="requestedFloor"
          onChange={handleFloorChange}
          max="19"
          min="0"
        />
        <label htmlFor="direction">Direction:</label>
        <select id="direction" value={direction} onChange={handleDirectionChange}>
          <option value="UP">Up</option>
          <option value="DOWN">Down</option>
        </select>
        <button type="submit">Request Elevator</button>
      </form>
      <button type="submit" id="step" onClick={handleStepAndVisualize} >Step and Visualize</button>
      
      <form onSubmit={handleRequestElevatorFromElevator}>
        <label htmlFor="floor">Floor:</label>
        <input
          type="number"
          id="requestedFloorElevator"
          onChange={handleFloorElevatorChange}
          max="19"
          min="0"
        />
        <label htmlFor="id">Id:</label>
        <input
          type="number"
          id="requestedId"
          onChange={handleIdChange}
          max="15"
          min="0"
        />
        <button type="submit">Press button inside elevator</button>
      </form>
      
      <div id="elevator-div">
        {floors.map((floorData, index) => (
          <ElevatorDisplay
            key={index} // Update key prop to a unique value
            floor={floorData.floor}
            direction={floorData.direction}
          />
        ))}
        {/* Render multiple ElevatorDisplay components with different floor and direction values */}
      </div>
    </div>
  );
};

export default App;