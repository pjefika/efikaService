package br.net.gvt.efika.efikaServiceAPI.rest;

import br.net.gvt.efika.efikaServiceAPI.model.ExecDetailedRequest;
import br.net.gvt.efika.efikaServiceAPI.model.service.factory.FactoryService;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoRequest;
import br.net.gvt.efika.util.json.JacksonMapper;
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
    @Consumes({"text/plain"})
    @Produces({"application/json"})
    public Response execAcao(String request) {

        try {
            ExecDetailedRequest acao = (ExecDetailedRequest) new JacksonMapper(ExecDetailedRequest.class).deserialize(request);
            return Response.status(200).entity(FactoryService.createTelecomServicesDistribution().execucaoDetalhada(acao)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex).build();
        }
    }

}
