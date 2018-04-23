/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.validador.factory;

import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;
import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoResultEnum;
import br.net.gvt.efika.efikaServiceAPI.model.enums.SystemEnum;
import br.net.gvt.efika.efikaServiceAPI.model.service.factory.FactoryLocale;
import br.net.gvt.efika.efikaServiceAPI.model.service.validator.ValidacaoResultGenerator;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoValidadora;
import br.net.gvt.efika.efika_customer.model.customer.EfikaCustomer;
import br.net.gvt.efika.fulltest.model.fulltest.ValidacaoResult;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

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
        av.setCustomer(new EfikaCustomer("4125252525"));
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
        }
//        String m = ResourceBundle.getBundle("messages", FactoryLocale.createLocale(SystemEnum.CRM)).getString(av.getNome() + "_" + r.name());

        av.setMensagem(valid.getMensagem());
        av.setTipo(r);
        av.setUrlCorrecao(urlResponseGenerator(av));
        return av;
    }

    public static String urlResponseGenerator(AcaoValidadora acao) {
        String s = null;
        switch (acao.getNome()) {
            case ASSOCIACAO_ONT:
                if (!acao.getResultado()) {
                    s = "http://10.40.196.171:7178/efikaServiceAPI/execAcao/detailed";
                }
                break;
            default:
                break;
        }
        return s;
    }

}
