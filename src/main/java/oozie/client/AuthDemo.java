package oozie.client;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.oozie.client.AuthOozieClient;
import org.apache.oozie.client.OozieClientException;

import java.io.IOException;
import java.security.PrivilegedExceptionAction;


public class AuthDemo {
    public static void main(String[] args) throws IOException, InterruptedException {

        String user = "bigdata";
        String path = "/Users/pipe/Documents/kerberos/test/bigdata.keytab";

        Configuration configuration = new Configuration();
        configuration.set("hadoop.security.authentication", "kerberos");

        UserGroupInformation.setConfiguration(configuration);
        UserGroupInformation.loginUserFromKeytab(user, path);
        UserGroupInformation.getLoginUser().doAs(new PrivilegedExceptionAction() {

            public Void run() throws Exception {
                oozieClient();
                return null;
            }

        });
    }

    private static void oozieClient() throws OozieClientException {
        String oozieUrl = "http://bigdata.t02.58btc.com:11000/oozie";
        AuthOozieClient authOozieClient = new AuthOozieClient(oozieUrl, AuthOozieClient.AuthType.KERBEROS.name());

        System.out.println(authOozieClient.getAuthOption());

        System.out.println(authOozieClient.getClientBuildVersion());
        System.out.println(authOozieClient.getServerBuildVersion());

    }
}
