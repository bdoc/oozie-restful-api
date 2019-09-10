package oozie.client;

import org.apache.oozie.client.AuthOozieClient;
import org.apache.oozie.client.OozieClientException;

import java.io.IOException;

/**
 * First --->
 * kinit -kt path/user.keytab user
 */
public class AuthDemo {
    public static void main(String[] args) throws IOException, OozieClientException {

        String oozieUrl = "http://bigdata.t02.58btc.com:11000/oozie";
        AuthOozieClient authOozieClient = new AuthOozieClient(oozieUrl, AuthOozieClient.AuthType.KERBEROS.name());

        System.out.println(authOozieClient.getAuthOption());

        System.out.println(authOozieClient.getClientBuildVersion());
        System.out.println(authOozieClient.getServerBuildVersion());

        String jobId = "0000001-190828185420270-oozie-oozi-W";
        System.out.println(authOozieClient.getJobInfo(jobId));
    }
}
