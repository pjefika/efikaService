package br.net.gvt.efika.efikaServiceAPI.rest;

import br.net.gvt.efika.efikaServiceAPI.model.ExecDetailedRequest;
import br.net.gvt.efika.efikaServiceAPI.model.service.factory.FactoryService;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;

@Path("/executar")
public class Exec {

    @POST
    @Path("/acao")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response execAcao(AcaoRequest acao) {

        try {
            return Response.status(200).entity(FactoryService.createTelecomServicesDistribution().validacao(acao)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex).build();
        }
    }

    @POST
    @Path("/acaoDetalhada")
//    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response execAcao(ExecDetailedRequest acao) {

        try {
            return Response.status(200).entity(FactoryService.createTelecomServicesDistribution().execucaoDetalhada(acao)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex).build();
        }
    }

}
