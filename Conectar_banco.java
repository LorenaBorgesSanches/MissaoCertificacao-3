import

import static java.lang.Class.forName;

public class Conectar_banco {
    static String drive = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static String user = "";
    static String senha = "";

    public static void main(String[] args) {
        try{
            System.out.println();
            class.forName(drive);
            System.out.println("carregando dados");
        }catch(Exception e){
            System.out.println("deu ruim");
        }
    }
}
