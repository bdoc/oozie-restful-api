package oozie.client;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.oozie.client.OozieClient;
import org.apache.oozie.client.OozieClientException;

import java.io.IOException;
import java.security.PrivilegedExceptionAction;

public class SimpleDemo {
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
        OozieClient oozieClient = new OozieClient(oozieUrl);

        System.out.println(oozieClient.getClientBuildVersion());
        System.out.println(oozieClient.getServerBuildVersion());

    }
}
