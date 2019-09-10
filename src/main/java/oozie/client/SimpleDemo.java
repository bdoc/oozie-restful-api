package oozie.client;

import org.apache.oozie.client.OozieClient;
import org.apache.oozie.client.OozieClientException;

import java.io.IOException;

public class SimpleDemo {
    public static void main(String[] args) throws OozieClientException, IOException {

        String oozieUrl = "http://bigdata.t02.58btc.com:11000/oozie";
        OozieClient oozieClient = new OozieClient(oozieUrl);

        System.out.println(oozieClient.getClientBuildVersion());
        System.out.println(oozieClient.getServerBuildVersion());

        String jobId = "0000001-190828185420270-oozie-oozi-W";
        System.out.println(oozieClient.getJobInfo(jobId));

    }
}
