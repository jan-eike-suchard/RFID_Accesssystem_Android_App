package de.cvd_gs.jufo.rfid_accesssystem.ui.setup;

import androidx.lifecycle.ViewModel;

import java.util.UUID;

public class SetupViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    public String server = "";
    public String port = "";
    public String api_key = "";
    public String device_id = UUID.randomUUID().toString();
    public int currentStep = 0;
    public String autoconfig_url;
    public Boolean autoconfig = true;
    public Boolean forward;
    public volatile boolean continueSetup;

    public Boolean getForward() {
        return forward;
    }

    public Boolean getAutoconfig() {
        return autoconfig;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public String getApi_key() {
        return api_key;
    }

    public String getAutoconfig_url() {
        return autoconfig_url;
    }

    public String getDevice_id() {
        return device_id;
    }

    public String getPort() {
        return port;
    }

    public String getServer() {
        return server;
    }

    public boolean isContinueSetup() {
        return continueSetup;
    }

    public void setContinueSetup(boolean continueSetup) {
        this.continueSetup = continueSetup;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public void setAutoconfig(Boolean autoconfig) {
        this.autoconfig = autoconfig;
    }

    public void setAutoconfig_url(String autoconfig_url) {
        this.autoconfig_url = autoconfig_url;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public void setForward(Boolean forward) {
        this.forward = forward;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setServer(String server) {
        this.server = server;
    }
}

