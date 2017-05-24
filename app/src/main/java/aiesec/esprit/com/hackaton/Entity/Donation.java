package aiesec.esprit.com.hackaton.Entity;

import java.io.Serializable;

/**
 * Created by bechirkaddech on 2/10/17.
 */
public class Donation implements Serializable {


    private String id ;
    private String userUid ;
    private String typeObject ;
    private String quantity ;
    private String evenementId ;
    private String objectUrl ;

    public void setId(String id) {
        this.id = id;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public void setTypeObject(String typeObject) {
        this.typeObject = typeObject;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setEvenementId(String evenementId) {
        this.evenementId = evenementId;
    }

    public void setObjectUrl(String objectUrl) {
        this.objectUrl = objectUrl;
    }

    public String getId() {

        return id;
    }

    public String getUserUid() {
        return userUid;
    }

    public String getTypeObject() {
        return typeObject;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getEvenementId() {
        return evenementId;
    }

    public String getObjectUrl() {
        return objectUrl;
    }

    public Donation() {

    }

    public Donation(String id, String userUid, String typeObject, String quantity, String evenementId, String objectUrl) {

        this.id = id;
        this.userUid = userUid;
        this.typeObject = typeObject;
        this.quantity = quantity;
        this.evenementId = evenementId;
        this.objectUrl = objectUrl;
    }
}
