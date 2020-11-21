package ace.infosolutions.tournyapp.model;

public class Username {
    private boolean exists;

    public Username(boolean exists) {
        this.exists = exists;
    }
    Username(){}

    public boolean isExists() {
        return exists;
    }
}
