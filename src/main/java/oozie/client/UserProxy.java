package oozie.client;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.oozie.client.*;

import java.io.IOException;
import java.security.PrivilegedExceptionAction;
import java.util.Properties;

/**
 * Reference
 * https://blog.csdn.net/nuoyahadili8/article/details/54023972
 */

public class UserProxy {
    public static void main(String[] args) throws Exception {
        String user = "bigdata";
        String path = "/Users/pipe/Documents/kerberos/test/bigdata.keytab";

        Configuration configuration = new Configuration();
        configuration.set("hadoop.security.authentication", "kerberos");

        UserGroupInformation.setConfiguration(configuration);
        UserGroupInformation.loginUserFromKeytab(user, path);
        UserGroupInformation.getLoginUser().doAs(new PrivilegedExceptionAction() {

            public Void run() throws Exception {
                submitJob();
                return null;
            }

        });
//		UserGroupInformation ugi = UserGroupInformation.createProxyUser("oozieweb", UserGroupInformation.getLoginUser());
//		ugi.doAs(new PrivilegedExceptionAction() {
//			public Void run() throws Exception {
//				 //Submit a job
////				 FileSystem fs = FileSystem.get(conf);
////				 Path sourcePath = new Path("/user/oozieweb/anll");
////				 fs.mkdirs(sourcePath);
//				submitJob();
//				return null;
//			}
//		});
    }

    private static void submitJob() throws OozieClientException, InterruptedException {
        // get a OozieClient for local Oozie
        XOozieClient wc = new AuthOozieClient("http://hadoop7:11000/oozie/");
//		 OozieClient wc = new OozieClient("http://hadoop7:11000/oozie/v1/job/");
//		 AuthOozieClient wc = new AuthOozieClient("http://hadoop7:11000/oozie/", AuthOozieClient.AuthType.KERBEROS.toString());
        try {
            System.out.println(UserGroupInformation.getLoginUser());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // create a workflow job configuration and set the workflow application path
        Properties conf = wc.createConfiguration();
        conf.setProperty(OozieClient.APP_PATH, "hdfs://nameservice1/user/oozieweb/oozie-app/oozieweb/workflow/antest2");

        // setting workflow parameters
        conf.setProperty("jobTracker", "hadoop7:8032");
        conf.setProperty("nameNode", "hdfs://nameservice1");
//		 conf.setProperty("examplesRoot", EXAMPLE_DIR);
        conf.setProperty("queueName", "cdrapp");
//		 conf.setProperty("outputDir", OUTPUT_DIR);
//		 conf.setProperty("oozie.wf.rerun.failnodes", "true");
        conf.setProperty("hdfs.keytab.file", "C:/Program Files (x86)/Java/newhadoop_oozieweb_conf/oozieweb.keytab");
        conf.setProperty("hdfs.kerberos.principal", "oozieweb");
        conf.setProperty("mapred.mapper.new-api", "true");
        conf.setProperty("mapred.reducer.new-api", "true");
        conf.setProperty("oozie.use.system.libpath", "true");

        // submit and start the workflow job
        String jobId = wc.run(conf);
        System.out.println("Workflow job submitted");

        // wait until the workflow job finishes printing the status every 10 seconds
        while (wc.getJobInfo(jobId).getStatus() == WorkflowJob.Status.RUNNING) {
            System.out.println("Workflow job running ...");
            Thread.sleep(10 * 1000);
        }

        // print the final status of the workflow job
        System.out.println("Workflow job completed ...");
        System.out.println(wc.getJobInfo(jobId));
    }
}