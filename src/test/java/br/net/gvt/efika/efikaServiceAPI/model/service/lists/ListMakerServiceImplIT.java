/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.service.lists;

import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoValidadora;
import br.net.gvt.efika.util.json.JacksonMapper;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author G0041775
 */
public class ListMakerServiceImplIT {

    public ListMakerServiceImplIT() {
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
     * Test of listarAcoes method, of class ListMakerServiceImpl.
     */
    @Test
    public void testListarAcoes() throws Exception {
        System.out.println("listarAcoes");
        ListMakerServiceImpl instance = new ListMakerServiceImpl();

        List<AcaoEnum> result = instance.listarAcoes();
        System.out.println(new JacksonMapper(List.class).serialize(result));
    }

    /**
     * Test of listarValidacoes method, of class ListMakerServiceImpl.
     */
    @Test
    public void testListarValidacoes() throws Exception {
        System.out.println("listarValidacoes");

//        AcaoEnum[] acoes = AcaoEnum.values();
//        for (int i = 0; i < acoes.length; i++) {
//            AcaoEnum acao = acoes[i];
//            ListMakerServiceImpl instance = new ListMakerServiceImpl();
//            List<AcaoValidadora> result = instance.listarValidacoes(acao);
//            System.out.println(new JacksonMapper(List.class).serialize(result));
//        }
        AcaoEnum acao = AcaoEnum.ASSOCIACAO_ONT;
        ListMakerServiceImpl instance = new ListMakerServiceImpl();
        List<AcaoValidadora> result = instance.listarValidacoes(acao);
        System.out.println(new JacksonMapper(List.class).serialize(result));
    }

}
