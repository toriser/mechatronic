package at.mechatronik;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customer",
                (rs, rowNum) -> new Customer(rs.getLong("id_customer"),
                        rs.getString("name_1"),
                        rs.getString("name_2"),
                        rs.getString("name_3"),
                        rs.getString("address_1"),
                        rs.getString("address_2"),
                        rs.getString("address_3"),
                        rs.getString("phone"),
                        rs.getString("email")));
    }


    private void add(Customer customer) {
        jdbcTemplate.update("INSERT INTO customer (name_1, name_2, name_3, address_1, address_2, address_3, phone, email) VALUES (?,?,?,?,?,?,?,?)",
                customer.getName1(),
                customer.getName2(),
                customer.getName3(),
                customer.getAddress1(),
                customer.getAddress2(),
                customer.getAddress3(),
                customer.getPhone(),
                customer.getEmail());
    }

    public void update(Customer customer) {
        if (customer.getId() != null) {
            jdbcTemplate.update("UPDATE customer SET name_1=?, name_2=?, name_3=?, address_1=?, address_2=?, address_3=?, phone=?, email=? WHERE id_customer=?",
                    customer.getName1(),
                    customer.getName2(),
                    customer.getName3(),
                    customer.getAddress1(),
                    customer.getAddress2(),
                    customer.getAddress3(),
                    customer.getPhone(),
                    customer.getEmail(),
                    customer.getId());
        } else {
            add(customer);
        }
    }

    public void delete(long uid) {
        jdbcTemplate.update("DELETE FROM customer WHERE id_customer = ?", uid );
    }

}