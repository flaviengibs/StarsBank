import java.util.Scanner;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        Bank b = new Bank();
        b.readJsonFile();
        b.printAccounts();
        boolean exit = false;
        System.out.println(b.accounts.toString());
        System.out.println(b.customers.toString());
        while(!exit) {
            b.ShowMainMenu();
            Scanner input = new Scanner(System.in);
            int a = input.nextInt();
            switch (a) {
                case 1:
                    b.createCustomer();
                    break;
                case 2:
                    b.addNewAccount();
                    break;
                case 3:
                    b.transaction();
                case 4:
                    b.login();
                    break;
                case 5:
                    b.printAccounts();
                    break;
                case 6:
                    b.printCustomers();
                    break;
                case 7:
                    b.loginAsAdmin();
                    break;
                case 8:
                    b.defineRibAccountToPay();
                    break;
                case 9:
                    b.suspendAccount();
                    break;
                case 10:
                    b.closeAccount();
                    break;
                case 11:
                    b.unsuspendAccount();
                    break;
                case 12:
                    exit = true;
            }
        }

        b.createJsonFile();
    }
}
