/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.validador.factory;

import br.net.gvt.efika.efikaServiceAPI.model.enums.ExecDetailedEnum;
import br.net.gvt.efika.efikaServiceAPI.model.validador.ExecucaoDetalhada;
import java.util.Calendar;

/**
 *
 * @author G0041775
 */
public class FactoryExecucaoDetalhada {

    public static ExecucaoDetalhada create(ExecDetailedEnum nome) throws Exception {
        ExecucaoDetalhada exec = new ExecucaoDetalhada(nome);
        exec.setDataInicio(Calendar.getInstance().getTime());
        return exec;
    }

}
