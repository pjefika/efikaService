/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.service.validator;

import br.net.gvt.efika.efikaServiceAPI.model.entity.AcaoValidadora;
import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;
import br.net.gvt.efika.util.json.JacksonMapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author G0041775
 */
public class TelecomServicesDistributionImplIT {
    
    public TelecomServicesDistributionImplIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of validacao method, of class TelecomServicesDistributionImpl.
     */
    @Test
    public void testValidacao() throws Exception {
        System.out.println("validacao");
        AcaoEnum acao = AcaoEnum.CORRETOR_ESTADO_ADM_PORTA;
        TelecomServicesDistributionImpl instance = new TelecomServicesDistributionImpl();
        AcaoValidadora result = instance.validacao(acao);
        System.out.println(new JacksonMapper(AcaoValidadora.class).serialize(result));
    }
    
}
