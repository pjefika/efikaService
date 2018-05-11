/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.service.finder;

import br.net.gvt.efika.efikaServiceAPI.dao.mongo.FactoryDAO;
import br.net.gvt.efika.efika_customer.model.customer.EfikaCustomer;
import br.net.gvt.efika.efikaServiceAPI.model.GenericRequest;
import br.net.gvt.efika.efikaServiceAPI.dao.request.RequestFactory;
import br.net.gvt.efika.efika_customer.model.customer.mock.CustomerMock;
import br.net.gvt.efika.util.dao.http.Urls;
import br.net.gvt.efika.util.dao.http.factory.FactoryHttpDAOAbstract;
import java.util.Date;

public class CustomerFinderImpl implements CustomerFinder {

    @Override
    public EfikaCustomer getCustomer(GenericRequest req) throws Exception {
        FactoryHttpDAOAbstract<EfikaCustomer> fac = new FactoryHttpDAOAbstract<>(EfikaCustomer.class);
        return (EfikaCustomer) fac.createWithoutProxy().post(Urls.CADASTRO_STEALER.getUrl(),
                RequestFactory.customerRequest(req));
    }

    @Override
    public EfikaCustomer getCustomerFromHist(String instancia, Date dataLimite) throws Exception {
        String mockIT = "1156421252;1156863239;1156421252;4131496819;1156422022;"
                + "1135300853;1125252525;4131492882;1151813390;4130176173";

        if (mockIT.contains(instancia)) {
            return CustomerMock.mockIt(instancia);
        }

        try {
            return FactoryDAO.newAcaoValidadoraDAO().findRecentCustomer(instancia, dataLimite);
        } catch (Exception e) {
            return getCustomer(new GenericRequest(instancia, "efikaServiceAPI"));
        }

    }

}
