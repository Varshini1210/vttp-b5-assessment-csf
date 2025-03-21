package vttp.batch5.csf.assessment.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

// Use the following class for MySQL database
@Repository
public class RestaurantRepository {

    @Autowired
    private JdbcTemplate template;

    public static final String SQL_VALIDATE_USER = """
            select * from customers
            where username = ? and password = ?
            """;

    public static final String SQL_INSERT_ORDER = """
            insert into place_orders (order_id, payment_id, order_date, total, username)
            values (?,?,?,?,?)
            """;

    public Boolean validateUser(String username, String password){
        SqlRowSet rs = template.queryForRowSet(SQL_VALIDATE_USER,username,password);
        if(!(rs.next())){
            return false;
        }
        else{
            return true;
        }
    }

    // public Boolean insertOrder(String orderId, String paymentId, )

}
