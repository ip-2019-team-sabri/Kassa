import org.apache.xmlrpc.XmlRpcException;
        import org.apache.xmlrpc.client.*;

        import java.net.MalformedURLException;
        import java.net.URL;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import static java.util.Arrays.asList;

public class test {

    public static void main(String[] args) throws MalformedURLException, XmlRpcException {
        String url = "http://10.3.56.6:8069";
        String db = "ErrasmusHB";
        String username = "anthony.moortgat@student.ehb.be";
        String password = "kassa";
        System.out.println("Get database list");
        System.out.println("Login");
        System.out.println("--------------");
        int uid = login(url,db,username,password);
        if (uid >0) {
            System.out.println("Login Ok");
        } else {
            System.out.println("Login Fail");
        }

        final XmlRpcClient models = new XmlRpcClient() {{
            setConfig(new XmlRpcClientConfigImpl() {{
                setServerURL(new URL(String.format("%s/xmlrpc/2/object", url)));
            }});
        }};

        final List ids = asList((Object[])models.execute(
                "execute_kw", asList(
                        db, uid, password,
                        "res.partner", "search",
                        asList(asList(
                                asList("customer", "=", true))),
                        new HashMap() {{ put("limit", 1); }})));
        final Map record = (Map)((Object[])models.execute(
                "execute_kw", asList(
                        db, uid, password,
                        "res.partner", "read",
                        asList(ids)
                )
        ))[0];
// count the number of fields fetched by default
        System.out.println(record.size());

        var list = asList((Object[])models.execute("execute_kw", asList(
                db, uid, password,
                "res.partner", "read",
                asList(ids),
                new HashMap() {{
                    put("fields", asList("name", "country_name","phone","email"));
                }}
        )));

        System.out.println(list);

//        final Integer id = (Integer)models.execute("execute_kw", asList(
//                db, uid, password,
//                "res.partner", "create",
//                asList(new HashMap() {
//                    { put("name", "Anas");
//                      put("phone","129038109283");
//                      put("street","Swag");
//                      put("city","Brussel");
//                      put("postcode","1755");
//                      put("country","Belgium");
//                    }})
//        )); // creating a customer with certain criterias

        var customers = asList((Object[])models.execute("execute_kw", asList(
                db, uid, password,
                "res.partner", "search",
                asList(asList(
                        asList("name", "=", "James"),
                        asList("customer", "=", true)))
        )));
        System.out.println(customers);

        // going through the whole list of customers and returning their id's
        // ( i gave Name as an parameter to get a certain user back.)


        models.execute("execute_kw", asList(
                db, uid, password,
                "res.partner", "unlink",
                asList(asList(13))));
// check if the deleted record is still in the database
        asList((Object[])models.execute("execute_kw", asList(
                db, uid, password,
                "res.partner", "search",
                asList(asList(asList("id", "=", 11)))
        ))); // deleting an customer using its unique ID
    }
    // login
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