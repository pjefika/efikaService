/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.service.validator;

import br.net.gvt.efika.efikaServiceAPI.model.ExecDetailedRequest;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoValidadora;
import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;
import br.net.gvt.efika.efikaServiceAPI.model.enums.ExecDetailedEnum;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoRequest;
import br.net.gvt.efika.efikaServiceAPI.model.validador.ExecucaoDetalhada;
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
        AcaoEnum acao = AcaoEnum.TROCA_PACOTES;
        TelecomServicesDistributionImpl instance = new TelecomServicesDistributionImpl();
        AcaoValidadora result = instance.validacao(new AcaoRequest(acao, "BHE-811OZ6Y6QU-013"));
        System.out.println(new JacksonMapper(AcaoValidadora.class).serialize(result));
    }

    /**
     * Test of execucaoDetalhada method, of class TelecomServicesDistributionImpl.
     */
    @Test
    public void testExecucaoDetalhada() throws Exception {
        System.out.println("execucaoDetalhada");
        
        ExecDetailedRequest req = new ExecDetailedRequest("5131057251", "26113822", ExecDetailedEnum.CONNECTED_DEVICES, null);
        TelecomServicesDistributionImpl instance = new TelecomServicesDistributionImpl();
        ExecucaoDetalhada result = instance.execucaoDetalhada(req);
        System.out.println("LERESULT");
        System.out.println(new JacksonMapper(ExecucaoDetalhada.class).serialize(result));
    }
    
}
