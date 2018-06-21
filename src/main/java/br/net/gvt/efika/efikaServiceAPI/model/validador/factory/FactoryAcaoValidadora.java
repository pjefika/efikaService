/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.validador.factory;

import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;
import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoResultEnum;
import br.net.gvt.efika.efikaServiceAPI.model.service.validator.ValidacaoResultGenerator;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoValidadora;
import br.net.gvt.efika.efika_customer.model.customer.EfikaCustomer;
import br.net.gvt.efika.efika_customer.model.customer.enums.OrigemPlanta;
import br.net.gvt.efika.fulltest.model.fulltest.ValidacaoResult;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author G0041775
 */
public class FactoryAcaoValidadora {

    public static AcaoValidadora create(AcaoEnum acao) throws Exception {
        AcaoValidadora av = new AcaoValidadora(acao);
        av.setDataInicio(Calendar.getInstance().getTime());
        return av;
    }

    public static List<AcaoValidadora> fakeListCreation(AcaoEnum acao) {
        List<AcaoValidadora> l = new ArrayList<>();
        new ValidacaoResultGenerator().fakeGeneration(acao).forEach((t) -> {
            l.add(fakeCreation(acao, t));
        });
        return l;
    }

    public static AcaoValidadora fakeCreation(AcaoEnum acao, ValidacaoResult valid) {
        AcaoValidadora av = new AcaoValidadora(acao, valid);
        av.setDataInicio(Calendar.getInstance().getTime());
        av.setDataFim(Calendar.getInstance().getTime());
        EfikaCustomer ec = new EfikaCustomer("4125252525");
        ec.setInstancia("4125252525");
        av.setCustomer(ec);
        AcaoResultEnum r;
        if (valid.getFoiCorrigido() != null) {
            if (valid.getResultado()) {
                r = AcaoResultEnum.VALIDADO_OK;
            } else {
                r = valid.getFoiCorrigido() ? AcaoResultEnum.CORRIGIDO_OK : AcaoResultEnum.CORRIGIDO_NOK;
            }
            av.setResultado(valid.getResultado() || valid.getFoiCorrigido());
        } else {
            r = valid.getResultado() ? AcaoResultEnum.VALIDADO_OK : AcaoResultEnum.VALIDADO_NOK;
            av.setResultado(valid.getResultado());
        }
        if (valid.getNome().isEmpty()) {
            r = AcaoResultEnum.EXCEPTION;
        }

        av.setTipo(r);
        av.setUrlCorrecao(urlResponseGenerator(av));
//        String m = ResourceBundle.getBundle("messages", FactoryLocale.createLocale(SystemEnum.CRM)).getString(av.getNome() + "_" + r.name());

        av.setMensagem(valid.getMensagem());
        av.setConsulta(hasConsulta(av));
        return av;
    }

    public static String urlResponseGenerator(AcaoValidadora acao) {
        String s = null;
        switch (acao.getAcao()) {
            case ASSOCIACAO_ONT:
                if (acao.getTipo() == AcaoResultEnum.CORRIGIDO_NOK && acao.getCustomer().getRede().getPlanta() != OrigemPlanta.VIVO1) {
                    s = "http://10.40.196.171/efika_gps/pages/associacao_olt/associacao_olt.php";
                }
                break;
            case WIFI_CRED:
                if (acao.getTipo() != AcaoResultEnum.VALIDADO_NOK) {
                    s = "http://10.40.196.171/efika_gps/pages/wificonf/wificonf.php";
                }
                break;
            case REBOOT:
                if (acao.getTipo() != AcaoResultEnum.VALIDADO_NOK) {
                    s = "http://10.40.196.171/efika_gps/pages/reboot/reboot.php";
                }
                break;
            case FACTORY_RESET:
                if (acao.getTipo() != AcaoResultEnum.VALIDADO_NOK) {
                    s = "http://10.40.196.171/efika_gps/pages/factoryreset/factoryreset.php";
                }
                break;
            case PING:
                if (acao.getTipo() != AcaoResultEnum.VALIDADO_NOK) {
                    s = "http://10.40.196.171/efika_gps/pages/test_ping/test_ping.php";
                }
                break;
            case LAN_DEVICES:
                if (acao.getTipo() != AcaoResultEnum.VALIDADO_NOK) {
                    s = "http://10.40.196.171/efika_gps/pages/dispcon/dispcon.php";
                }
                break;
            case WIFI_CHANNEL:
                if (acao.getTipo() != AcaoResultEnum.VALIDADO_NOK) {
                    s = "http://10.40.196.171/efika_gps/pages/wificonf/wificonf.php";
                }
                break;
            case WIFI_STATUS:
                if (acao.getTipo() != AcaoResultEnum.VALIDADO_NOK) {
                    s = "http://10.40.196.171/efika_gps/pages/wificonf/wifiactive.php";
                }
                break;
            case DNS:
                if (acao.getTipo() != AcaoResultEnum.VALIDADO_NOK) {
                    s = "http://10.40.196.171/efika_gps/pages/dns/dns.php";
                }
                break;
            case IPS_IPTV:
                if (acao.getTipo() != AcaoResultEnum.VALIDADO_NOK) {
                    s = "http://10.40.196.171/efika_gps/pages/ipvod/ipvod.php";
                }
                break;
            case FIRMWARE:
                if (acao.getTipo() != AcaoResultEnum.VALIDADO_NOK) {
                    s = "http://10.40.196.171/efika_gps/pages/firmwareupdate/firmwareupdate.php";
                }
                break;
            default:
                break;
        }
        return s;
    }

    public static Boolean hasConsulta(AcaoValidadora acao) {
        Boolean bool = true;
        switch (acao.getAcao()) {
            case ASSOCIACAO_ONT:
                bool = acao.getTipo() != AcaoResultEnum.CORRIGIDO_NOK;
                break;
            case WIFI_CRED:
                bool = acao.getTipo() == AcaoResultEnum.VALIDADO_NOK;
                break;
            case REBOOT:
                bool = acao.getTipo() == AcaoResultEnum.VALIDADO_NOK;
                break;
            case FACTORY_RESET:
                bool = acao.getTipo() == AcaoResultEnum.VALIDADO_NOK;
                break;
            case PING:
                bool = acao.getTipo() == AcaoResultEnum.VALIDADO_NOK;
                break;
            case LAN_DEVICES:
                bool = acao.getTipo() == AcaoResultEnum.VALIDADO_NOK;
                break;
            case WIFI_CHANNEL:
                bool = acao.getTipo() == AcaoResultEnum.VALIDADO_NOK;
                break;
            case FIRMWARE:
                bool = acao.getTipo() == AcaoResultEnum.VALIDADO_NOK;
                break;
            default:
                break;
        }
        return bool;
    }

}
