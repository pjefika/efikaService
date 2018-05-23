/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 *
 * @author G0041775
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AcaoEnum {

//    PARAMETROS_REDE,
    ASSOCIACAO_ONT("Associação ONT a OLT"),
    CHECK_GERENCIA("Disponibilidade de Gerência"),
    ESTADO_PORTA("Estado da Porta"),
    VLAN_BANDA("Vlan de Banda"),
    PROFILE("Profile"),
    VLANS_VIDEO("Vlans de Vídeo"),
    VLAN_VOIP("Vlan de VoIP"),
    PARAMETROS("Parâmetros de Rede"),
    ATM("ATM"),
    WIFI_CRED("Nome e senha da rede Wifi"),
    REBOOT("Reinicialização de equipamento"),
    FACTORY_RESET("Configurações de fábrica"),
    PING("Ping"),
    LAN_DEVICES("Dispositivos conectados"),
    DNS("DNS"),
    WIFI_CHANNEL("Canal da rede Wifi"),
    WIFI_STATUS("Estado da rede Wifi"),
    TROCA_PACOTES("Troca de pacotes");


    private String value;

    AcaoEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public String getNome() {
        return toString();
    }

//    @JsonValue
    public String getAlias() {
        return this.name();
    }

}
