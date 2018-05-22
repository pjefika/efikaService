/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.service.validator;

import br.net.gvt.efika.acs.model.dto.ForceOnlineDevicesIn;
import br.net.gvt.efika.acs.model.dto.GetDeviceDataIn;
import br.net.gvt.efika.acs.model.search.SearchCriteria;
import br.net.gvt.efika.acs.model.search.SearchIn;
import br.net.gvt.efika.acs.model.service.factory.FactoryAcsService;
import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;
import static br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum.ASSOCIACAO_ONT;
import static br.net.gvt.efika.efikaServiceAPI.model.enums.ExecDetailedEnum.GET_ONTS;
import br.net.gvt.efika.efikaServiceAPI.model.service.factory.FactoryService;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoValidadora;
import br.net.gvt.efika.efikaServiceAPI.model.validador.ExecucaoDetalhada;
import br.net.gvt.efika.efika_customer.model.customer.EfikaCustomer;
import br.net.gvt.efika.efika_customer.model.customer.mock.CustomerMock;
import br.net.gvt.efika.fulltest.model.fulltest.FulltestRequest;
import br.net.gvt.efika.fulltest.model.fulltest.SetOntToOltRequest;
import br.net.gvt.efika.fulltest.model.fulltest.ValidacaoResult;
import br.net.gvt.efika.fulltest.model.telecom.properties.TelecomPropertiesEnum;
import br.net.gvt.efika.fulltest.model.telecom.properties.ValidavelAbs;
import br.net.gvt.efika.fulltest.model.telecom.properties.gpon.SerialOntGpon;
import br.net.gvt.efika.fulltest.service.factory.FactoryFulltestService;
import com.alcatel.hdm.service.nbi2.NbiDeviceData;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author G0041775
 */
public class ValidacaoResultGenerator {

    protected static ResourceBundle bundle = ResourceBundle.getBundle("messages", new Locale("co", "CO"));

    public ValidacaoResultGenerator() {
    }

    public static EfikaCustomer getCust(String instancia) throws Exception {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, -15);
        Date dataLimite = now.getTime();
        return FactoryService.customerFinder().getCustomerFromHist(instancia, dataLimite);
    }

    public static ValidacaoResult generate(AcaoValidadora a) throws Exception {
        ValidacaoResult v = null;
        SearchIn reqAcs = new SearchIn(SearchCriteria.SUBSCRIBER, a.getCustomer().getDesignador());
        reqAcs.setExecutor("efikaServiceAPI");
        List<NbiDeviceData> l = null;
        ForceOnlineDevicesIn reqAcs1 = new ForceOnlineDevicesIn();
        reqAcs1.setExecutor("efikaServiceAPI");
        Boolean isAnyOnline = null;
        String str = null;
        if (CustomerMock.mockIT().contains(a.getCustomer().getInstancia())) {
            return mockValidation(a);
        }
        switch (a.getAcao()) {
        case ASSOCIACAO_ONT:
            v = FactoryFulltestService.newConfigPortaService()
                    .getOntFromOlt(new FulltestRequest(a.getCustomer(), "efikaServiceAPI"));
            break;
        case CHECK_GERENCIA:
            v = new ValidacaoResult(a.getAcao().toString(), "Gerência disponível", FactoryFulltestService
                    .newConfigPortaService().isManageable(new FulltestRequest(a.getCustomer(), "efikaServiceAPI")),
                    null);
            break;
        case ESTADO_PORTA:
            v = FactoryFulltestService.newConfigPortaService()
                    .corretorEstadoPorta(new FulltestRequest(a.getCustomer(), "efikaServiceAPI"));
            break;
        case VLAN_BANDA:
            v = FactoryFulltestService.newConfigPortaService()
                    .corretorVlanBanda(new FulltestRequest(a.getCustomer(), "efikaServiceAPI"));
            break;
        case PROFILE:
            v = FactoryFulltestService.newConfigPortaService()
                    .corretorProfile(new FulltestRequest(a.getCustomer(), "efikaServiceAPI"));
            break;
        case VLANS_VIDEO:
            v = FactoryFulltestService.newConfigPortaService()
                    .corretorVlansVideo(new FulltestRequest(a.getCustomer(), "efikaServiceAPI"));
            break;
        case VLAN_VOIP:
            v = FactoryFulltestService.newConfigPortaService()
                    .corretorVlanVoIP(new FulltestRequest(a.getCustomer(), "efikaServiceAPI"));
            break;
        case PARAMETROS:
            v = FactoryFulltestService.newConfigPortaService()
                    .validadorParametros(new FulltestRequest(a.getCustomer(), "efikaServiceAPI"));
            break;
        case ATM:
            Boolean booleano = a.getCustomer().getRede().getModeloDslam().equalsIgnoreCase("MA5100");
            str = booleano ? "É ATM" : "Não é ATM";
            v = new ValidacaoResult("ATM", str, booleano, null);
            break;
        case WIFI_CRED:
            l = FactoryAcsService.searchService().search(reqAcs);
            reqAcs1.setDevices(l);
            isAnyOnline = FactoryAcsService.equipamentoService().forceAnyOnline(reqAcs1);
            str = isAnyOnline ? bundle.getString("onlineAcs_ok") : bundle.getString("onlineAcs_nok");
            v = new ValidacaoResult(a.getAcao().toString(), str, isAnyOnline, null);
            break;
        case REBOOT:
            l = FactoryAcsService.searchService().search(reqAcs);
            reqAcs1.setDevices(l);
            isAnyOnline = FactoryAcsService.equipamentoService().forceAnyOnline(reqAcs1);
            str = isAnyOnline ? bundle.getString("onlineAcs_ok") : bundle.getString("onlineAcs_nok");
            v = new ValidacaoResult(a.getAcao().toString(), str, isAnyOnline, null);
            break;
        case FACTORY_RESET:
            l = FactoryAcsService.searchService().search(reqAcs);
            reqAcs1.setDevices(l);
            isAnyOnline = FactoryAcsService.equipamentoService().forceAnyOnline(reqAcs1);
            str = isAnyOnline ? bundle.getString("onlineAcs_ok") : bundle.getString("onlineAcs_nok");
            v = new ValidacaoResult(a.getAcao().toString(), str, isAnyOnline, null);
            break;
        case PING:
            l = FactoryAcsService.searchService().search(reqAcs);
            reqAcs1.setDevices(l);
            isAnyOnline = FactoryAcsService.equipamentoService().forceAnyOnline(reqAcs1);
            str = isAnyOnline ? bundle.getString("onlineAcs_ok") : bundle.getString("onlineAcs_nok");
            v = new ValidacaoResult(a.getAcao().toString(), str, isAnyOnline, null);
            break;
        case LAN_DEVICES:
            l = FactoryAcsService.searchService().search(reqAcs);
            reqAcs1.setDevices(l);
            isAnyOnline = FactoryAcsService.equipamentoService().forceAnyOnline(reqAcs1);
            str = isAnyOnline ? bundle.getString("onlineAcs_ok") : bundle.getString("onlineAcs_nok");
            v = new ValidacaoResult(a.getAcao().toString(), str, isAnyOnline, null);
            break;
        case DNS:
            l = FactoryAcsService.searchService().search(reqAcs);
            reqAcs1.setDevices(l);
            isAnyOnline = FactoryAcsService.equipamentoService().forceAnyOnline(reqAcs1);
            str = isAnyOnline ? bundle.getString("onlineAcs_ok") : bundle.getString("onlineAcs_nok");
            v = new ValidacaoResult(a.getAcao().toString(), str, isAnyOnline, null);
            break;
        case WIFI_CHANNEL:
            l = FactoryAcsService.searchService().search(reqAcs);
            reqAcs1.setDevices(l);
            isAnyOnline = FactoryAcsService.equipamentoService().forceAnyOnline(reqAcs1);
            str = isAnyOnline ? bundle.getString("onlineAcs_ok") : bundle.getString("onlineAcs_nok");
            v = new ValidacaoResult(a.getAcao().toString(), str, isAnyOnline, null);
            break;
        default:
            break;

        }
        return v;
    }

    public static Object generate(ExecucaoDetalhada exec) throws Exception {
        Object v = null;

        switch (exec.getNome()) {
        case GET_ONTS:
            v = new ValidacaoResult("Onts Disponíveis", "", null, null, null,
                    FactoryFulltestService.newConfigPortaService()
                            .ontsDisponiveis(new FulltestRequest(exec.getCustomer(), "efikaServiceAPI")));
            break;
        case SET_ONT:
            v = FactoryFulltestService.newConfigPortaService().setOntToOlt(new SetOntToOltRequest(exec.getCustomer(),
                    "efikaServiceAPI", new SerialOntGpon((String) exec.getParametro())));
            break;
        case GET_WIFI:
            GetDeviceDataIn getWifi = new GetDeviceDataIn();
            getWifi.setGuid(new Long(exec.getParametro()));
            getWifi.setExecutor("efikaServiceAPI");
            v = FactoryAcsService.equipamentoService().getWifiInfo(getWifi);
            break;
        case SEEK_DEVICES:
            SearchIn reqAcs = new SearchIn(SearchCriteria.SUBSCRIBER, exec.getCustomer().getDesignador());
            reqAcs.setExecutor("efikaServiceAPI");
            v = FactoryAcsService.searchService().search(reqAcs);
            break;
        default:
            break;
        }
        return v;

    }

    public static List<ValidacaoResult> fakeGeneration(AcaoEnum a) {
        List<ValidacaoResult> l = new ArrayList<>();
        switch (a) {
        case ASSOCIACAO_ONT:
            l.add(new ValidacaoResult(a.toString(), bundle.getString("validacaoSerialOnt_ok") + " ABC123456",
                    Boolean.TRUE, new ValidavelAbs(TelecomPropertiesEnum.SerialOntGpon) {
                        public SerialOntGpon getSerial() {
                            return new SerialOntGpon("ABC123456");
                        }
                    }, Boolean.FALSE));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("validacaoSerialOnt_nok"), Boolean.FALSE,
                    new ValidavelAbs(TelecomPropertiesEnum.SerialOntGpon) {
                        public SerialOntGpon getSerial() {
                            return new SerialOntGpon("");
                        }
                    }, null));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("validacaoSerialOnt_ok") + "0123456789",
                    Boolean.TRUE, new ValidavelAbs(TelecomPropertiesEnum.SerialOntGpon) {
                        public SerialOntGpon getSerial() {
                            SerialOntGpon s = new SerialOntGpon();
                            s.setIdOnt("0123456789");
                            return s;
                        }
                    }, Boolean.FALSE));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("correcaoSerialOnt_ok") + "0123456789",
                    Boolean.FALSE, new ValidavelAbs(TelecomPropertiesEnum.SerialOntGpon) {
                        public SerialOntGpon getSerial() {
                            SerialOntGpon s = new SerialOntGpon();
                            s.setIdOnt("0123456789");
                            return s;
                        }
                    }, Boolean.TRUE));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("correcaoSerialOnt_nok"), Boolean.FALSE,
                    new ValidavelAbs(TelecomPropertiesEnum.SerialOntGpon) {
                        public SerialOntGpon getSerial() {
                            SerialOntGpon s = new SerialOntGpon();
                            s.setIdOnt("0123456789");
                            return s;
                        }
                    }, Boolean.FALSE));
            break;
        case CHECK_GERENCIA:
            l.add(new ValidacaoResult(a.toString(), "Gerência disponível", true, null));
            break;
        case ESTADO_PORTA:
            l.add(new ValidacaoResult(a.toString(), bundle.getString("validacaoEstadoOper_ok"), Boolean.TRUE, null));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("validacaoEstadoOper_nok"), Boolean.FALSE, null));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("correcaoEstadoAdm_ok"), Boolean.FALSE,
                    Boolean.TRUE));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("correcaoEstadoAdm_nok"), Boolean.FALSE,
                    Boolean.FALSE));
            break;
        case VLAN_BANDA:
            l.add(new ValidacaoResult(a.toString(), bundle.getString("validacaoVlan_ok"), Boolean.TRUE, null));
            // l.add(new ValidacaoResult(a.toString(),
            // bundle.getString("validacaoVlan_nok"), Boolean.FALSE, null));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("correcaoVlan_ok"), Boolean.FALSE, Boolean.TRUE));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("correcaoVlan_nok"), Boolean.FALSE,
                    Boolean.FALSE));
            break;
        case PROFILE:
            l.add(new ValidacaoResult(a.toString(), bundle.getString("validacaoProfile_ok"), Boolean.TRUE, null));
            // l.add(new ValidacaoResult(a.toString(),
            // bundle.getString("validacaoProfile_nok"), Boolean.FALSE, null));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("correcaoProfile_ok"), Boolean.FALSE,
                    Boolean.TRUE));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("correcaoProfile_nok"), Boolean.FALSE,
                    Boolean.FALSE));
            break;
        case VLANS_VIDEO:
            l.add(new ValidacaoResult(a.toString(), "Cliente sem TV Híbrida/IPTV.", Boolean.TRUE, null));
            l.add(new ValidacaoResult(a.toString(), "Cliente sem TV.", Boolean.TRUE, null));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("validacaoVlan_ok"), Boolean.TRUE, null));
            // l.add(new ValidacaoResult(a.toString(),
            // bundle.getString("validacaoVlan_nok"), Boolean.FALSE, null));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("correcaoVlan_ok"), Boolean.FALSE, Boolean.TRUE));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("correcaoVlan_nok"), Boolean.FALSE,
                    Boolean.FALSE));
            break;
        case VLAN_VOIP:
            l.add(new ValidacaoResult(a.toString(), "Cliente sem VoIP.", Boolean.TRUE, null));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("validacaoVlan_ok"), Boolean.TRUE, null));
            // l.add(new ValidacaoResult(a.toString(),
            // bundle.getString("validacaoVlan_nok"), Boolean.FALSE, null));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("correcaoVlan_ok"), Boolean.FALSE, Boolean.TRUE));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("correcaoVlan_nok"), Boolean.FALSE,
                    Boolean.FALSE));
            break;
        case ATM:
            l.add(new ValidacaoResult("ATM", "É ATM", Boolean.TRUE, null));
            l.add(new ValidacaoResult("ATM", "Não é ATM", Boolean.FALSE, null));
            break;
        case PARAMETROS:
            l.add(new ValidacaoResult(a.toString(), bundle.getString("validacaoParametros_ok"), Boolean.TRUE, null));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("validacaoParametros_nok"), Boolean.FALSE, null));
            break;
        case WIFI_CRED:
            l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_ok"), Boolean.TRUE, null));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_nok"), Boolean.FALSE, null));
            return l;
        case REBOOT:
            l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_ok"), Boolean.TRUE, null));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_nok"), Boolean.FALSE, null));
            return l;
        case FACTORY_RESET:
            l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_ok"), Boolean.TRUE, null));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_nok"), Boolean.FALSE, null));
            return l;
        case PING:
            l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_ok"), Boolean.TRUE, null));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_nok"), Boolean.FALSE, null));
            return l;
        case LAN_DEVICES:
            l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_ok"), Boolean.TRUE, null));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_nok"), Boolean.FALSE, null));
            return l;
        case DNS:
            l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_ok"), Boolean.TRUE, null));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_nok"), Boolean.FALSE, null));
            return l;
        case WIFI_CHANNEL:
            l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_ok"), Boolean.TRUE, null));
            l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_nok"), Boolean.FALSE, null));
            return l;
        default:
            break;

        }
        l.add(new ValidacaoResult("", "Falha ao conectar-se com o Jump Access.", Boolean.FALSE, Boolean.FALSE));
        l.add(new ValidacaoResult("", "Inventário de Rede inexistente.", Boolean.FALSE, Boolean.FALSE));
        l.add(new ValidacaoResult("", "Inventário de Rede incompleto.", Boolean.FALSE, Boolean.FALSE));
        l.add(new ValidacaoResult("", "Funcionalidade indisponível para este modelo de DSLAM/OLT.", Boolean.FALSE,
                Boolean.FALSE));
        l.add(new ValidacaoResult("",
                "Identificado Shelf sem gerência. Não foi possível completar a conexão através do protocolo utilizado pelo Efika(telnet/ssh/http(s)).",
                Boolean.FALSE, Boolean.FALSE));
        l.add(new ValidacaoResult("", "Identificado Shelf sem gerência. Não é possível contactar o equipamento.",
                Boolean.FALSE, Boolean.FALSE));
        return l;
    }

    public static ValidacaoResult mockValidation(AcaoValidadora a) {
        ValidacaoResult v = null;
        switch (a.getCustomer().getInstancia()) {
        case "1151834829":
            if (a.getAcao() == AcaoEnum.ESTADO_PORTA) {
                v = fakeGeneration(a.getAcao()).get(2);
            }
            if (a.getAcao() == AcaoEnum.ASSOCIACAO_ONT) {
                v = fakeGeneration(a.getAcao()).get(fakeGeneration(a.getAcao()).size() - 3);
            }
            if (a.getAcao() == AcaoEnum.PARAMETROS) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            break;
        case "1135300239":
            if (a.getAcao() == AcaoEnum.ESTADO_PORTA) {
                v = fakeGeneration(a.getAcao()).get(2);
            }
            if (a.getAcao() == AcaoEnum.ASSOCIACAO_ONT) {
                v = fakeGeneration(a.getAcao()).get(0);
            }
            if (a.getAcao() == AcaoEnum.CHECK_GERENCIA) {
                v = fakeGeneration(a.getAcao()).get(0);
            }
            break;
        case "1135300782":
            if (a.getAcao() == AcaoEnum.ESTADO_PORTA) {
                v = fakeGeneration(a.getAcao()).get(2);
            }
            if (a.getAcao() == AcaoEnum.PARAMETROS) {
                v = fakeGeneration(a.getAcao()).get(0);
            }
            break;
        case "1135310155":
            if (a.getAcao() == AcaoEnum.ESTADO_PORTA) {
                v = fakeGeneration(a.getAcao()).get(2);
            }
            if (a.getAcao() == AcaoEnum.ASSOCIACAO_ONT) {
                v = fakeGeneration(a.getAcao()).get(0);
            }
            if (a.getAcao() == AcaoEnum.VLAN_VOIP) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            if (a.getAcao() == AcaoEnum.VLANS_VIDEO) {
                v = fakeGeneration(a.getAcao()).get(3);
            }
            break;
        case "1136891110":
            if (a.getAcao() == AcaoEnum.ESTADO_PORTA) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            if (a.getAcao() == AcaoEnum.ASSOCIACAO_ONT) {
                v = fakeGeneration(a.getAcao()).get(3);
            }
            break;
        case "9156420321":
            if (a.getAcao() == AcaoEnum.ESTADO_PORTA) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            if (a.getAcao() == AcaoEnum.PARAMETROS) {
                v = fakeGeneration(a.getAcao()).get(0);
            }
            if (a.getAcao() == AcaoEnum.WIFI_CRED) {
                v = fakeGeneration(a.getAcao()).get(0);
            }
            break;
        case "4131495583":
            if (a.getAcao() == AcaoEnum.ESTADO_PORTA) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            break;
        case "1135302490":
            if (a.getAcao() == AcaoEnum.ESTADO_PORTA) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            break;
        case "1151842138":
            if (a.getAcao() == AcaoEnum.ESTADO_PORTA) {
                v = fakeGeneration(a.getAcao()).get(0);
            }
            if (a.getAcao() == AcaoEnum.PARAMETROS) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            if (a.getAcao() == AcaoEnum.WIFI_CRED) {
                v = fakeGeneration(a.getAcao()).get(0);
            }
            break;
        case "1156421670":
            if (a.getAcao() == AcaoEnum.ESTADO_PORTA) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            if (a.getAcao() == AcaoEnum.PARAMETROS) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            if (a.getAcao() == AcaoEnum.VLANS_VIDEO) {
                v = fakeGeneration(a.getAcao()).get(4);
            }
            break;
        case "1135302098":
            if (a.getAcao() == AcaoEnum.ESTADO_PORTA) {
                v = fakeGeneration(a.getAcao()).get(0);
            }
            if (a.getAcao() == AcaoEnum.VLAN_VOIP) {
                v = fakeGeneration(a.getAcao()).get(2);
            }
            if (a.getAcao() == AcaoEnum.VLANS_VIDEO) {
                v = fakeGeneration(a.getAcao()).get(2);
            }
            break;
        case "1135310138":
            if (a.getAcao() == AcaoEnum.ESTADO_PORTA) {
                v = fakeGeneration(a.getAcao()).get(0);
            }
            if (a.getAcao() == AcaoEnum.ASSOCIACAO_ONT) {
                v = fakeGeneration(a.getAcao()).get(0);
            }
            if (a.getAcao() == AcaoEnum.VLAN_VOIP) {
                v = fakeGeneration(a.getAcao()).get(2);
            }
            break;
        case "1156421252":
            if (a.getAcao() == AcaoEnum.PROFILE) {
                v = fakeGeneration(a.getAcao()).get(0);
            }
            if (a.getAcao() == AcaoEnum.VLAN_BANDA) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            if (a.getAcao() == AcaoEnum.PARAMETROS) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            break;
        case "4131496819":
            if (a.getAcao() == AcaoEnum.PROFILE) {
                v = fakeGeneration(a.getAcao()).get(0);
            }
            if (a.getAcao() == AcaoEnum.VLAN_BANDA) {
                v = fakeGeneration(a.getAcao()).get(0);
            }
            break;
        case "1151842070":
            if (a.getAcao() == AcaoEnum.PROFILE) {
                v = fakeGeneration(a.getAcao()).get(0);
            }
            if (a.getAcao() == AcaoEnum.VLAN_BANDA) {
                v = fakeGeneration(a.getAcao()).get(0);
            }
            break;
        case "4131522654":
            if (a.getAcao() == AcaoEnum.PROFILE) {
                v = fakeGeneration(a.getAcao()).get(0);
            }
            if (a.getAcao() == AcaoEnum.VLAN_BANDA) {
                v = fakeGeneration(a.getAcao()).get(2);
            }
            break;
        case "1156422022":
            if (a.getAcao() == AcaoEnum.PROFILE) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            if (a.getAcao() == AcaoEnum.VLAN_VOIP) {
                v = fakeGeneration(a.getAcao()).get(2);
            }
            if (a.getAcao() == AcaoEnum.VLANS_VIDEO) {
                v = fakeGeneration(a.getAcao()).get(3);
            }
            break;
        case "1135300853":
            if (a.getAcao() == AcaoEnum.PROFILE) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            if (a.getAcao() == AcaoEnum.VLAN_BANDA) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            break;
        case "1136891105":
            if (a.getAcao() == AcaoEnum.PROFILE) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            if (a.getAcao() == AcaoEnum.VLAN_BANDA) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            break;
        case "4131521805":
            if (a.getAcao() == AcaoEnum.PROFILE) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            if (a.getAcao() == AcaoEnum.VLAN_BANDA) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            break;
        case "1155230481":
            if (a.getAcao() == AcaoEnum.ASSOCIACAO_ONT) {
                v = fakeGeneration(a.getAcao()).get(0);
            }
            break;
        case "1136891024":
            if (a.getAcao() == AcaoEnum.VLAN_BANDA) {
                v = fakeGeneration(a.getAcao()).get(2);
            }
            break;
        case "1156422076":
            if (a.getAcao() == AcaoEnum.VLAN_VOIP) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            if (a.getAcao() == AcaoEnum.VLANS_VIDEO) {
                v = fakeGeneration(a.getAcao()).get(3);
            }
            break;
        case "1151837555":
            if (a.getAcao() == AcaoEnum.VLAN_VOIP) {
                v = fakeGeneration(a.getAcao()).get(2);
            }
            if (a.getAcao() == AcaoEnum.VLANS_VIDEO) {
                v = fakeGeneration(a.getAcao()).get(2);
            }
            break;
        case "1151841998":
            if (a.getAcao() == AcaoEnum.VLAN_VOIP) {
                v = fakeGeneration(a.getAcao()).get(3);
            }
            if (a.getAcao() == AcaoEnum.VLANS_VIDEO) {
                v = fakeGeneration(a.getAcao()).get(3);
            }
            break;
        case "1135301572":
            if (a.getAcao() == AcaoEnum.VLAN_VOIP) {
                v = fakeGeneration(a.getAcao()).get(3);
            }
            if (a.getAcao() == AcaoEnum.VLANS_VIDEO) {
                v = fakeGeneration(a.getAcao()).get(4);
            }
            break;
        case "1156850068":
            if (a.getAcao() == AcaoEnum.PARAMETROS) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            break;
        case "1151842073":
            if (a.getAcao() == AcaoEnum.PARAMETROS) {
                v = fakeGeneration(a.getAcao()).get(0);
            }
            break;
        case "1156420632":
            if (a.getAcao() == AcaoEnum.PARAMETROS) {
                v = fakeGeneration(a.getAcao()).get(0);
            }
            break;
        case "1135300575":
            if (a.getAcao() == AcaoEnum.PARAMETROS) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            break;
        case "1135302761":
            if (a.getAcao() == AcaoEnum.PARAMETROS) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            break;
        case "4132650103":
            if (a.getAcao() == AcaoEnum.PARAMETROS) {
                v = fakeGeneration(a.getAcao()).get(0);
            }
            break;
        case "4131492882":
            if (a.getAcao() == AcaoEnum.PARAMETROS) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            break;
        case "4130176173":
            if (a.getAcao() == AcaoEnum.PARAMETROS) {
                v = fakeGeneration(a.getAcao()).get(1);
            }
            break;
        case "1148674418":
            if (a.getAcao() == AcaoEnum.VLANS_VIDEO) {
                v = fakeGeneration(a.getAcao()).get(3);
            }
            break;
        case "1148678349":
            if (a.getAcao() == AcaoEnum.VLANS_VIDEO) {
                v = fakeGeneration(a.getAcao()).get(3);
            }
            break;
        default:
            break;
        }
        return v;
    }
}
