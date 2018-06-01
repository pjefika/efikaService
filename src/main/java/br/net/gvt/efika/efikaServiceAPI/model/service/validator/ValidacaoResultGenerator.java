/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.service.validator;

import br.net.gvt.efika.acs.model.device.dns.Dns;
import br.net.gvt.efika.acs.model.device.interfacestatistics.InterfaceStatistics;
import br.net.gvt.efika.acs.model.device.lanhost.LanDevice;
import br.net.gvt.efika.acs.model.device.wan.WanInfo;
import br.net.gvt.efika.acs.model.device.wifi.WifiNets;
import br.net.gvt.efika.acs.model.dto.DetailIn;
import br.net.gvt.efika.acs.model.dto.FirmwareUpdateIn;
import br.net.gvt.efika.acs.model.dto.ForceOnlineDevicesIn;
import br.net.gvt.efika.acs.model.dto.GetDeviceDataIn;
import br.net.gvt.efika.acs.model.dto.GetPhoneNumberIn;
import br.net.gvt.efika.acs.model.dto.SetDnsIn;
import br.net.gvt.efika.acs.model.dto.SetWifiIn;
import br.net.gvt.efika.acs.model.dto.SipDiagnosticsIn;
import br.net.gvt.efika.acs.model.search.SearchCriteria;
import br.net.gvt.efika.acs.model.search.SearchIn;
import br.net.gvt.efika.acs.model.service.factory.FactoryAcsService;
import br.net.gvt.efika.efikaServiceAPI.dao.acao_validadora.AcaoValidadoraDAO;
import br.net.gvt.efika.efikaServiceAPI.dao.execucao_detalhada.ExecucaoDetalhadaDAO;
import br.net.gvt.efika.efikaServiceAPI.dao.factory.FactoryDAO;
import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;
import static br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum.ASSOCIACAO_ONT;
import br.net.gvt.efika.efikaServiceAPI.model.enums.ExecDetailedEnum;
import static br.net.gvt.efika.efikaServiceAPI.model.enums.ExecDetailedEnum.GET_ONTS;
import br.net.gvt.efika.efikaServiceAPI.model.service.factory.FactoryService;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoValidadora;
import br.net.gvt.efika.efikaServiceAPI.model.validador.ExecucaoDetalhada;
import br.net.gvt.efika.efika_customer.model.customer.EfikaCustomer;
import br.net.gvt.efika.efika_customer.model.customer.enums.OrigemPlanta;
import br.net.gvt.efika.efika_customer.model.customer.enums.TipoRede;
import br.net.gvt.efika.efika_customer.model.customer.mock.CustomerMock;
import br.net.gvt.efika.fulltest.model.fulltest.FulltestRequest;
import br.net.gvt.efika.fulltest.model.fulltest.SetOntToOltRequest;
import br.net.gvt.efika.fulltest.model.fulltest.ValidacaoResult;
import br.net.gvt.efika.fulltest.model.telecom.properties.TelecomPropertiesEnum;
import br.net.gvt.efika.fulltest.model.telecom.properties.ValidavelAbs;
import br.net.gvt.efika.fulltest.model.telecom.properties.gpon.SerialOntGpon;
import br.net.gvt.efika.fulltest.model.telecom.properties.metalico.TabelaRedeMetalico;
import br.net.gvt.efika.fulltest.service.factory.FactoryFulltestService;
import com.alcatel.hdm.service.nbi2.NbiDeviceData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author G0041775
 */
public class ValidacaoResultGenerator {

    protected static ResourceBundle bundle = ResourceBundle.getBundle("messages", new Locale("co", "CO"));

    private static AcaoValidadoraDAO acaoDao = FactoryDAO.acaoDao();

    private static ExecucaoDetalhadaDAO execDao = FactoryDAO.execDao();

    private static ObjectMapper mapper = new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES,
            true);

    public ValidacaoResultGenerator() {
    }

    public static EfikaCustomer getCust(String instancia) throws Exception {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, -15);
        Date dataLimite = now.getTime();
        return FactoryService.customerFinder().getCustomerFromHist(instancia, dataLimite);
    }

    public static Boolean checkRecentSets(String instancia, ExecDetailedEnum exec) throws Exception {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, -15);
        Date dataLimite = now.getTime();
        return execDao.findRecentExec(instancia, exec, dataLimite) != null;
    }

    public static ExecucaoDetalhada getRecentSets(String instancia, ExecDetailedEnum exec) throws Exception {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, -15);
        Date dataLimite = now.getTime();
        return execDao.findRecentExec(instancia, exec, dataLimite);
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
                if (!checkRecentSets(a.getCustomer().getInstancia(), ExecDetailedEnum.SET_WIFI)) {
                    l = FactoryAcsService.searchService().search(reqAcs);
                    reqAcs1.setDevices(l);
                    isAnyOnline = FactoryAcsService.equipamentoService().forceAnyOnline(reqAcs1);
                    str = isAnyOnline ? bundle.getString("onlineAcs_ok") : bundle.getString("onlineAcs_nok");
                    v = new ValidacaoResult(a.getAcao().toString(), str, isAnyOnline, null);
                } else {
                    v = new ValidacaoResult(a.getAcao().toString(), "Foi executada alteração em Wifi recentemente.",
                            Boolean.TRUE, null);
                }
                break;
            case REBOOT:
                if (!checkRecentSets(a.getCustomer().getInstancia(), ExecDetailedEnum.REBOOT_DEVICE)) {
                    l = FactoryAcsService.searchService().search(reqAcs);
                    reqAcs1.setDevices(l);
                    isAnyOnline = FactoryAcsService.equipamentoService().forceAnyOnline(reqAcs1);
                    str = isAnyOnline ? bundle.getString("onlineAcs_ok") : bundle.getString("onlineAcs_nok");
                    v = new ValidacaoResult(a.getAcao().toString(), str, isAnyOnline, null);
                } else {
                    ValidacaoResult vr = (ValidacaoResult) getRecentSets(a.getCustomer().getInstancia(),
                            ExecDetailedEnum.REBOOT_DEVICE).getValid();
                    Boolean deucertoreboot = vr.getResultado();
                    str = deucertoreboot ? "Foi executado Reboot recentemente."
                            : "Houve falha ao tentar executar Reboot recentemente.";
                    v = new ValidacaoResult(a.getAcao().toString(), str, deucertoreboot, deucertoreboot);
                }
                break;
            case FACTORY_RESET:
                if (!checkRecentSets(a.getCustomer().getInstancia(), ExecDetailedEnum.FACTORY_RESET_DEVICE)) {
                    l = FactoryAcsService.searchService().search(reqAcs);
                    reqAcs1.setDevices(l);
                    isAnyOnline = FactoryAcsService.equipamentoService().forceAnyOnline(reqAcs1);
                    str = isAnyOnline ? bundle.getString("onlineAcs_ok") : bundle.getString("onlineAcs_nok");
                    v = new ValidacaoResult(a.getAcao().toString(), str, isAnyOnline, null);
                } else {
                    ValidacaoResult vr = (ValidacaoResult) getRecentSets(a.getCustomer().getInstancia(),
                            ExecDetailedEnum.FACTORY_RESET_DEVICE).getValid();
                    Boolean deucertoreset = vr.getResultado();
                    str = deucertoreset ? "Foi executado Reset de Fábrica recentemente."
                            : "Houve falha ao tentar executar Reset de Fábrica recentemente.";
                    v = new ValidacaoResult(a.getAcao().toString(), str, deucertoreset, deucertoreset);
                }
                break;
            case PING:
                if (!checkRecentSets(a.getCustomer().getInstancia(), ExecDetailedEnum.PING)) {
                    l = FactoryAcsService.searchService().search(reqAcs);
                    reqAcs1.setDevices(l);
                    isAnyOnline = FactoryAcsService.equipamentoService().forceAnyOnline(reqAcs1);
                    str = isAnyOnline ? bundle.getString("onlineAcs_ok") : bundle.getString("onlineAcs_nok");
                    v = new ValidacaoResult(a.getAcao().toString(), str, isAnyOnline, null);
                } else {
                    ValidacaoResult vr = (ValidacaoResult) getRecentSets(a.getCustomer().getInstancia(),
                            ExecDetailedEnum.PING).getValid();
                    Boolean deucertoping = vr.getResultado();
                    str = deucertoping ? "Foi realizado Ping recentemente."
                            : "Houve falha ao tentar realizar Ping recentemente.";
                    v = new ValidacaoResult(a.getAcao().toString(), str, deucertoping, isAnyOnline);
                }
                break;
            case LAN_DEVICES:
                l = FactoryAcsService.searchService().search(reqAcs);
                reqAcs1.setDevices(l);
                isAnyOnline = FactoryAcsService.equipamentoService().forceAnyOnline(reqAcs1);
                str = isAnyOnline ? bundle.getString("onlineAcs_ok") : bundle.getString("onlineAcs_nok");
                v = new ValidacaoResult(a.getAcao().toString(), str, isAnyOnline, null);
                break;
            case DNS:
                if (!checkRecentSets(a.getCustomer().getInstancia(), ExecDetailedEnum.SET_DNS)) {
                    l = FactoryAcsService.searchService().search(reqAcs);
                    reqAcs1.setDevices(l);
                    isAnyOnline = FactoryAcsService.equipamentoService().forceAnyOnline(reqAcs1);
                    str = isAnyOnline ? bundle.getString("onlineAcs_ok") : bundle.getString("onlineAcs_nok");
                    v = new ValidacaoResult(a.getAcao().toString(), str, isAnyOnline, null);
                } else {
                    v = new ValidacaoResult(a.getAcao().toString(), "Foi executada alteração de DNS recentemente.", true,
                            null);
                }

                break;
            case WIFI_CHANNEL:
                l = FactoryAcsService.searchService().search(reqAcs);
                reqAcs1.setDevices(l);
                isAnyOnline = FactoryAcsService.equipamentoService().forceAnyOnline(reqAcs1);
                str = isAnyOnline ? bundle.getString("onlineAcs_ok") : bundle.getString("onlineAcs_nok");
                v = new ValidacaoResult(a.getAcao().toString(), str, isAnyOnline, null);
                break;
            case WIFI_STATUS:
                l = FactoryAcsService.searchService().search(reqAcs);
                reqAcs1.setDevices(l);
                isAnyOnline = FactoryAcsService.equipamentoService().forceAnyOnline(reqAcs1);
                str = isAnyOnline ? bundle.getString("onlineAcs_ok") : bundle.getString("onlineAcs_nok");
                v = new ValidacaoResult(a.getAcao().toString(), str, isAnyOnline, null);
                break;
            case FIRMWARE:
                if (!checkRecentSets(a.getCustomer().getInstancia(), ExecDetailedEnum.FIRMWARE_UPDATE)) {
                    l = FactoryAcsService.searchService().search(reqAcs);
                    reqAcs1.setDevices(l);
                    isAnyOnline = FactoryAcsService.equipamentoService().forceAnyOnline(reqAcs1);
                    str = isAnyOnline ? bundle.getString("onlineAcs_ok") : bundle.getString("onlineAcs_nok");
                    v = new ValidacaoResult(a.getAcao().toString(), str, isAnyOnline, null);
                } else {
                    ValidacaoResult vr = (ValidacaoResult) getRecentSets(a.getCustomer().getInstancia(),
                            ExecDetailedEnum.FIRMWARE_UPDATE).getValid();
                    Boolean deucertofirmware = vr.getResultado();
                    str = deucertofirmware ? "Foi realizado Atualização de Firmware recentemente."
                            : "Houve falha ao tentar realizar Atualização de Firmware recentemente.";
                    v = new ValidacaoResult(a.getAcao().toString(), str, deucertofirmware, isAnyOnline);
                }
                break;
            case TROCA_PACOTES:
                Boolean hasTraffic = false;
                if (a.getCustomer().getRede().getTipo() == TipoRede.METALICA) {
                    TabelaRedeMetalico first = (TabelaRedeMetalico) FactoryFulltestService.newConfigPortaService()
                            .confiabilidadeRede(new FulltestRequest(a.getCustomer(), "efikaServiceAPI")).getResult();
                    Thread.sleep(3000);
                    TabelaRedeMetalico second = (TabelaRedeMetalico) FactoryFulltestService.newConfigPortaService()
                            .confiabilidadeRede(new FulltestRequest(a.getCustomer(), "efikaServiceAPI")).getResult();

                    hasTraffic = second.getPctDown().compareTo(first.getPctDown()) > 0
                            || second.getPctUp().compareTo(first.getPctUp()) > 0;
                } else {
                    l = FactoryAcsService.searchService().search(reqAcs);

                    List<NbiDeviceData> l1 = mapper.convertValue(l, new TypeReference<List<NbiDeviceData>>() {
                    });
                    reqAcs1.setDevices(l);
                    if (FactoryAcsService.equipamentoService().forceAnyOnline(reqAcs1)) {
                        GetDeviceDataIn getWan = new GetDeviceDataIn();
                        getWan.setExecutor("efikaServiceAPI");
                        /**
                         * refact using sipdiag
                         */
                        NbiDeviceData device = null;
                        for (NbiDeviceData d : l1) {
                            GetPhoneNumberIn getPhoneIn = new GetPhoneNumberIn();
                            getPhoneIn.setGuid(d.getDeviceGUID());
                            getPhoneIn.setExecutor("efikaServiceAPI");
                            if (FactoryAcsService.equipamentoService().getPhoneNumber(getPhoneIn).getPhoneNumber()
                                    .contains(a.getCustomer().getInstancia())) {
                                device = d;
                            }
                        }
                        Long guid = device == null ? l1.get(0).getDeviceGUID() : device.getDeviceGUID();
                        getWan.setGuid(guid);
                        WanInfo first = FactoryAcsService.equipamentoService().getWanInfo(getWan);
                        Thread.sleep(4000);
                        WanInfo second = FactoryAcsService.equipamentoService().getWanInfo(getWan);
                        hasTraffic = new BigInteger(second.getBytesReceived())
                                .compareTo(new BigInteger(first.getBytesReceived())) > 0
                                || new BigInteger(second.getBytesSent())
                                        .compareTo(new BigInteger(first.getBytesSent())) > 0;

                    }
                }
                str = hasTraffic ? bundle.getString("trafegoPct_ok") : bundle.getString("trafegoPct_nok");
                v = new ValidacaoResult(a.getAcao().toString(), str, hasTraffic, null);
                break;
            case T38:

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
            case SET_WIFI:
                if (exec.getCustomer().getInstancia().equalsIgnoreCase("9156420321")
                        || exec.getCustomer().getInstancia().equalsIgnoreCase("1151842138")) {
                    v = new WifiNets();
                } else {
                    SetWifiIn setWifi = new SetWifiIn();
                    setWifi.setGuid(new Long(exec.getParametro()));
                    setWifi.setExecutor("efikaServiceAPI");
                    v = FactoryAcsService.equipamentoService().setWifiInfo(setWifi);
                }
                break;
            case REBOOT_DEVICE:
                if (exec.getCustomer().getInstancia().equalsIgnoreCase("1157422076")
                        || exec.getCustomer().getInstancia().equalsIgnoreCase("1135301572")) {
                    v = new ValidacaoResult("Reboot", "", Boolean.TRUE, null);
                } else {
                    if (exec.getCustomer().getInstancia().equalsIgnoreCase("1135310155")) {
                        v = new ValidacaoResult("Reboot", "", Boolean.FALSE, null);
                    } else {
                        GetDeviceDataIn getDeviceIn = new GetDeviceDataIn();
                        getDeviceIn.setGuid(new Long(exec.getParametro()));
                        getDeviceIn.setExecutor("efikaServiceAPI");
                        v = new ValidacaoResult("Reboot", "", FactoryAcsService.equipamentoService().reboot(getDeviceIn),
                                null);
                    }
                }

                break;
            case PING:
                if (exec.getCustomer().getInstancia().equalsIgnoreCase("9156420321")) {
                    v = new ValidacaoResult("Ping", "", Boolean.FALSE, null);
                } else {
                    v = new ValidacaoResult("Ping", "", Boolean.TRUE, null);
                }

                break;
            case CONNECTED_DEVICES:
                GetDeviceDataIn getDeviceIn = new GetDeviceDataIn();
                getDeviceIn.setGuid(new Long(exec.getParametro()));
                getDeviceIn.setExecutor("efikaServiceAPI");
                List<LanDevice> devices = FactoryAcsService.equipamentoService().getLanHosts(getDeviceIn);
                List<InterfaceStatistics> interfaceStatistics = FactoryAcsService.equipamentoService()
                        .getInterfaceStatistics(getDeviceIn);
                v = new Object() {
                    public List<LanDevice> getLanDevices() {
                        return devices;
                    }

                    public List<InterfaceStatistics> getInterfaceStatistics() {
                        return interfaceStatistics;
                    }
                };
                break;
            case ACTIVATE_WIFI:
                GetDeviceDataIn activateWifiIn = new GetDeviceDataIn();
                activateWifiIn.setExecutor("efikaServiceAPI");
                activateWifiIn.setGuid(new Long(exec.getParametro()));
                v = FactoryAcsService.equipamentoService().activateWifi(activateWifiIn);
                break;
            case GET_DNS:
                if (exec.getCustomer().getInstancia().equalsIgnoreCase("1156421670")) {
                    v = new Dns("200.204.1.4,200.204.1.138");
                } else if (exec.getCustomer().getInstancia().equalsIgnoreCase("1148674418")) {
                    v = new Dns("200.175.5.139,200.175.89.139");
                } else if (exec.getCustomer().getInstancia().equalsIgnoreCase("1151842070")) {
                    v = new Dns("200.204.1.9,200.204.1.137");
                } else if (exec.getCustomer().getInstancia().equalsIgnoreCase("1135310138")) {
                    v = new Dns("");
                } else if (exec.getCustomer().getInstancia().equalsIgnoreCase("1136891105")) {
                    v = new Dns("");
                } else {
                    GetDeviceDataIn getDnsIn = new GetDeviceDataIn();
                    getDnsIn.setExecutor("efikaServiceAPI");
                    getDnsIn.setGuid(new Long(exec.getParametro()));
                    v = FactoryAcsService.equipamentoService().getDns(getDnsIn);
                }

                break;
            case SET_DNS:
                if (exec.getCustomer().getInstancia().equalsIgnoreCase("1151842070")) {
                    v = new Dns("200.204.1.4,200.204.1.138");
                } else if (exec.getCustomer().getInstancia().equalsIgnoreCase("1135310138")) {
                    v = new Dns("200.175.5.139,200.175.89.139");
                } else if (exec.getCustomer().getInstancia().equalsIgnoreCase("1148674418")) {
                    v = new Dns("200.175.5.139,200.175.89.139");
                } else if (exec.getCustomer().getInstancia().equalsIgnoreCase("1136891105")) {
                    v = new Dns("200.204.1.4,200.204.1.138");
                } else {
                    SetDnsIn setDnsIn = new SetDnsIn();
                    setDnsIn.setExecutor("efikaServiceAPI");
                    setDnsIn.setGuid(new Long(exec.getParametro()));
                    String dnsServers = exec.getCustomer().getRede().getPlanta() == OrigemPlanta.VIVO2
                            ? "200.175.5.139,200.175.89.139"
                            : "200.204.1.4,200.204.1.138";
                    setDnsIn.setDns(new Dns(dnsServers));
                    v = new ValidacaoResult("Set DNS", "", FactoryAcsService.equipamentoService().setDns(setDnsIn), null);
                }
                break;
            case FACTORY_RESET_DEVICE:

                if (exec.getCustomer().getInstancia().equalsIgnoreCase("1151842138")) {
                    v = new ValidacaoResult("FactoryReset", "", Boolean.TRUE, null);
                } else {
                    if (exec.getCustomer().getInstancia().equalsIgnoreCase("1148681918")) {
                        v = new ValidacaoResult("FactoryReset", "", Boolean.FALSE, null);
                    } else {
                        // GetDeviceDataIn factoryIn = new GetDeviceDataIn();
                        // factoryIn.setExecutor("efikaServiceAPI");
                        // factoryIn.setGuid(new Long(exec.getParametro()));
                        // v = FactoryAcsService.equipamentoService().factoryReset(factoryIn);
                        GetDeviceDataIn factoryIn = new GetDeviceDataIn();
                        factoryIn.setExecutor("efikaServiceAPI");
                        factoryIn.setGuid(new Long(exec.getParametro()));
                        v = new ValidacaoResult("Factory Reset", "", FactoryAcsService.equipamentoService().factoryReset(factoryIn), null);
                    }
                }

                break;
            case FIRMWARE_UPDATE:
                FirmwareUpdateIn firmwareIn = new FirmwareUpdateIn();
                firmwareIn.setExecutor("efikaServiceAPI");
                firmwareIn.setGuid(new Long(exec.getParametro()));
                v = new ValidacaoResult("Firmware Update", "", FactoryAcsService.equipamentoService().firmwareUpdate(firmwareIn), null);
                break;
            case GET_FIRMWARE:
                DetailIn detailIn = new DetailIn();
                detailIn.setExecutor("efikaServiceAPI");
                detailIn.setGuid(new Long(exec.getParametro()));
                v = FactoryAcsService.equipamentoService().getDetail(detailIn).getFirmware();
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
                l.add(new ValidacaoResult(a.toString(), "Foi executada alteração em Wifi recentemente.", Boolean.TRUE,
                        null));
                return l;
            case REBOOT:
                l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_ok"), Boolean.TRUE, null));
                l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_nok"), Boolean.FALSE, null));
                l.add(new ValidacaoResult(a.toString(), "Foi executado Reboot recentemente.", Boolean.TRUE, null));
                l.add(new ValidacaoResult(a.toString(), "Houve falha ao tentar executar Reboot recentemente.",
                        Boolean.FALSE, Boolean.FALSE));
                return l;
            case FACTORY_RESET:
                l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_ok"), Boolean.TRUE, null));
                l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_nok"), Boolean.FALSE, null));
                l.add(new ValidacaoResult(a.toString(), "Foi executado Reset de Fábrica recentemente.", Boolean.TRUE,
                        null));
                l.add(new ValidacaoResult(a.toString(), "Houve falha ao tentar executar Reset de Fábrica recentemente.",
                        Boolean.FALSE, Boolean.FALSE));
                return l;
            case PING:
                l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_ok"), Boolean.TRUE, null));
                l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_nok"), Boolean.FALSE, null));
                l.add(new ValidacaoResult(a.toString(), "Foi realizado Ping recentemente.", Boolean.TRUE, null));
                l.add(new ValidacaoResult(a.toString(), "Houve falha ao tentar realizar Ping recentemente.", Boolean.FALSE,
                        Boolean.FALSE));
                return l;
            case LAN_DEVICES:
                l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_ok"), Boolean.TRUE, null));
                l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_nok"), Boolean.FALSE, null));
                return l;
            case DNS:
                l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_ok"), Boolean.TRUE, null));
                l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_nok"), Boolean.FALSE, null));
                l.add(new ValidacaoResult(a.toString(), "Foi executada alteração de DNS recentemente.", Boolean.TRUE,
                        null));
                return l;
            case WIFI_CHANNEL:
                l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_ok"), Boolean.TRUE, null));
                l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_nok"), Boolean.FALSE, null));
                return l;
            case WIFI_STATUS:
                l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_ok"), Boolean.TRUE, null));
                l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_nok"), Boolean.FALSE, null));
                return l;
            case FIRMWARE:
                l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_ok"), Boolean.TRUE, null));
                l.add(new ValidacaoResult(a.toString(), bundle.getString("onlineAcs_nok"), Boolean.FALSE, null));
                l.add(new ValidacaoResult(a.toString(), "Foi executado Atualização de Firmware recentemente.", Boolean.TRUE,
                        null));
                l.add(new ValidacaoResult(a.toString(),
                        "Houve falha ao tentar executar Atualização de Firmware recentemente.", Boolean.FALSE,
                        Boolean.FALSE));
                return l;
            case TROCA_PACOTES:
                l.add(new ValidacaoResult(a.toString(), bundle.getString("trafegoPct_ok"), Boolean.TRUE, null));
                l.add(new ValidacaoResult(a.toString(), bundle.getString("trafegoPct_nok"), Boolean.FALSE, null));
                break;
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
                if (a.getAcao() == AcaoEnum.REBOOT) {
                    try {
                        if (checkRecentSets("1135310155", ExecDetailedEnum.REBOOT_DEVICE)) {
                            ValidacaoResult vr = (ValidacaoResult) getRecentSets("1135310155",
                                    ExecDetailedEnum.REBOOT_DEVICE).getValid();
                            Boolean deucertoreboot = vr.getResultado();
                            if (!deucertoreboot) {
                                v = fakeGeneration(a.getAcao()).get(3);
                            } else {
                                v = fakeGeneration(a.getAcao()).get(2);
                            }
                        } else {
                            v = fakeGeneration(a.getAcao()).get(0);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        v = fakeGeneration(a.getAcao()).get(0);
                    }

                }
                if (a.getAcao() == AcaoEnum.DNS) {
                    v = fakeGeneration(a.getAcao()).get(1);
                }
                if (a.getAcao() == AcaoEnum.PING) {
                    v = fakeGeneration(a.getAcao()).get(0);
                }
                break;
            case "1136891110":
                if (a.getAcao() == AcaoEnum.ESTADO_PORTA) {
                    v = fakeGeneration(a.getAcao()).get(1);
                }
                if (a.getAcao() == AcaoEnum.ASSOCIACAO_ONT) {
                    v = fakeGeneration(a.getAcao()).get(3);
                }
                if (a.getAcao() == AcaoEnum.DNS) {
                    v = fakeGeneration(a.getAcao()).get(0);
                }
                break;
            case "9156420321":
                if (a.getAcao() == AcaoEnum.ESTADO_PORTA) {
                    v = fakeGeneration(a.getAcao()).get(1);
                }
                if (a.getAcao() == AcaoEnum.PARAMETROS) {
                    v = fakeGeneration(a.getAcao()).get(0);
                }
                if (a.getAcao() == AcaoEnum.PING) {
                    try {
                        if (checkRecentSets("9156420321", ExecDetailedEnum.PING)) {
                            ValidacaoResult vr = (ValidacaoResult) getRecentSets("9156420321", ExecDetailedEnum.PING)
                                    .getValid();
                            Boolean deucertoping = vr.getResultado();
                            if (!deucertoping) {
                                v = fakeGeneration(a.getAcao()).get(3);
                            } else {
                                v = fakeGeneration(a.getAcao()).get(2);
                            }
                        } else {
                            v = fakeGeneration(a.getAcao()).get(0);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        v = fakeGeneration(a.getAcao()).get(0);
                    }
                }
                if (a.getAcao() == AcaoEnum.WIFI_CRED) {

                    try {
                        v = checkRecentSets("9156420321", ExecDetailedEnum.SET_WIFI) ? fakeGeneration(a.getAcao()).get(2)
                                : fakeGeneration(a.getAcao()).get(0);
                    } catch (Exception ex) {
                        v = fakeGeneration(a.getAcao()).get(0);
                        Logger.getLogger(ValidacaoResultGenerator.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
                if (a.getAcao() == AcaoEnum.PING) {
                    v = fakeGeneration(a.getAcao()).get(0);
                }
                if (a.getAcao() == AcaoEnum.WIFI_CRED) {
                    try {
                        v = checkRecentSets("1151842138", ExecDetailedEnum.SET_WIFI) ? fakeGeneration(a.getAcao()).get(2)
                                : fakeGeneration(a.getAcao()).get(0);
                    } catch (Exception ex) {
                        v = fakeGeneration(a.getAcao()).get(0);
                        Logger.getLogger(ValidacaoResultGenerator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (a.getAcao() == AcaoEnum.FACTORY_RESET) {
                    try {
                        if (checkRecentSets("1151842138", ExecDetailedEnum.FACTORY_RESET_DEVICE)) {
                            ValidacaoResult vr = (ValidacaoResult) getRecentSets("1151842138", ExecDetailedEnum.FACTORY_RESET_DEVICE)
                                    .getValid();
                            Boolean deucertoreset = vr.getResultado();
                            if (!deucertoreset) {
                                v = fakeGeneration(a.getAcao()).get(3);
                            } else {
                                v = fakeGeneration(a.getAcao()).get(2);
                            }
                        } else {
                            v = fakeGeneration(a.getAcao()).get(0);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        v = fakeGeneration(a.getAcao()).get(0);
                    }
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
                if (a.getAcao() == AcaoEnum.DNS) {
                    v = fakeGeneration(a.getAcao()).get(0);
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
                if (a.getAcao() == AcaoEnum.REBOOT) {
                    v = fakeGeneration(a.getAcao()).get(1);
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
                if (a.getAcao() == AcaoEnum.DNS) {
                    try {
                        if (checkRecentSets("1135310138", ExecDetailedEnum.SET_DNS)) {
                            v = fakeGeneration(a.getAcao()).get(2);
                        } else {
                            v = fakeGeneration(a.getAcao()).get(0);
                        }
                    } catch (Exception ex) {
                        v = fakeGeneration(a.getAcao()).get(0);
                    }
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
                if (a.getAcao() == AcaoEnum.DNS) {
                    try {
                        if (checkRecentSets("1151842070", ExecDetailedEnum.SET_DNS)) {
                            v = fakeGeneration(a.getAcao()).get(2);
                        } else {
                            v = fakeGeneration(a.getAcao()).get(0);
                        }
                    } catch (Exception ex) {
                        v = fakeGeneration(a.getAcao()).get(0);
                    }
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
                if (a.getAcao() == AcaoEnum.FACTORY_RESET) {
                    v = fakeGeneration(a.getAcao()).get(1);
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
                if (a.getAcao() == AcaoEnum.DNS) {
                    try {
                        if (checkRecentSets("1136891105", ExecDetailedEnum.SET_DNS)) {
                            v = fakeGeneration(a.getAcao()).get(2);
                        } else {
                            v = fakeGeneration(a.getAcao()).get(0);
                        }
                    } catch (Exception ex) {
                        v = fakeGeneration(a.getAcao()).get(0);
                    }

                }
                break;
            case "4131521805":
                if (a.getAcao() == AcaoEnum.PROFILE) {
                    v = fakeGeneration(a.getAcao()).get(1);
                }
                if (a.getAcao() == AcaoEnum.VLAN_BANDA) {
                    v = fakeGeneration(a.getAcao()).get(1);
                }
                if (a.getAcao() == AcaoEnum.FACTORY_RESET) {
                    try {
                        if (checkRecentSets("4131521805", ExecDetailedEnum.FACTORY_RESET_DEVICE)) {
                            ValidacaoResult vr = (ValidacaoResult) getRecentSets("4131521805",
                                    ExecDetailedEnum.FACTORY_RESET_DEVICE).getValid();
                            Boolean deucertoreboot = vr.getResultado();
                            if (!deucertoreboot) {
                                v = fakeGeneration(a.getAcao()).get(3);
                            } else {
                                v = fakeGeneration(a.getAcao()).get(2);
                            }
                        } else {
                            v = fakeGeneration(a.getAcao()).get(0);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        v = fakeGeneration(a.getAcao()).get(0);
                    }
                }
                break;
            case "1155230481":
                if (a.getAcao() == AcaoEnum.ASSOCIACAO_ONT) {
                    v = fakeGeneration(a.getAcao()).get(0);
                }
                if (a.getAcao() == AcaoEnum.FACTORY_RESET) {
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
                if (a.getAcao() == AcaoEnum.REBOOT) {
                    v = fakeGeneration(a.getAcao()).get(0);
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
                if (a.getAcao() == AcaoEnum.REBOOT) {
                    v = fakeGeneration(a.getAcao()).get(0);
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
                if (a.getAcao() == AcaoEnum.PING) {
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
                if (a.getAcao() == AcaoEnum.DNS) {
                    v = fakeGeneration(a.getAcao()).get(0);
                }
                break;
            case "1148678349":
                if (a.getAcao() == AcaoEnum.VLANS_VIDEO) {
                    v = fakeGeneration(a.getAcao()).get(3);
                }
                break;
            case "1156437947":
                if (a.getAcao() == AcaoEnum.WIFI_CRED) {
                    v = fakeGeneration(a.getAcao()).get(1);
                }
            case "1148681918":
                if (a.getAcao() == AcaoEnum.FACTORY_RESET) {
                    v = fakeGeneration(a.getAcao()).get(1);
                }
                break;
            // case "1135302119":
            // if (a.getAcao() == AcaoEnum.DNS) {
            // v = fakeGeneration(a.getAcao()).get(0);
            // }
            // break;
            default:
                break;
        }
        return v;
    }
}
