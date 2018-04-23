package br.net.gvt.efika.efikaServiceAPI.rest;

import br.net.gvt.efika.efikaServiceAPI.model.ExecDetailedRequest;
import br.net.gvt.efika.efikaServiceAPI.model.service.factory.FactoryService;
import br.net.gvt.efika.util.json.JacksonMapper;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/executar")
public class ExecAcao {

    @POST
    @Path("/detailed")
    @Produces("{application/json}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response execTela(String req) {
        System.out.println("acionou");
        System.out.println("acionou");
        System.out.println("acionou");
        try {
            return Response.status(200).entity(FactoryService.createTelecomServicesDistribution()
                    .execucaoDetalhada((ExecDetailedRequest) new JacksonMapper(ExecDetailedRequest.class).deserialize(req)))
                    .build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex).build();
        }
    }

}
