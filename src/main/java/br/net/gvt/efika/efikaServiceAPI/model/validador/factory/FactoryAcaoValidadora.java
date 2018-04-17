/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.validador.factory;

import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoValidadora;
import java.util.Calendar;

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

}
