/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3;

import java.sql.Timestamp;

/**
 *
 * @author Sophia
 */
public class Auction {
    private int id;
    private Timestamp quand;
    private int montant;
    private int sur;
    private int de;

    public Auction(int id, Timestamp quand, int montant, int sur, int de) {
        this.id = id;
        this.quand = quand;
        this.montant = montant;
        this.sur = sur;
        this.de = de;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getQuand() {
        return quand;
    }

    public void setQuand(Timestamp quand) {
        this.quand = quand;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public int getSur() {
        return sur;
    }

    public void setSur(int sur) {
        this.sur = sur;
    }

    public int getDe() {
        return de;
    }

    public void setDe(int de) {
        this.de = de;
    }
    
    
}
