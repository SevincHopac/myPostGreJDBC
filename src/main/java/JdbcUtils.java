import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUtils {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;


    public static Connection connectToDataBase(String hostName, String dbName, String userName, String password) {

        //1.adim Driver'a kaydol
        //2.adim Database e kaydol
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://" + hostName + ":5432/" + dbName, userName, password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (connection != null) {
            System.out.println("Connection Success");
        } else {
            System.out.println("Connection Fail");
        }
        return connection;
    }


    public static Statement createStatement() {

        //3.adim :statement olustur

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return statement;

    }

    // 4.Adim Query calistir

    public static Boolean execute(String sql) {

        boolean isExecute;

        try {
            isExecute = statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isExecute;
    }

    //ExecuteQuery methodu
    public static ResultSet executeQuery(String query) {

        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    public static int executeUpdate(String sql){
        int numberOfUpdateData;

        try {
            numberOfUpdateData=statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return numberOfUpdateData;
    }






    //5. adim Connet ve Statement i kapa

    public static void closeConnectionAndStatement() {
        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            if (connection.isClosed() && statement.isClosed()) {
                System.out.println("Connection and Statement closed!");

            } else {
                System.out.println("Connection and Statement NOT closed!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Table creating metod -- table olusturan metod

    public static void createTable(String tableName, String... columnName_dataType) {

        StringBuilder columnName_dataValue = new StringBuilder("");

        for (String w : columnName_dataType) {

            columnName_dataValue.append(w).append(",");
        }

        columnName_dataValue.deleteCharAt(columnName_dataValue.length() - 1);


        try {
            statement.execute("CREATE TABLE " + tableName + "(" + columnName_dataValue + " )");
            System.out.println("Table " + tableName + " successfully created !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //Table'a data girme methodu
    public static void insertDataIntoTable(String tableName, String... columnName_Value) {

        StringBuilder columnNames = new StringBuilder("");
        StringBuilder values = new StringBuilder("");

        for (String w : columnName_Value) {
            columnNames.append(w.split(" ")[0]).append(",");//Bir String de??eri ikiye b??l??p birinciyi s??tun ad??, ikinciyi s??tun de??eri olarak al??yorum.
            values.append(w.split(" ")[1]).append(",");
        }

        columnNames.deleteCharAt(columnNames.lastIndexOf(","));//En son virg??l?? siliyor.
        values.deleteCharAt(values.lastIndexOf(","));

        //"INSERT INTO      members     ( id, name, address ) VALUES(123, 'john', 'new york')"
        String query = "INSERT INTO " + tableName + "(" + columnNames + ") VALUES(" + values + ")";

        execute(query);//execute methodu ??stte olu??turuldu, query'yi ??al????t??r??yor.
        System.out.println("Data " + tableName + " tablosuna girildi.");

    }

    //S??tun De??erlerini List i??erisine alan method
    public static List<Object> getColumnList(String columnName, String tableName) {

        List<Object> columnData = new ArrayList<>();//ResultSet'ten al??nan datan??n koyulaca???? List.

        //SELECT        id          FROM      students
        String query = "SELECT " + columnName + " FROM " + tableName;

        executeQuery(query);// => Bu method ??stte olu??turuldu. Query'yi ??al????t??r??p al??nan datay?? 'resultSet' container'?? i??ine atama yap??yor.

        try {
            while (resultSet.next()) {
                columnData.add(resultSet.getObject(columnName));//add methodu ile al??nan s??tun de??erlerini List'e ekliyor.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return columnData;
    }


}
