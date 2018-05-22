package br.net.gvt.efika.efikaServiceAPI.rest;

import br.net.gvt.efika.efikaServiceAPI.dao.factory.FactoryDAO;
import br.net.gvt.efika.efikaServiceAPI.model.ExecDetailedRequest;
import br.net.gvt.efika.efikaServiceAPI.model.service.factory.FactoryService;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoRequest;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoValidadora;
import br.net.gvt.efika.efikaServiceAPI.model.validador.ExecucaoDetalhada;
import br.net.gvt.efika.util.json.JacksonMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            AcaoValidadora v = FactoryService.createTelecomServicesDistribution().validacao(acao);
            FactoryDAO.acaoDao().save(v);
            return Response.status(200).entity(v).build();
        } catch (Exception ex) {
            Logger.getLogger(Exec.class.getName()).log(Level.SEVERE, null, ex);
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
            ExecucaoDetalhada d = FactoryService.createTelecomServicesDistribution().execucaoDetalhada(acao);
            FactoryDAO.execDao().save(d);
            return Response.status(200).entity(d).build();
        } catch (Exception ex) {
            Logger.getLogger(Exec.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex).build();
        }
    }

}
