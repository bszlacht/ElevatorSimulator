import React from "react";
import "./ElevatorDisplay.css"; // Import CSS file for styling

const ElevatorDisplay = (props) => {
  const { floor, direction } = props;

  
    // Calculate height of elevator based on floor
    const elevatorSize = floor * 70 + "px";
    return (
      <div className="elevator-parent">
        <div className="elevator-container">
          <div className="elevator" style={{ height: 70, bottom: elevatorSize}}> Floor {floor} <br /> Direction {direction}
          </div>
        </div>
      </div>
    );
}

export default ElevatorDisplay;