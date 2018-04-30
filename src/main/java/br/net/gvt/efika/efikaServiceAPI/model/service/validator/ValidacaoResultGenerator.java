/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.service.validator;

import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;
import static br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum.ASSOCIACAO_ONT;
import static br.net.gvt.efika.efikaServiceAPI.model.enums.ExecDetailedEnum.GET_ONTS;
import br.net.gvt.efika.efikaServiceAPI.model.service.factory.FactoryService;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoValidadora;
import br.net.gvt.efika.efikaServiceAPI.model.validador.ExecucaoDetalhada;
import br.net.gvt.efika.efika_customer.model.customer.EfikaCustomer;
import br.net.gvt.efika.fulltest.model.fulltest.FulltestRequest;
import br.net.gvt.efika.fulltest.model.fulltest.SetOntToOltRequest;
import br.net.gvt.efika.fulltest.model.fulltest.ValidacaoResult;
import br.net.gvt.efika.fulltest.model.telecom.properties.TelecomPropertiesEnum;
import br.net.gvt.efika.fulltest.model.telecom.properties.ValidavelAbs;
import br.net.gvt.efika.fulltest.model.telecom.properties.gpon.SerialOntGpon;
import br.net.gvt.efika.fulltest.service.factory.FactoryFulltestService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author G0041775
 */
public class ValidacaoResultGenerator {

    protected static ResourceBundle bundle = ResourceBundle.getBundle("messages", new Locale("co", "CO"));

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

        switch (a.getAcao()) {
            case ASSOCIACAO_ONT:
                v = FactoryFulltestService.newConfigPortaService().getOntFromOlt(
                        new FulltestRequest(a.getCustomer(), "efikaServiceAPI"));
                break;
            case CHECK_GERENCIA:
                v = new ValidacaoResult(a.getAcao().toString(), "Gerência disponível",
                        FactoryFulltestService.newConfigPortaService().isManageable(
                                new FulltestRequest(a.getCustomer(), "efikaServiceAPI")), null);
                break;
            case ESTADO_PORTA:
                v = FactoryFulltestService.newConfigPortaService().corretorEstadoPorta(
                        new FulltestRequest(a.getCustomer(), "efikaServiceAPI"));
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

    public static List<ValidacaoResult> fakeGeneration(AcaoEnum a) {
        List<ValidacaoResult> l = new ArrayList<>();
        switch (a) {
            case ASSOCIACAO_ONT:
                l.add(new ValidacaoResult(a.toString(), bundle.getString("validacaoSerialOnt_ok") + " ABC123456", Boolean.TRUE, new ValidavelAbs(TelecomPropertiesEnum.SerialOntGpon) {
                    public SerialOntGpon getSerial() {
                        return new SerialOntGpon("ABC123456");
                    }
                }, Boolean.FALSE));
                l.add(new ValidacaoResult(a.toString(), bundle.getString("validacaoSerialOnt_nok"), Boolean.FALSE, new ValidavelAbs(TelecomPropertiesEnum.SerialOntGpon) {
                    public SerialOntGpon getSerial() {
                        return new SerialOntGpon("");
                    }
                }, null));
                l.add(new ValidacaoResult(a.toString(), bundle.getString("validacaoSerialOnt_ok") + "0123456789", Boolean.TRUE, new ValidavelAbs(TelecomPropertiesEnum.SerialOntGpon) {
                    public SerialOntGpon getSerial() {
                        SerialOntGpon s = new SerialOntGpon();
                        s.setIdOnt("0123456789");
                        return s;
                    }
                }, Boolean.FALSE));
                l.add(new ValidacaoResult(a.toString(), bundle.getString("correcaoSerialOnt_ok") + "0123456789", Boolean.FALSE, new ValidavelAbs(TelecomPropertiesEnum.SerialOntGpon) {
                    public SerialOntGpon getSerial() {
                        SerialOntGpon s = new SerialOntGpon();
                        s.setIdOnt("0123456789");
                        return s;
                    }
                }, Boolean.TRUE));
                l.add(new ValidacaoResult(a.toString(), bundle.getString("correcaoSerialOnt_nok"), Boolean.FALSE, new ValidavelAbs(TelecomPropertiesEnum.SerialOntGpon) {
                    public SerialOntGpon getSerial() {
                        SerialOntGpon s = new SerialOntGpon();
                        s.setIdOnt("0123456789");
                        return s;
                    }
                }, Boolean.FALSE));

                break;
            case CHECK_GERENCIA:
                l.add(new ValidacaoResult(a.toString(), "Gerência disponível", true, null));
                break;
            case ESTADO_PORTA:
                l.add(new ValidacaoResult(a.toString(), bundle.getString("validacaoEstadoOper_ok"), Boolean.TRUE, null));
                l.add(new ValidacaoResult(a.toString(), bundle.getString("validacaoEstadoOper_nok"), Boolean.FALSE, null));
                l.add(new ValidacaoResult(a.toString(), bundle.getString("correcaoEstadoAdm_ok"), Boolean.FALSE, Boolean.TRUE));
                l.add(new ValidacaoResult(a.toString(), bundle.getString("correcaoEstadoAdm_nok"), Boolean.FALSE, Boolean.FALSE));
                break;
            default:
                break;
        }
        l.add(new ValidacaoResult("", "Falha ao conectar-se com o Jump Access.", Boolean.FALSE, Boolean.FALSE));
        l.add(new ValidacaoResult("", "Inventário de Rede inexistente.", Boolean.FALSE, Boolean.FALSE));
        l.add(new ValidacaoResult("", "Inventário de Rede incompleto.", Boolean.FALSE, Boolean.FALSE));
        l.add(new ValidacaoResult("", "Funcionalidade indisponível para este modelo de DSLAM/OLT.", Boolean.FALSE, Boolean.FALSE));
        l.add(new ValidacaoResult("", "Identificado Shelf sem gerência.", Boolean.FALSE, Boolean.FALSE));
        return l;
    }
}
