
package fr.insa.espinola.infos3.Interface;

import fr.insa.espinola.infos3.tables.Clients;
import java.sql.Connection;
import java.util.Optional;

/**
 *
 * @author nicol
 */
public class Utilisateurs {

    private Optional<Clients> Utilisateur;
    private Connection conBdD;

    public Utilisateurs() {
        this.Utilisateur = Optional.empty();
        this.conBdD = null;
    }

    public boolean UtilisateurConnecte() {
        return this.Utilisateur.isPresent();
    }

    public Optional<Clients> getUtilisateur() {
        return Utilisateur;
    }

    public void setUtilisateur(Optional<Clients> Utilisateur) {
        this.Utilisateur = Utilisateur;
    }


    public int getUtilisateurID() {
        return this.Utilisateur.orElseThrow().getId();
    }

    public Connection getConBdD() {
        return conBdD;
    }

    public void setConBdD(Connection conBdD) {
        this.conBdD = conBdD;
    }

    public String getUtilisateurEmail() {
        return this.Utilisateur.orElseThrow().getEmail();
    }

}
