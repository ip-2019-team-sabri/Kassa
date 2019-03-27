package Database;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;

public class Connection {
    static int login(String url, String db, String login, String password) throws XmlRpcException, MalformedURLException {
        XmlRpcClient client = new XmlRpcClient();
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setEnabledForExtensions(true);
        //config.setServerURL(new URL(url+"/xmlrpc/common"));
        config.setServerURL(new URL(url+"/xmlrpc/2/common"));
        client.setConfig(config);
        //Connect
        //Object[] empty = null; // Ok
        //Object[] params = new Object[] {db,login,password, empty}; // Ok
        Object[] params = new Object[] {db,login,password}; // Ok & simple
        Object uid = client.execute("login", params);
        if (uid instanceof Integer)
            return (int) uid;
        return -1;
    }
}
