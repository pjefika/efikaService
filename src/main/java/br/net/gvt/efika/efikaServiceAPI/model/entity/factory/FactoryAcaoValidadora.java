/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.entity.factory;

import br.net.gvt.efika.efikaServiceAPI.model.entity.AcaoValidadora;
import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;
import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoResultEnum;
import br.net.gvt.efika.efikaServiceAPI.model.enums.SystemEnum;
import br.net.gvt.efika.efikaServiceAPI.model.service.factory.FactoryLocale;
import br.net.gvt.efika.fulltest.model.fulltest.ValidacaoResult;
import java.util.ResourceBundle;

/**
 *
 * @author G0041775
 */
public class FactoryAcaoValidadora {

    public static AcaoValidadora create(AcaoEnum nome, ValidacaoResult valid) {
        AcaoValidadora av = new AcaoValidadora(nome, valid);
        AcaoResultEnum r;

        if (valid.getFoiCorrigido() != null) {
            if (valid.getResultado()) {
                r = AcaoResultEnum.VALIDADO_OK;
            } else {
                r = valid.getFoiCorrigido() ? AcaoResultEnum.CORRIGIDO_OK : AcaoResultEnum.CORRIGIDO_NOK;
            }
        } else {
            r = valid.getResultado() ? AcaoResultEnum.VALIDADO_OK : AcaoResultEnum.VALIDADO_NOK;
        }
        String m = ResourceBundle.getBundle("messages", FactoryLocale.createLocale(SystemEnum.CRM)).getString(nome + "_" + r.name());
        av.setResultado(r);
        av.setMensagem(m);

        return av;
    }
    

}
