package aiesec.esprit.com.hackaton.Entity;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by bechirkaddech on 2/10/17.
 */

public class Evenement implements Serializable {


    private String id;
    private String title;
    private String description;
    private String dateFin;
    private String dateDebut;
    private String adresse;
    private String gouvernerat;
    private String imageUrl;
    private String userUid;
    private HashMap<String, Donation> donations;
    private String objectifArgent;
    private String objectiVetement;
    private String objectifNourriture;
    private String currentArgent;
    private String currentVetement;
    private String currentNourriture;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setGouvernerat(String gouvernerat) {
        this.gouvernerat = gouvernerat;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public void setDonations(HashMap<String, Donation> donations) {
        this.donations = donations;
    }

    public void setObjectifArgent(String objectifArgent) {
        this.objectifArgent = objectifArgent;
    }

    public void setObjectiVetement(String objectiVetement) {
        this.objectiVetement = objectiVetement;
    }

    public void setObjectifNourriture(String objectifNourriture) {
        this.objectifNourriture = objectifNourriture;
    }

    public void setCurrentArgent(String currentArgent) {
        this.currentArgent = currentArgent;
    }

    public void setCurrentVetement(String currentVetement) {
        this.currentVetement = currentVetement;
    }

    public void setCurrentNourriture(String currentNourriture) {
        this.currentNourriture = currentNourriture;
    }

    public String getId() {

        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDateFin() {
        return dateFin;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getGouvernerat() {
        return gouvernerat;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUserUid() {
        return userUid;
    }

    public HashMap<String, Donation> getDonations() {
        return donations;
    }

    public String getObjectifArgent() {
        return objectifArgent;
    }

    public String getObjectiVetement() {
        return objectiVetement;
    }

    public String getObjectifNourriture() {
        return objectifNourriture;
    }

    public String getCurrentArgent() {
        return currentArgent;
    }

    public String getCurrentVetement() {
        return currentVetement;
    }

    public String getCurrentNourriture() {
        return currentNourriture;
    }

    public Evenement() {

    }

    public Evenement(String id, String title, String description, String dateFin, String dateDebut, String adresse, String gouvernerat, String imageUrl, String userUid, HashMap<String, Donation> donations, String objectifArgent, String objectiVetement, String objectifNourriture, String currentArgent, String currentVetement, String currentNourriture) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.dateFin = dateFin;
        this.dateDebut = dateDebut;
        this.adresse = adresse;
        this.gouvernerat = gouvernerat;
        this.imageUrl = imageUrl;
        this.userUid = userUid;
        this.donations = donations;
        this.objectifArgent = objectifArgent;
        this.objectiVetement = objectiVetement;
        this.objectifNourriture = objectifNourriture;
        this.currentArgent = currentArgent;
        this.currentVetement = currentVetement;
        this.currentNourriture = currentNourriture;
    }
}