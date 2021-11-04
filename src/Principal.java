import java.sql.*;

public class Principal {

    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost;databaseName=Chat;integratedSecurity=true";
        try(Connection cnx = DriverManager.getConnection(url)){
            if(! cnx.isClosed()){
                CallableStatement cstm = cnx.prepareCall("{?=call userRegistro_Sp(?, ?, ?, ?)}");
                cstm.registerOutParameter(1, Types.INTEGER);
                cstm.setString(2, "prueba2");
                cstm.setString(3, "prueba2");
                cstm.setString(4, "prueba2@correo.es");
                cstm.setString(5, "Prueba2");
                cstm.execute();
                int emisor = cstm.getInt(1);
                String sentencia = "INSERT INTO Mensajes(emisor, receptor, asunto, texto) VALUES (1, ?, 'Bienvenido','Mensaje de bienvenida')";
                PreparedStatement pstmt = cnx.prepareStatement(sentencia);
                pstmt.setInt(1, emisor);
                int filas = pstmt.executeUpdate();
                if(filas > 0)
                    System.out.println("Mensaje de bienvenida enviado correctamente");
                else
                    System.out.println("No se ha enviado el mensaje de bienvenida");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
