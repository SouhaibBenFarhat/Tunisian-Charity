package aiesec.esprit.com.hackaton.Entity;

import java.io.Serializable;

/**
 * Created by Ch on 11/02/2017.
 */

public class Rating implements Serializable{

      // IdUserVote_ToUserVoted
    private String note;

    public Rating( String note) {
         this.note = note;
    }

    public Rating() {
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
