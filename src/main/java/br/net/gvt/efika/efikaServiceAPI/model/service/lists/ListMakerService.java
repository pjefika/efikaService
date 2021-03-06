/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.service.lists;

import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoValidadora;
import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;
import java.util.List;

/**
 *
 * @author G0041775
 */
public interface ListMakerService {

    public List<AcaoEnum> listarAcoes();

    public List<AcaoValidadora> listarValidacoes(AcaoEnum acao);
}
