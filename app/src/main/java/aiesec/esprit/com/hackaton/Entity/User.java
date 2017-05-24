package aiesec.esprit.com.hackaton.Entity;

/**
 * Created by bechirkaddech on 2/11/17.
 */

public class User {




    private String Email ;
    private String pictureUrl ;
    private String Vote ;
    private String DonationNumber ;
    private String userName ;
    private String Token ;



    public void setEmail(String email) {
        Email = email;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setVote(String vote) {
        Vote = vote;
    }

    public void setDonationNumber(String donationNumber) {
        DonationNumber = donationNumber;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getEmail() {

        return Email;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getVote() {
        return Vote;
    }

    public String getDonationNumber() {
        return DonationNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getToken() {
        return Token;
    }

    public User() {

    }

    public User(String email, String pictureUrl, String vote, String donationNumber, String userName, String token) {

        Email = email;
        this.pictureUrl = pictureUrl;
        Vote = vote;
        DonationNumber = donationNumber;
        this.userName = userName;
        Token = token;
    }
}
