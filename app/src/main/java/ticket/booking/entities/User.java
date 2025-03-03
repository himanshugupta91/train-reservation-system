package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming (PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String name;
    private String password;
    private String hashedPassword;
    private List<Ticket> ticketsbooked;
    private String  userid;

    public User(String name, String password, String hashedPassword, List<Ticket> ticketsbooked, String userid) {
        this.name = name;
        this.password = password;
        this.hashedPassword = hashedPassword;
        this.ticketsbooked = ticketsbooked;
        this.userid = userid;
    }
    public User() {}

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getHashedPassword () {
        return hashedPassword;
    }

    public List<Ticket> getTicketsbooked() {
        return ticketsbooked;
    }

    public void printTickets() {
        for(int i = 0; i<ticketsbooked.size(); i++) {
            System.out.println(ticketsbooked.get(i).getTicketInfo());
        }
    }

    public String getUserid() {
        return userid;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setHashedPassword(String hashedPassword){
        this.hashedPassword = hashedPassword;
    }

    public void setTicketsbooked(List<Ticket> ticketsbooked){
        this.ticketsbooked = ticketsbooked;
    }

    public void setUserid(String userid){
        this.userid = userid;
    }

}
