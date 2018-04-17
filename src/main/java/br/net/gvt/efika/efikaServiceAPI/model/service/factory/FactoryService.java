/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.service.factory;

import br.net.gvt.efika.efikaServiceAPI.model.service.finder.CustomerFinder;
import br.net.gvt.efika.efikaServiceAPI.model.service.finder.CustomerFinderImpl;
import br.net.gvt.efika.efikaServiceAPI.model.service.customer.EfikaCustomerService;
import br.net.gvt.efika.efikaServiceAPI.model.service.customer.EfikaCustomerServiceImpl;
import br.net.gvt.efika.efikaServiceAPI.model.service.lists.ListMakerService;
import br.net.gvt.efika.efikaServiceAPI.model.service.lists.ListMakerServiceImpl;
import br.net.gvt.efika.efikaServiceAPI.model.service.validator.TelecomServicesDistribution;
import br.net.gvt.efika.efikaServiceAPI.model.service.validator.TelecomServicesDistributionImpl;

/**
 *
 * @author G0041775
 */
public class FactoryService {

    public static CustomerFinder customerFinder() {
        return new CustomerFinderImpl();
    }

    public static EfikaCustomerService createEfikaCustomerService() throws Exception {
        return new EfikaCustomerServiceImpl();
    }

    public static ListMakerService createListMakerService() throws Exception {
        return new ListMakerServiceImpl();
    }

    public static TelecomServicesDistribution createTelecomServicesDistribution() throws Exception {
        return new TelecomServicesDistributionImpl();
    }

}
