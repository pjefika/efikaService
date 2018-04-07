/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.service.validator;

import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;
import br.net.gvt.efika.fulltest.model.fulltest.ValidacaoResult;

/**
 *
 * @author G0041775
 */
public class ValidacaoResultGenerator {

    public ValidacaoResultGenerator() {
    }

    public ValidacaoResult generate(AcaoEnum a) {
        switch (a) {
            case CORRETOR_ESTADO_ADM_PORTA:
                break;
            default:
                break;
        }
        return new ValidacaoResult();
    }

}
