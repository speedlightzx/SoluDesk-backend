import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BancoDados {
   private static String url = "jdbc:mysql://localhost:3306/teste";
   private static String user = "root";
   private static String password = "";

   static String emailSalvo;
   static String nomeSalvo;

   	boolean adicionarHistorico(String data, String problema) {
   		try (Connection conn = DriverManager.getConnection(url, user, password)) {
   			String sql = "INSERT into historico values (?, ?)";
   			PreparedStatement stmt = conn.prepareStatement(sql);
   			stmt.setString(1, data);
   			stmt.setString(2, problema);
   			stmt.executeUpdate();
   			return true;
   		} catch(Exception e) {
   			e.printStackTrace();
   			return false;
   		}
   	}

    boolean registrarUsuario(String email, String senha) {
    try (Connection conn = DriverManager.getConnection(url, user, password)) {
        String sql = "SELECT * FROM usuarios WHERE email=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);

        ResultSet rs = stmt.executeQuery();

        if(rs.next()) return false;
        
        String sql2 = "INSERT into usuarios values (default,?,?)";
        PreparedStatement statement = conn.prepareStatement(sql2);
        statement.setString(1, email);
        statement.setString(2, senha);
        statement.executeUpdate();
        return true;
        
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
   }

    boolean validarLogin(String email, String senha) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT * FROM usuarios WHERE email=? AND senha=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) return true;
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    boolean TestarConexao() {
            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                //System.out.println("\nConectado com sucesso!");
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    }
}