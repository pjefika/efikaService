package br.net.gvt.efika.efikaServiceAPI.rest;

import br.net.gvt.efika.efikaServiceAPI.model.service.factory.FactoryService;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;

@Path("/serviceList")
public class ServiceList {

    @GET
    @Path("/acoes")
    @Produces({"application/json"})
    public Response certification() {

        try {
            return Response.status(200).entity(FactoryService.createListMakerService().listarAcoes()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex).build();
        }
    }

}
