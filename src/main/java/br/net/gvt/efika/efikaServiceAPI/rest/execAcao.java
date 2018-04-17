package br.net.gvt.efika.efikaServiceAPI.rest;

import br.net.gvt.efika.efikaServiceAPI.model.ExecDetailedRequest;
import br.net.gvt.efika.efikaServiceAPI.model.service.factory.FactoryService;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;

@Path("/executar")
public class execAcao {

    @POST
    @Path("/acao")
    @Produces({"application/json"})
    public Response execAcao() {

        try {
            return Response.status(200).entity(FactoryService.createListMakerService().listarAcoes()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex).build();
        }
    }

    @POST
    @Path("/detailed")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response execTela(ExecDetailedRequest req) {

        try {
            return Response.status(200).entity(FactoryService.createTelecomServicesDistribution().execucaoDetalhada(req)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex).build();
        }
    }

}
