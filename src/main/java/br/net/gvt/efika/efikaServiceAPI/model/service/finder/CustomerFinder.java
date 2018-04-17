/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.service.finder;

import br.net.gvt.efika.efika_customer.model.customer.EfikaCustomer;
import br.net.gvt.efika.efikaServiceAPI.model.GenericRequest;
import java.util.Date;

/**
 *
 * @author G0041775
 */
public interface CustomerFinder {

    public EfikaCustomer getCustomer(GenericRequest req) throws Exception;

    public EfikaCustomer getCustomerFromHist(String instancia, Date dataLimite) throws Exception;

}
