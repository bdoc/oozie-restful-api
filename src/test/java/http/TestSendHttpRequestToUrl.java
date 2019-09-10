package http;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Arrays;
/**
 * Reference
 * https://blog.csdn.net/high2011/article/details/74889321/
 */
public class TestSendHttpRequestToUrl {
    private static Logger logger = LoggerFactory.getLogger(TestSendHttpRequestToUrl.class);

    public static void main(String[] args) {
        String user = "bigdata";
        String keytab = "/Users/pipe/Documents/kerberos/test/bigdata.keytab";
        String krb5Location = "/etc/krb5.conf";

        try {
            RequestKerberosUrlUtils restTest = new RequestKerberosUrlUtils(user, keytab, krb5Location, false);

            String url_liststatus = "http://bigdata.t02.58btc.com:11000/oozie/versions";

            HttpResponse response = restTest.callRestUrl(url_liststatus, user);

            InputStream is = response.getEntity().getContent();
            System.out.println("Status code " + response.getStatusLine().getStatusCode());
            System.out.println("message is :" + Arrays.deepToString(response.getAllHeaders()));
            System.out.println("string：\n" + new String(IOUtils.toByteArray(is), "UTF-8"));

        } catch (Exception exp) {
            exp.printStackTrace();
        }

    }
}