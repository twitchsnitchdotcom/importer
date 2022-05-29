package com.twitchsnitch.importer.dto;

/**


 Lobbyist Name	50CAN, INC. (FKA NYCAN (50CAN): NEW YORK CAMPAIGN FOR ACHIEVEMENT NOW)
 Lobbyist Address	1150 6th Avenue
 6th Floor
 New York
 NY 10036
 Lobbyist Phone	(212) 757-3210
 Lobbyist Email	DERRELL.BRADFORD@NYCAN.ORG
 Lobbyist Type	E - Employed
 Lobbying Type	N - Non-Procurement
 Government Level	S - State
 Additional Lobbyist	Listed on the Principal Lobbyist Registration
 DERRELL BRADFORD
 Listed on the Principal Lobbyist Bi-monthly Report
 DERRELL BRADFORD

 */
public class LobbyistDTO {

    private String url;
    private String  name;
    private String  address;
    private String  phone;
    private String  email;
    private String  lobbyistType;
    private String  lobbyingType;
    private String governmentLevel;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGovernmentLevel() {
        return governmentLevel;
    }

    public void setGovernmentLevel(String governmentLevel) {
        this.governmentLevel = governmentLevel;
    }

    public String getLobbyistType() {
        return lobbyistType;
    }

    public void setLobbyistType(String lobbyistType) {
        this.lobbyistType = lobbyistType;
    }

    public String getLobbyingType() {
        return lobbyingType;
    }

    public void setLobbyingType(String lobbyingType) {
        this.lobbyingType = lobbyingType;
    }
}
