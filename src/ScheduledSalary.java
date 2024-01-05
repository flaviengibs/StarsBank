import java.time.MonthDay;

public class ScheduledSalary {
    public static void main(String[] args) {
        Bank b = new Bank();
        b.readJsonFile();
        if (MonthDay.now().getDayOfMonth() == 4) {
            System.out.print("It’s salary payment day!");
            for (Customer cust : b.customers.values()) {
                String ribAccountToPay = cust.getRibOfAccountToCreditBySalary();
                Account accountToPay = b.accounts.get(ribAccountToPay);
                accountToPay.addMoney(cust.getSalary());
                double moneyOfAccount = accountToPay.getMoney();
                System.out.println("This account has now " + moneyOfAccount + " €");
                b.createJsonFile();
                break;
            }
        } else {
            System.out.println("It's not salary payment day... Maybe tomorrow!");
        }
    }
}
