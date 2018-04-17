/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.dao.acao_validadora;

import br.net.gvt.efika.efika_customer.model.customer.EfikaCustomer;
import java.util.List;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoValidadora;
import br.net.gvt.efika.mongo.dao.AbstractMongoDAO;
import br.net.gvt.efika.mongo.dao.MongoEndpointEnum;
import java.util.Date;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.FindOptions;

/**
 *
 * @author G0041775
 */
public class AcaoValidadoraDAOImpl extends AbstractMongoDAO<AcaoValidadora> implements AcaoValidadoraDAO {

    public AcaoValidadoraDAOImpl() {
        super(MongoEndpointEnum.MONGO.getIp(), "efikaServiceAPI", AcaoValidadora.class);
    }

    @Override
    public List<AcaoValidadora> findByCustomer(EfikaCustomer cust) throws Exception {
        return getDatastore().createQuery(AcaoValidadora.class)
                .field("customer.instancia")
                .equal(cust.getInstancia())
                .order("-dataFim")
                .asList(new FindOptions()
                        .limit(10));
    }

    @Override
    public AcaoValidadora read(String id) throws Exception {
        return super.read(new ObjectId(id));
    }

    @Override
    public EfikaCustomer findRecentCustomer(String instancia, Date dataLimite) throws Exception {
        return getDatastore().createQuery(AcaoValidadora.class)
                .field("customer.instancia")
                .equal(instancia)
                .field("dataFim")
                .greaterThan(dataLimite)
                .get()
                .getCustomer();
    }

}
