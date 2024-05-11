package io.github.lwdjd.okxapisdk.account;



public class Account {
    private String apiKey, secretKey, passphrase , httpProxy = null;
    private Boolean simulatedTrading;

    /**
     *
     * @param apiKey 你的KEY
     * @param secretKey 你的密钥key
     * @param passphrase 你的密码
     * @param simulatedTrading 是否为模拟交易
     */
    public Account(String apiKey, String secretKey, String passphrase, Boolean simulatedTrading) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.passphrase = passphrase;
        this.simulatedTrading = simulatedTrading;
    }

    /**
     *
     * @param apiKey 你的KEY
     * @param secretKey 你的密钥key
     * @param passphrase 你的密码
     * @param simulatedTrading 是否为模拟交易
     * @param httpProxy 你的http代理
     */
    public Account(String apiKey, String secretKey, String passphrase, Boolean simulatedTrading , String httpProxy) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.passphrase = passphrase;
        this.simulatedTrading = simulatedTrading;
        this.httpProxy = httpProxy;
    }

    public Account setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public Account setSecretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    public Account setPassphrase(String passphrase) {
        this.passphrase = passphrase;
        return this;
    }

    public Account setSimulatedTrading(Boolean simulatedTrading) {
        this.simulatedTrading = simulatedTrading;
        return this;
    }


    public Account setHttpProxy(String httpProxy) {
        this.httpProxy = httpProxy;
        return this;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public Boolean getSimulatedTrading() {
        return simulatedTrading;
    }


    public String getHttpProxy() {
        return httpProxy;
    }
}
