/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.service.lists;

import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;
import java.util.Arrays;
import java.util.List;

public class ListMakerServiceImpl implements ListMakerService {

    @Override
    public List<AcaoEnum> listarAcoes() {
        return Arrays.asList(AcaoEnum.values());
    }

}
