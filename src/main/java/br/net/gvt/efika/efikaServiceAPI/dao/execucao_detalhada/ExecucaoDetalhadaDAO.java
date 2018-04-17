/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.dao.execucao_detalhada;

import br.net.gvt.efika.efika_customer.model.customer.EfikaCustomer;
import java.util.List;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoValidadora;
import br.net.gvt.efika.efikaServiceAPI.model.validador.ExecucaoDetalhada;
import java.util.Date;

/**
 *
 * @author g0042204
 */
public interface ExecucaoDetalhadaDAO {

    public List<ExecucaoDetalhada> findByCustomer(EfikaCustomer cust) throws Exception;

    public ExecucaoDetalhada save(ExecucaoDetalhada cert) throws Exception;

    public ExecucaoDetalhada read(String id) throws Exception;
    
    public EfikaCustomer findRecentCustomer(String instancia, Date dataLimite) throws Exception;

}
