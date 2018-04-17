/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.service.validator;

import br.net.gvt.efika.efikaServiceAPI.model.ExecDetailedRequest;
import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoResultEnum;
import br.net.gvt.efika.efikaServiceAPI.model.enums.SystemEnum;
import br.net.gvt.efika.efikaServiceAPI.model.service.factory.FactoryLocale;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoValidadora;
import br.net.gvt.efika.efikaServiceAPI.model.validador.factory.FactoryAcaoValidadora;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoRequest;
import br.net.gvt.efika.efikaServiceAPI.model.validador.ExecucaoDetalhada;
import br.net.gvt.efika.efikaServiceAPI.model.validador.factory.FactoryExecucaoDetalhada;
import br.net.gvt.efika.fulltest.model.fulltest.ValidacaoResult;
import java.util.Calendar;
import java.util.ResourceBundle;

public class TelecomServicesDistributionImpl implements TelecomServicesDistribution {

    @Override
    public AcaoValidadora validacao(AcaoRequest acao) throws Exception {
        AcaoValidadora av = FactoryAcaoValidadora.create(acao.getAcao());
        av.setCustomer(ValidacaoResultGenerator.getCust(acao.getInstancia()));
        AcaoResultEnum r;
        ValidacaoResult valid = ValidacaoResultGenerator.generate(av);

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
        String m = ResourceBundle.getBundle("messages", FactoryLocale.createLocale(SystemEnum.CRM)).getString(av.getNome() + "_" + r.name());
        av.setTipo(r);
        av.setMensagem(m);
        av.setValid(valid);
        av.setDataFim(Calendar.getInstance().getTime());

        return av;
    }

    @Override
    public ExecucaoDetalhada execucaoDetalhada(ExecDetailedRequest req) throws Exception {
        ExecucaoDetalhada exec = FactoryExecucaoDetalhada.create(req.getExecucao());
        exec.setParametro(req.getParametro());
        exec.setCustomer(ValidacaoResultGenerator.getCust(req.getInstancia()));
        exec.setValid(ValidacaoResultGenerator.generate(exec));
        exec.setDataFim(Calendar.getInstance().getTime());
        return exec;
    }

}
