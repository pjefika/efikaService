/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.service.validator;

import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;
import br.net.gvt.efika.fulltest.model.fulltest.ValidacaoResult;
import br.net.gvt.efika.fulltest.model.telecom.properties.TelecomPropertiesEnum;
import br.net.gvt.efika.fulltest.model.telecom.properties.ValidavelAbs;

/**
 *
 * @author G0041775
 */
public class ValidacaoResultGenerator {

    public ValidacaoResultGenerator() {
    }

    public static ValidacaoResult generate(AcaoEnum a) {
        ValidacaoResult v = null;
        switch (a) {
            case CORRETOR_ESTADO_ADM_PORTA:
                v = new ValidacaoResult("Estado Administrativo da Porta", "mensagemValid", Boolean.FALSE, new ValidavelAbs(TelecomPropertiesEnum.EstadoDaPorta) {
                }, Boolean.FALSE);
                break;
            default:
                break;
        }
        return v;
    }

}
