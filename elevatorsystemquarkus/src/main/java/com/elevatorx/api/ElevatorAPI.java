package com.elevatorx.api;

import com.elevatorx.enums.FloorButton;
import com.elevatorx.services.ElevatorService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;

// TODO DTO work
@ApplicationScoped
@Path("/elevator")
public class ElevatorAPI {
    @Inject
    ElevatorService elevatorService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/status")
    public String status() {
        return elevatorService.getStatuses();
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/step")
    public Response step() {
        elevatorService.step();
        return Response.ok().build();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/floorButtonPress")
    public Response floorButtonPress(@QueryParam("floor") Integer floor, @QueryParam("button") FloorButton button) {
        if (button == null || floor == null) {
            // Check if elevatorId or floor is null
            throw new BadRequestException("Missing required parameters."); // Return a 400 Bad Request response
        }
        elevatorService.pickUp(floor, button);
        return Response.ok().build();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/elevatorButtonPress")
    public Response elevatorButtonPress(@QueryParam("elevatorId") Integer elevatorId, @QueryParam("floor") Integer floor) {
        if (elevatorId == null || floor == null) {
            // Check if elevatorId or floor is null
            throw new BadRequestException("Missing required parameters."); // Return a 400 Bad Request response
        }
        elevatorService.clickElevatorButtons(elevatorId, Collections.singletonList(floor));
        return Response.ok().build();
    }
}