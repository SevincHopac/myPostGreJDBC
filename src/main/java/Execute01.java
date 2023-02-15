import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Execute01 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        //1. Adim Driver'a kaydol

        Class.forName("org.postgresql.Driver");


        //2.adim database e baglanma

        Connection con =DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","341416");

        //3.Adim Statement olustur

        Statement st = con.createStatement();

        //4.Adim Query calistir

        /*
        execute() metodu ---->  DDL(create, alter, drop table) ve DQL(Select) icin kullanilabilir.
        1)Eger execute() methodu DDL icin kullanilirsa 'false' return eder.
        2)Eger execute() methodu DQL icin kullanilirsa RESULT SET alindiginda 'true', aksi halde  'false' return eder.
         */




        // --Ex01: "workers" adinda bir table olusturup "worker_id","worker_name","worker_salary" fieldlari ekleyin

        boolean sql1 = st.execute("CREATE TABLE workers(worker_id VARCHAR(20),worker_name VARCHAR(20), worker_salary INT)");

        System.out.println(sql1);     // data cagirmadigi icin false döner

        // --Ex02: Table a worker_address field ekleyerek alter yapin

        String sql2 = "ALTER TABLE workers ADD worker_address VARCHAR(20)";
        st.execute(sql2);

        // --Ex03: workers table drop et

        String sql3 = "DROP TABLE workers";
        st.execute(sql3);

        //5. adim Connet ve Statement i kapa

        con.close();
        st.close();


    }
}
