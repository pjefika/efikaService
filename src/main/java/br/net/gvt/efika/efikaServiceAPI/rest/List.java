package br.net.gvt.efika.efikaServiceAPI.rest;

import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;
import br.net.gvt.efika.efikaServiceAPI.model.service.factory.FactoryService;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;

@Path("/listar")
public class List {

    @GET
    @Path("/acoes")
    @Produces({"application/json"})
    public Response listAcoes() {

        try {
            return Response.status(200).entity(FactoryService.createListMakerService().listarAcoes()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex).build();
        }
    }
    
    @GET
    @Path("/respostas/{acao}")
    @Produces({"application/json"})
    public Response listResp(@PathParam("acao") String acao) {

        try {
            return Response.status(200).entity(FactoryService.createListMakerService().listarValidacoes(AcaoEnum.valueOf(acao))).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex).build();
        }
    }
    
    

}
