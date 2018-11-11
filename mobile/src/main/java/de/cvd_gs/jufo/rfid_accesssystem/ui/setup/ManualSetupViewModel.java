package de.cvd_gs.jufo.rfid_accesssystem.ui.setup;

import android.arch.lifecycle.ViewModel;


public class ManualSetupViewModel extends ViewModel {
    public String server_address;
    public String server_port;
    public String username;
    public String password;
    public Boolean nfcLogin;
    public Boolean nfcKey;

    public String getServer_address() {
        return server_address;
    }

    public String getServer_port() {
        return server_port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getNfcLogin() {
        return nfcLogin;
    }

    public Boolean getNfcKey() {
        return nfcKey;
    }

    public void setServer_address(String server_address) {
        this.server_address = server_address;
    }

    public void setServer_port(String server_port) {
        this.server_port = server_port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNfcLogin(Boolean nfcLogin) {
        this.nfcLogin = nfcLogin;
    }

    public void setNfcKey(Boolean nfcKey) {
        this.nfcKey = nfcKey;
    }
}
