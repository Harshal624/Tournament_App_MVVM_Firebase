package ace.infosolutions.tournyapp.model;

import android.graphics.Bitmap;

import com.google.firebase.firestore.Exclude;


public class ProfileModel {
    private String username;
    @Exclude
    private Bitmap profile_pic;
    private String profile_url;
    private String full_name;
    private String dob;
    private String location;
    private String freefire_id;
    private String valorant_id;
    private String codmobile_id;
    private String pubg_id;
    private String twitter_id;
    private String discord_id;
    private String facebook_id;
    private String whatsapp_id;
    @Exclude
    private boolean success;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Bitmap getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(Bitmap profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFreefire_id() {
        return freefire_id;
    }

    public void setFreefire_id(String freefire_id) {
        this.freefire_id = freefire_id;
    }

    public String getValorant_id() {
        return valorant_id;
    }

    public void setValorant_id(String valorant_id) {
        this.valorant_id = valorant_id;
    }

    public String getCodmobile_id() {
        return codmobile_id;
    }

    public void setCodmobile_id(String codmobile_id) {
        this.codmobile_id = codmobile_id;
    }

    public String getPubg_id() {
        return pubg_id;
    }

    public void setPubg_id(String pubg_id) {
        this.pubg_id = pubg_id;
    }

    public String getTwitter_id() {
        return twitter_id;
    }

    public void setTwitter_id(String twitter_id) {
        this.twitter_id = twitter_id;
    }

    public String getDiscord_id() {
        return discord_id;
    }

    public void setDiscord_id(String discord_id) {
        this.discord_id = discord_id;
    }

    public String getFacebook_id() {
        return facebook_id;
    }

    public void setFacebook_id(String facebook_id) {
        this.facebook_id = facebook_id;
    }

    public String getWhatsapp_id() {
        return whatsapp_id;
    }

    public void setWhatsapp_id(String whatsapp_id) {
        this.whatsapp_id = whatsapp_id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
