/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.service.validator;

import br.net.gvt.efika.efikaServiceAPI.model.entity.AcaoValidadora;
import br.net.gvt.efika.efikaServiceAPI.model.entity.factory.FactoryAcaoValidadora;
import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;
import br.net.gvt.efika.fulltest.model.fulltest.ValidacaoResult;

public class TelecomServicesDistributionImpl implements TelecomServicesDistribution {

    @Override
    public AcaoValidadora validacao(AcaoEnum acao) throws Exception {
        ValidacaoResult v = ValidacaoResultGenerator.generate(acao);
        return FactoryAcaoValidadora.create(acao, v);
    }

}
