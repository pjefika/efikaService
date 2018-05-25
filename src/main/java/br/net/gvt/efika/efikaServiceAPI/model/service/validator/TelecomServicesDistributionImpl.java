/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.service.validator;

import br.net.gvt.efika.efikaServiceAPI.model.ExecDetailedRequest;
import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoResultEnum;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoValidadora;
import br.net.gvt.efika.efikaServiceAPI.model.validador.factory.FactoryAcaoValidadora;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoRequest;
import br.net.gvt.efika.efikaServiceAPI.model.validador.ExecucaoDetalhada;
import br.net.gvt.efika.efikaServiceAPI.model.validador.factory.FactoryExecucaoDetalhada;
import br.net.gvt.efika.fulltest.model.fulltest.ValidacaoResult;
import java.util.Calendar;

public class TelecomServicesDistributionImpl implements TelecomServicesDistribution {

    @Override
    public AcaoValidadora validacao(AcaoRequest acao) throws Exception {
        AcaoValidadora av = FactoryAcaoValidadora.create(acao.getAcao());
        av.setCustomer(ValidacaoResultGenerator.getCust(acao.getInstancia()));
        AcaoResultEnum r;
        ValidacaoResult valid = null;
        try {
            valid = ValidacaoResultGenerator.generate(av);
        } catch (Exception e) {
            e.printStackTrace();
            valid = new ValidacaoResult("", e.getMessage(), Boolean.FALSE, Boolean.FALSE);
        }

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
            if (valid.getNome().isEmpty()) {
                r = AcaoResultEnum.EXCEPTION;
            }
            av.setUrlCorrecao(FactoryAcaoValidadora.urlResponseGenerator(av));
        }
        if (valid.getNome().isEmpty()) {
            r = AcaoResultEnum.EXCEPTION;
        }
        av.setTipo(r);
        av.setValid(valid);
        av.setMensagem(valid.getMensagem());
        av.setConsulta(FactoryAcaoValidadora.hasConsulta(av));
        av.setDataFim(Calendar.getInstance().getTime());

        return av;
    }

    @Override
    public ExecucaoDetalhada execucaoDetalhada(ExecDetailedRequest req) throws Exception {
        ExecucaoDetalhada exec = FactoryExecucaoDetalhada.create(req);
        exec.setValid(ValidacaoResultGenerator.generate(exec));
        exec.setDataFim(Calendar.getInstance().getTime());
        return exec;
    }

}
