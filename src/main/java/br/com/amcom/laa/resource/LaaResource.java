package br.com.amcom.laa.resource;

import br.com.amcom.laa.domain.Access;
import br.com.amcom.laa.exception.ElasticSearchClientException;
import br.com.amcom.laa.service.MetricsService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        } catch (ElasticSearchClientException e) {
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
        } catch (ElasticSearchClientException e) {
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
        } catch (ElasticSearchClientException e) {
            return e.getResponse();
        }
    }

    @GET
    @Path("/minute")
    @Produces("application/json")
    public Response getMinuteMoreAccess() {
        return Response.serverError().build();
    }
}
