package Database;

import Logic.Customer;

import java.util.*;

public class CustomerDAO {
    public List<Customer> getCustomerWP(){
        List<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer());
        return customerList;
    }
}
