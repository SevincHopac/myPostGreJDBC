import java.sql.*;

public class CallableStatement01 {

    /*
    Java'da methodlar return type sahibi olsa da olmasa da method olarak adlandırılır.
    SQL'de ise data return ediyorsa "function" denir. Return yapmıyorsa "procedure" olarak adlandırılır.
    */


    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "341416");
        Statement st = con.createStatement();


        //CallableStatement ile function çağırmayı parametrelendireceğiz.

        //1.Adım: Function kodunu yaz:

        String sql1 = "CREATE OR REPLACE FUNCTION  toplamaF(x NUMERIC ,y NUMERIC)\n" +
                "RETURNS NUMERIC\n" +
                "LANGUAGE plpgsql\n" +
                "AS\n" +
                "$$\n" +
                "BEGIN\n" +
                "RETURN x+y;\n" +
                "END\n" +
                "$$";


        //2. Adım: Function'ı çalıştır.
        st.execute(sql1);

        //3. Adım: Fonction'ı çağır.
        CallableStatement cst1 = con.prepareCall("{? = call toplamaF(?, ?)}");   //İlk parametre retun type

        //4. Adım: Return için registerOutParameter() methodunu, parametreler için ise set() ... methodlarını uygula.
        cst1.registerOutParameter(1, Types.NUMERIC);
        cst1.setInt(2, 6);
        cst1.setInt(3, -6);

        //5. Adım: execute() methodu ile CallableStatement'ı çalıştır.
        cst1.execute();

        //6. Adım: Sonucu çağırmak için return data type tipine göre

        System.out.println("cst1 = "+cst1.getBigDecimal(1));


        //2. Örnek: Koninin hacmini hesaplayan bir function yazın.

        String sql2 = "CREATE OR REPLACE FUNCTION  konihacmiF(r NUMERIC ,h NUMERIC)\n" +
                "RETURNS NUMERIC\n" +
                "LANGUAGE plpgsql\n" +
                "AS\n" +
                "$$\n" +
                "BEGIN\n" +
                "RETURN (r*r*h*3.14)/3;\n" +
                "END\n" +
                "$$";

        st.execute(sql2);
        CallableStatement cst2 = con.prepareCall("{? = call konihacmiF(?, ?)}");

        cst2.registerOutParameter(1, Types.NUMERIC);
        cst2.setInt(2, 1);
        cst2.setInt(3, 6);
        cst2.execute();
        System.out.printf("%.2f", cst2.getBigDecimal(1));

        con.close();
        st.close();
        cst1.close();
        cst2.close();

    }
}
