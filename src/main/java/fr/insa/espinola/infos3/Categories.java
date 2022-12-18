/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Sophia
 */
public class Categories {

    private String nom;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Categories(String nom) {
        this.nom = nom;
    }

    public static void createTableCategory(Connection con)
            throws SQLException {
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) {
            st.executeUpdate(
                    """
                    create table Category (
                        id integer not null primary key
                        generated always as identity,
                        nom varchar(30) not null unique,
                    )
                    """);

            con.commit(); // je retourne dans le mode par defaut de gestion des transaction : chaque ordre au SGBD sera considere comme une transaction independante
            con.setAutoCommit(true);
        } catch (SQLException ex) { // quelque chose s'est mal passe j'annule la transaction
            con.rollback(); // puis je renvoie l'exeption pour qu'elle puisse eventuellement etre geree (message a  l'utilisateur...)
            throw ex;
        } finally { // je reviens a  la gestion par defaut : une transaction pour chaque ordre SQL
            con.setAutoCommit(true);
        }
    }

    public static void deleteTableCategory(Connection con)
            throws SQLException {
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) { // elimination des tables
            st.executeUpdate(
                    """
                    drop table Category 
                    """);

            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }

    public static void createCategory(Connection con) throws SQLException {
        try ( PreparedStatement pst = con.prepareStatement(
                """
                insert into Category (nom)
                values (?)
                    """)) {

            System.out.println("Nom");
            String nom = Lire.S();

            pst.setString(1, nom);
            pst.executeUpdate();
            con.setAutoCommit(false);
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }

    /*public static void searchCategory(Connection con) throws SQLException {                    // creo que me tendria que devolver todos los objetos con la categoria seleccionada
        try ( PreparedStatement pst = con.prepareStatement(
                """
                insert into Category (nom)
                values (?)
                    """)) {

            System.out.println("Nom");
            String nom = Lire.S();

            pst.setString(1, nom);
            pst.executeUpdate();
            con.setAutoCommit(false);
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }*/
}
