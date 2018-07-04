/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.dao.acao_validadora;

import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;
import br.net.gvt.efika.efika_customer.model.customer.EfikaCustomer;
import java.util.List;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoValidadora;
import java.util.Date;

/**
 *
 * @author g0042204
 */
public interface AcaoValidadoraDAO {

    public List<AcaoValidadora> findByCustomer(EfikaCustomer cust) throws Exception;

    public AcaoValidadora save(AcaoValidadora cert) throws Exception;

    public AcaoValidadora read(String id) throws Exception;
    
    public EfikaCustomer findRecentCustomer(String instancia, Date dataLimite) throws Exception;
    
    public AcaoValidadora findRecentExec(String instancia, AcaoEnum acao, Date dataLimite) throws Exception;

}
