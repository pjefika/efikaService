/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.service.validator;

import static br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum.ASSOCIACAO_ONT;
import static br.net.gvt.efika.efikaServiceAPI.model.enums.ExecDetailedEnum.GET_ONTS;
import br.net.gvt.efika.efikaServiceAPI.model.service.factory.FactoryService;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoValidadora;
import br.net.gvt.efika.efikaServiceAPI.model.validador.ExecucaoDetalhada;
import br.net.gvt.efika.efika_customer.model.customer.EfikaCustomer;
import br.net.gvt.efika.fulltest.model.fulltest.FulltestRequest;
import br.net.gvt.efika.fulltest.model.fulltest.SetOntToOltRequest;
import br.net.gvt.efika.fulltest.model.fulltest.ValidacaoResult;
import br.net.gvt.efika.fulltest.model.telecom.properties.gpon.SerialOntGpon;
import br.net.gvt.efika.fulltest.service.factory.FactoryFulltestService;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author G0041775
 */
public class ValidacaoResultGenerator {

    public ValidacaoResultGenerator() {
    }

    public static EfikaCustomer getCust(String instancia) throws Exception {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, -15);
        Date dataLimite = now.getTime();
        return FactoryService.customerFinder().getCustomerFromHist(instancia, dataLimite);
    }

    public static ValidacaoResult generate(AcaoValidadora a) throws Exception {
        ValidacaoResult v = null;

        switch (a.getNome()) {
            case ASSOCIACAO_ONT:
                v = FactoryFulltestService.newConfigPortaService().getOntFromOlt(new FulltestRequest(a.getCustomer(), "efikaServiceAPI"));
                break;
            default:
                break;
        }
        return v;
    }

    public static ValidacaoResult generate(ExecucaoDetalhada exec) throws Exception {
        ValidacaoResult v = null;

        switch (exec.getNome()) {
            case GET_ONTS:
                v = new ValidacaoResult("Onts Disponíveis",
                        "", null, null, null,
                        FactoryFulltestService.newConfigPortaService().ontsDisponiveis(new FulltestRequest(exec.getCustomer(), "efikaServiceAPI")));
                break;
            case SET_ONT:
                v = FactoryFulltestService.newConfigPortaService().setOntToOlt(new SetOntToOltRequest(exec.getCustomer(), "efikaServiceAPI", new SerialOntGpon(exec.getParametro())));
                break;
            default:
                break;
        }
        return v;
    }
}
