package br.com.amcom.laa.resource;

import br.com.amcom.laa.domain.Access;
import br.com.amcom.laa.exception.ResponseException;
import br.com.amcom.laa.service.MetricsService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/laa/metrics")
public class LaaResource {

    @GET
    @Path("/top")
    @Produces("application/json")
    public Response getTopAccess() {
        MetricsService service = new MetricsService();
        try {
            List<Access> accessList = service.getTop3World();
            return Response.ok().entity(accessList).build();
        } catch (ResponseException e) {
            return e.getResponse();
        }
    }

    @GET
    @Path("/top/{regionCode}")
    @Produces("application/json")
    public Response getTopAccess(@PathParam("regionCode") Integer regionCode) {
        MetricsService service = new MetricsService();
        try {
            List<Access> accessList = service.getTop3Region(regionCode);
            return Response.ok().entity(accessList).build();
        } catch (ResponseException e) {
            return e.getResponse();
        }
    }

    @GET
    @Path("/less")
    @Produces("application/json")
    public Response getLessAccess() {
        MetricsService service = new MetricsService();
        try {
            List<Access> accessList = service.getLessWorld();
            return Response.ok().entity(accessList).build();
        } catch (ResponseException e) {
            return e.getResponse();
        }
    }

    @GET
    @Path("/period")
    @Produces("application/json")
    public Response getPeriodAccess(
            @QueryParam("day") Integer day,
            @QueryParam("week") Integer week,
            @QueryParam("year") Integer year) {
        MetricsService service = new MetricsService();
        try {
            List<Access> accessList = service.getTop3Period(day, week, year);
            return Response.ok(accessList).build();
        } catch (ResponseException e) {
            return e.getResponse();
        }
    }

    @GET
    @Path("/minute")
    @Produces("application/json")
    public Response getMinuteMoreAccess() {
        MetricsService service = new MetricsService();
        try {
            List<Access> accessList = service.getMinuteMoreAccess();
            return Response.ok().entity(accessList).build();
        } catch (ResponseException e) {
            return e.getResponse();
        }
    }
}
