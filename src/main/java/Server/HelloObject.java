package Server;

import java.io.Serializable;
import java.net.InetAddress;

public class HelloObject implements Serializable {
    private String message;
    private Integer id;

    public HelloObject(Integer id,String message){
        this.id = id;
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String meaasge) {
        this.message = meaasge;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
