/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package japedidos.produto;

/**
 *
 * @author Vanderson
 */
public class db_config {
        private String db_url; // Contador de carros no semáforos
        private String db_user; // Contador de carros fora da interseção 
        private String db_pwd; // Contador que passaram pelo semaforo 

        public void setDB_Url(String url) {
        	this.db_url = url;
        }
        public void setDB_User(String usuario) {
        	this.db_user = usuario;
        }
        public void setDB_Pwd(String senha) {
        	this.db_pwd = senha;
        }
        public String getDB_Url() {
        	return db_url;
        }
        public String getDB_User() {
        	return db_user;
        }
        public String getDB_Pwd() {
        	return db_pwd;
        }
}
