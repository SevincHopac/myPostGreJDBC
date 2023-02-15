import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CountriesTest {

    /*
        Given
          User connects to the database
        When
          User sends the query to get the region ids from "countries" table
        Then
          Verify that the number of region ids greater than 1 is 17.
        And
          User closes the connection
       */


    @Test

    public void countryTest() throws SQLException {

        // User connects to the database

        JdbcUtils.connectToDataBase("localhost","postgres","postgres","341416");
        Statement statement = JdbcUtils.createStatement();

        // User sends the query to get the region ids from "countries" table

        String sql1 = "SELECT region_id FROM countries";

        ResultSet resultSet1 = statement.executeQuery(sql1);
        List<Integer> region_ids = new ArrayList<>();

        while (resultSet1.next()){

            region_ids.add(resultSet1.getInt(1));
        }



        //Verify that the number of region ids greater than 1 is 17.

        System.out.println("region_ids = " + region_ids);

        List<Integer> region_idsGreaterThan1 = new ArrayList<>();

        for (Integer w : region_ids){

            if (w>1){
                region_idsGreaterThan1.add(w);
            }
        }

        System.out.println("region_idsGreaterThan1 = " + region_idsGreaterThan1);

        Assert.assertEquals(17,region_idsGreaterThan1.size());


        //User closes the connection

        JdbcUtils.closeConnectionAndStatement();

    }
}
