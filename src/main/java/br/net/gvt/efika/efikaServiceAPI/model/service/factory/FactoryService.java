/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.service.factory;

import br.net.gvt.efika.efikaServiceAPI.model.service.finder.CustomerFinder;
import br.net.gvt.efika.efikaServiceAPI.model.service.finder.CustomerFinderImpl;
import br.net.gvt.efika.efikaServiceAPI.model.service.certification.impl.CertificationServiceImpl;
import br.net.gvt.efika.efikaServiceAPI.model.service.certification.impl.CertificationService;
import br.net.gvt.efika.efikaServiceAPI.model.service.customer.EfikaCustomerService;
import br.net.gvt.efika.efikaServiceAPI.model.service.customer.EfikaCustomerServiceImpl;
import br.net.gvt.efika.efikaServiceAPI.model.service.lists.ListMakerService;
import br.net.gvt.efika.efikaServiceAPI.model.service.lists.ListMakerServiceImpl;

/**
 *
 * @author G0041775
 */
public class FactoryService {

    public static CustomerFinder customerFinder() {
        return new CustomerFinderImpl();
    }

    public static CertificationService certSrvc() {
        return new CertificationServiceImpl();
    }

    public static EfikaCustomerService createEfikaCustomerService() throws Exception {
        return new EfikaCustomerServiceImpl();
    }

    public static ListMakerService createListMakerService() throws Exception {
        return new ListMakerServiceImpl();
    }

}
