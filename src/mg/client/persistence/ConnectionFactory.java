/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mg.client.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Vitor
 */
public class ConnectionFactory {
    
    public static Connection connection;
    /**
     * Método que faz a conexão com o banco de dados
     * @return Conexão com o banco de dados
     */
     static Connection getConnection(){
        try{
            if(connection == null){
                //connection = DriverManager.getConnection("jdbc:mysql://localhost/game", "root", "");
                connection = DriverManager.getConnection("jdbc:mysql://150.164.102.160/daw-aluno13", "daw-aluno13", "daw13");
            }
            
            return connection;
        } catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
}
