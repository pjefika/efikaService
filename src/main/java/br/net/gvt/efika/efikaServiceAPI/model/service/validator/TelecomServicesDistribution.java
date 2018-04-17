/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.service.validator;

import br.net.gvt.efika.efikaServiceAPI.model.ExecDetailedRequest;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoValidadora;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoRequest;
import br.net.gvt.efika.efikaServiceAPI.model.validador.ExecucaoDetalhada;

/**
 *
 * @author G0041775 ainda a ser renomeada - sugestões aceitas...
 */
public interface TelecomServicesDistribution {

    public AcaoValidadora validacao(AcaoRequest acao) throws Exception;

    public ExecucaoDetalhada execucaoDetalhada(ExecDetailedRequest req) throws Exception;
}
