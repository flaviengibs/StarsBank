import java.time.*;

public class Account {
    private double money;
    private String customerId;
    private String RIB;
    public enum Status {
        Active, InCreation, Suspended,Closed
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private Status status = Status.InCreation;
    public String getOpenedOn() {
        return openedOn;
    }

    public final String openedOn;
    public Account(String customerId, String RIB)
    {
        this.customerId = customerId;
        this.money = getBalance();
        this.RIB = RIB;
        this.openedOn = LocalDate.now().toString();
        this.status = Status.Active;
    }

    public void setMoney(Integer money){
        this.money = money;
    }
    public Double getMoney(){
        return this.money;
    }
    public void addMoney(double additionalMoney){
        this.money = this.money + additionalMoney;
    }
    public void removeMoney(double removedMoney){
        this.money = this.money - removedMoney;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public String getRIB() {
        return this.RIB;
    }

    public void setRIB(String RIB) {
        this.RIB = RIB;
    }

    public double getBalance() {
        return money;
    }


}
