/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.validador.factory;

import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;
import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoResultEnum;
import br.net.gvt.efika.efikaServiceAPI.model.service.validator.ValidacaoResultGenerator;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoValidadora;
import br.net.gvt.efika.efika_customer.model.customer.EfikaCustomer;
import br.net.gvt.efika.fulltest.model.fulltest.ValidacaoResult;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author G0041775
 */
public class FactoryAcaoValidadora {

    public static AcaoValidadora create(AcaoEnum acao) throws Exception {
        AcaoValidadora av = new AcaoValidadora(acao);
        av.setDataInicio(Calendar.getInstance().getTime());
        return av;
    }

    public static List<AcaoValidadora> fakeListCreation(AcaoEnum acao) {
        List<AcaoValidadora> l = new ArrayList<>();
        ValidacaoResultGenerator.fakeGeneration(acao).forEach((t) -> {
            l.add(fakeCreation(acao, t));
        });
        return l;
    }

    public static AcaoValidadora fakeCreation(AcaoEnum acao, ValidacaoResult valid) {
        AcaoValidadora av = new AcaoValidadora(acao, valid);
        av.setDataInicio(Calendar.getInstance().getTime());
        av.setDataFim(Calendar.getInstance().getTime());
        EfikaCustomer ec = new EfikaCustomer("4125252525");
        ec.setInstancia("4125252525");
        av.setCustomer(ec);
        AcaoResultEnum r;
        if (valid.getFoiCorrigido() != null) {
            if (valid.getResultado()) {
                r = AcaoResultEnum.VALIDADO_OK;
            } else {
                r = valid.getFoiCorrigido() ? AcaoResultEnum.CORRIGIDO_OK : AcaoResultEnum.CORRIGIDO_NOK;
            }
            av.setResultado(valid.getResultado() || valid.getFoiCorrigido());
        } else {
            r = valid.getResultado() ? AcaoResultEnum.VALIDADO_OK : AcaoResultEnum.VALIDADO_NOK;
            av.setResultado(valid.getResultado());
            av.setUrlCorrecao(urlResponseGenerator(av));
        }
        if(valid.getNome().isEmpty()){
            r = AcaoResultEnum.EXCEPTION;
        }
//        String m = ResourceBundle.getBundle("messages", FactoryLocale.createLocale(SystemEnum.CRM)).getString(av.getNome() + "_" + r.name());

        av.setMensagem(valid.getMensagem());
        av.setTipo(r);
        av.setConsulta(hasConsulta(av));
        return av;
    }

    public static String urlResponseGenerator(AcaoValidadora acao) {
        String s = null;
        switch (acao.getAcao()) {
            case ASSOCIACAO_ONT:
                if (!acao.getResultado()) {
                    s = "http://10.40.196.171/efika_gps/pages/associacao_olt/associacao_olt.html?instancia="+acao.getCustomer().getInstancia();
                }
                break;
            case WIFI_CRED:
                if(acao.getResultado()){
                    s = "http://10.40.196.171/efika_gps/pages/wificonf/wificonf.html?instancia="+acao.getCustomer().getInstancia();
                }
                break;
            case REBOOT:
                if(acao.getResultado()){
                    s = "http://10.40.196.171/efika_gps/pages/reboot/reboot.html?instancia="+acao.getCustomer().getInstancia();
                }
                break;
            case FACTORY_RESET:
                if(acao.getResultado()){
                    s = "http://10.40.196.171/efika_gps/pages/factoryreset/factoryreset.html?instancia="+acao.getCustomer().getInstancia();
                }
                break;
            default:
                break;
        }
        return s;
    }

    public static Boolean hasConsulta(AcaoValidadora acao) {
        Boolean bool = true;
        switch (acao.getAcao()) {
            case ASSOCIACAO_ONT:
                bool = acao.getTipo() != AcaoResultEnum.VALIDADO_NOK;
                break;
            default:
                break;
        }
        return bool;
    }

}
