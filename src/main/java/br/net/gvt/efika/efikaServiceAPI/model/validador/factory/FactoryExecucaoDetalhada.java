/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.validador.factory;

import br.net.gvt.efika.efikaServiceAPI.model.ExecDetailedRequest;
import br.net.gvt.efika.efikaServiceAPI.model.service.validator.ValidacaoResultGenerator;
import br.net.gvt.efika.efikaServiceAPI.model.validador.ExecucaoDetalhada;
import java.util.Calendar;

/**
 *
 * @author G0041775
 */
public class FactoryExecucaoDetalhada {

    public static ExecucaoDetalhada create(ExecDetailedRequest req) throws Exception {
        ExecucaoDetalhada exec = new ExecucaoDetalhada(req.getExecucao());
        exec.setDataInicio(Calendar.getInstance().getTime());
        exec.setParametro(req.getParametro());
        exec.setSetter(req.getSetter());
        exec.setCustomer(ValidacaoResultGenerator.getCust(req.getInstancia()));

        return exec;
    }

}
