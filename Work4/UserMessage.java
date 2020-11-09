package stage4.HomeWork.Work4;
import java.io.Serializable;

public class UserMessage implements Serializable {

    private static final long serialVersionUID = -1336136822913986807L;
    private String type;
    private User user;

    public UserMessage() {
    }

    public UserMessage(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
