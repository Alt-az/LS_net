public class ClientLicenseApi {
    String nul;
    String kl;
    String ip;
    String port;

    void start(String ip, String port){
        this.ip = ip;
        this.port = port;
    }

    void setLicense(String nul, String kl){
        this.nul = nul;
        this.kl = kl;
    }
    String getLicenseToken(){
        String result = null;
        return result;
    }
    void stop(){
        nul = null;
        kl = null;
        ip = null;
        port = null;
    }
}
