import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.*;

public class Bank {
    public Map<String, Account> accounts;
    public Map<String, Customer> customers;

    public Bank() {

        this.accounts = new HashMap<>();
        this.customers = new HashMap<>();
    }

    public void addNewAccount(String custId, String RIB) {
        accounts.put(custId, new Account(custId, RIB));
    }

    public void addNewAccount() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please provide customer name:");
        String name = input.nextLine();
        Customer theCust = null;
        for (String custId : customers.keySet()) {
            Customer c = customers.get(custId);
            if (c.getName().equals(name)) {
                theCust = c;
                break;
            }
        }

        if (theCust == null) {
            System.out.println("There is no customer with that name !!");
            return;
        }

        String rib = getNewRandomRib();
        Account a = new Account(theCust.getId(), rib);
        accounts.put(rib, a);
        System.out.println("In a few moments, you'll see your RIB. Don't forget it, you'll have to pay to see it again.");
        System.out.println("The RIB of your new account is : " + rib);
    }

    @org.jetbrains.annotations.NotNull
    private String getNewRandomRib() {
        java.util.Random r = new Random();

        int rib = 0;
        do {
            rib = r.nextInt(10000);
        } while (accounts.containsKey(Integer.toString(rib)));
        return Integer.toString(rib);

    }

    public void printAccounts() {
        for (Account account : accounts.values()) {
            System.out.println("There is an account owned by " + account.getCustomerId() + " on " + account.getOpenedOn() + " with the RIB " + account.getRIB());
        }
    }

    public void printAccountMoney() {
        for (Account account : accounts.values()) {
            System.out.println("There is " + String.format("%.2f", account.getBalance()) + "€ on " + account.getCustomerId() + " account." + "(RIB = " + account.getRIB() + ")");

        }
    }

    public void addMoneyToAccount() {
        Scanner input = new Scanner(System.in);
        Account theAccount = null;

        while (true) {
            System.out.println("Which account do you want to credit ? Put the RIB of this account here :");
            String whichAccount = input.nextLine();

            for (String rib : accounts.keySet()) {
                if (rib.equals(whichAccount)) {
                    theAccount = accounts.get(rib);
                    break;
                }
            }

            if (theAccount != null) {
                break;
            }

            System.out.println("Sorry, account does not exist.");
        }

        System.out.println("This account is affiliated to " + theAccount.getCustomerId() + ".");
        System.out.println("How much money do you want to credit on account " + theAccount.getRIB() + " ?");
        double appendMoney = input.nextDouble();
        theAccount.addMoney(appendMoney);
    }

    public void getAccountByJSON() {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        final String json = gson.toJson(accounts.get(0));
        System.out.println(json);
        System.out.println("Enter a JSON String to use an existing account : ");
        Scanner input = new Scanner(System.in);
        String JsonString = input.nextLine();
        Account a = gson.fromJson(JsonString, Account.class);
        accounts.put(a.getRIB(), a);
        printAccounts();

    }

    public void createJsonFile() {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        try {
            final FileWriter fileWriter = new FileWriter("save.json", false);
            final JsonWriter jsonWriter = new JsonWriter(fileWriter);
            jsonWriter.beginObject();
            jsonWriter.name("Customers");
            jsonWriter.beginArray();
            for (Customer c : customers.values()) {
                gson.toJson(c, Customer.class, jsonWriter);
            }
            jsonWriter.endArray();
            jsonWriter.name("Accounts");
            jsonWriter.beginArray();
            for (Account a : accounts.values()) {
                gson.toJson(a, Account.class, jsonWriter);
            }
            jsonWriter.endArray();
            jsonWriter.endObject();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void readJsonFile() {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        try {
            final FileReader fileReader = new FileReader("save.json");
            final JsonReader jsonReader = new JsonReader(fileReader);
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                if (name.equals("Customers")) {
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        Customer c = gson.fromJson(jsonReader, Customer.class);
                        this.customers.put(c.getId(), c);
                    }
                    jsonReader.endArray();
                } else if (name.equals("Accounts")) {
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        Account a = gson.fromJson(jsonReader, Account.class);
                        this.accounts.put(a.getRIB(), a);
                    }
                    jsonReader.endArray();
                }
            }

            jsonReader.endObject();
            fileReader.close();

        } catch (java.io.IOException io) {
            System.out.println(io.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("ISE: " + e.getMessage());
        }

    }

    public void createCustomer() {
        String customerId;
        String phoneNumber;
        String customerName;
        String customerBirthDate;
        String address;
        String emailAddress;
        String password;
        String ribOfAccountToCreditBySalary;
        double salary;

        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the StarsBank client creation service! Let's start the procedure. ");
        System.out.println("Enter your name (example : Firstname FAMILY-NAME) : ");
        customerName = input.nextLine();

        System.out.println("Enter a phone number. If you don't have a phone, please enter : no_phone. ");
        System.out.println("Warning ! : If you do not have an address, telephone number or email address, the account creation procedure will be stopped.");
        phoneNumber = input.nextLine();

        System.out.println("Enter your email address. If you don't have an email address, please enter : no_email . ");
        emailAddress = input.nextLine();

        System.out.println("Enter your born date (format : YYYY-MM-DD) : ");
        customerBirthDate = input.nextLine();

        System.out.println("Enter your address. If you don't have an address, please enter : no_address .");
        address = input.nextLine();

        System.out.println("Please enter the exact amount of your salary. " +
                "It will be credited to your account each month. " + "\n" +
                "Be careful if you enter a false salary (verified every month), " +
                "you risk 27 years in prison and a fine of €300,000.");
        salary = input.nextDouble();

        System.out.println("Please enter the RIB of the account into which you want your salary to be paid. ");
        System.out.println("If you do not have an account yet, please enter rib 0000, you can change it later : ");
        ribOfAccountToCreditBySalary = input.nextLine();

        input.nextLine();

        System.out.println("Define a password");
        password = input.nextLine();

        customerId = UUID.randomUUID().toString();

        if (address.equals("no_address") & emailAddress.equals("no_email") & phoneNumber.equals("no_phone")) {
            customerId = null;
        }

        Customer c = new Customer(customerId, customerName, emailAddress, password, phoneNumber, customerBirthDate, address, ribOfAccountToCreditBySalary, salary);
        customers.put(c.getId(), c);
    }

    @SuppressWarnings("SpellCheckingInspection")
    public void ShowMainMenu() {
        System.out.println("Welcome to StarsBank, please choose an action:");
        System.out.println("1. Create new customer");
        System.out.println("2. Create new account");
        System.out.println("3. Do a transaction with another client");
        System.out.println("4. Login as customer");
        System.out.println("5. List Accounts");
        System.out.println("6. List Customers");
        System.out.println("7. Login as admin");
        System.out.println("8. Change the RIB of the account who will receive your salary");
        System.out.println("9. Suspend an account      (Admin)");
        System.out.println("10. Delete an account      (Admin)");
        System.out.println("11. Unsuspend an account   (Admin)");
        System.out.println("12. Quit");
    }

    public void printCustomers() {
        System.out.println("There are " + this.customers.size() + " customers:");
        for (Customer c : customers.values()) {
            System.out.println("Customer id: " + c.getId() + ", Customer name: " + c.getName());
        }
    }

    public void transaction() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your RIB");
        String sourceRib = input.nextLine();
        System.out.println("If you want to do a transaction, you must enter the RIB of the account that you want to pay : ");
        System.out.println("These accounts are available :");
        printAccounts();
        String destRib = input.nextLine();
        System.out.println("How much money do you want to credit to the account with the RIB " + destRib);
        double amount = input.nextDouble();
        if (accounts.get(sourceRib).getBalance() < amount) {
            System.out.println("You don't have enough money. The transaction will be delete.");
            amount = 0;
        } else {
            Account sourceAccount = accounts.get(sourceRib);
            sourceAccount.removeMoney(amount);
            Account destAccount = accounts.get(destRib);
            destAccount.addMoney(amount);
        }
    }

    public void defineRibAccountToPay() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please provide customer name:");
        String name = input.nextLine();
        Customer theCust = null;
        for (String custId : customers.keySet()) {
            Customer c = customers.get(custId);
            if (c.getName().equals(name)) {
                theCust = c;
                break;
            }
        }

        if (theCust == null) {
            System.out.println("There is no customer with that name !!");
            return;
        }

        System.out.println("Please enter the RIB of the account into which you want your salary to be paid : ");
        String ribAccountToPay = input.nextLine();
        theCust.setRibOfAccountToCreditBySalary(ribAccountToPay);


    }

    boolean logged = false;
    String loggedInCust = "";
    boolean loggedAsAdmin = false;

    public void login() {
        Scanner input = new Scanner(System.in);
        System.out.println("To login as customer, please enter your name");
        String nameLoginCustomer = input.nextLine();
        System.out.println("Now please enter your password");
        String providedPwd = input.nextLine();
        Customer theCust = null;
        for (String custId : customers.keySet()) {
            Customer c = customers.get(custId);
            if (c.getName().equals(nameLoginCustomer)) {
                theCust = c;
                break;
            }
        }

        if (theCust == null) {
            System.out.println("There is no customer with that name !!");
            return;
        }

        byte[] pwdHash = Customer.getHash(providedPwd);
        if (Arrays.equals(pwdHash, theCust.getPasswordHash())) {
            boolean login = true;
            loggedInCust = theCust.getId();
            System.out.println("Logged successfully !");
        }
    }

    public void loginAsAdmin() {
        Admin admin = new Admin("TheAdminPassword");
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the admins password");
        String providedPwd = input.nextLine();
        byte[] pwdHash = Admin.getHash(providedPwd);
        if (Arrays.equals(pwdHash, admin.getPwdHash())) {
            boolean loggedAsAdmin = true;
            System.out.println("You are login as admin !");
        }
    }

    public void suspendAccount() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the RIB of the account that you want to suspend : ");
        String nameAccountToSuspend = input.nextLine();
        Account accountToSuspend = accounts.get(nameAccountToSuspend);
        try {
            accountToSuspend.setStatus(Account.Status.Suspended);
        } catch (AccountInvalidStatusChangeException e) {
            System.err.println(e.getMessage());
        }
    }

    public void closeAccount() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the RIB of the account that you want to close : ");
        String ribAccountToClose = input.nextLine();
        Account accountToClose = accounts.get(ribAccountToClose);
        try {
            accountToClose.setStatus(Account.Status.Closed);
        } catch (AccountInvalidStatusChangeException e){
            System.err.println(e.getMessage());
        }
        if (accountToClose.getMoney() == 0) {
            accountToClose.setRIB(null);
            accountToClose.setCustomerId(null);
        } else {
            accountToClose.setRIB(null);
            accountToClose.setMoney(0);
            accountToClose.setCustomerId(null);
        }
    }

    @SuppressWarnings("SpellCheckingInspection")
    public void unsuspendAccount(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the RIB of the account that you want to reactive : ");
        String ribAccountToReactive = input.nextLine();
        Account accountToReactive = accounts.get(ribAccountToReactive);
        try{
            accountToReactive.setStatus(Account.Status.Active);
        } catch (AccountInvalidStatusChangeException e){
            System.err.println(e.getMessage());
        }

    }

    /**
     * Registers a daily scheduled task (Windows Task Scheduler or cron on Unix)
     * that runs ScheduledSalary every day at 08:00, from the current working directory
     * so that save.json is resolved correctly.
     * On Windows this requires administrator privileges.
     */
    public void setupScheduledTask() {
        // Resolve the JAR that is currently running
        String jarPath;
        try {
            jarPath = new java.io.File(
                    Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()
            ).getAbsolutePath();
        } catch (java.net.URISyntaxException e) {
            System.out.println("[Scheduler] Could not determine JAR path: " + e.getMessage());
            return;
        }

        // Working directory = wherever the JAR lives (so save.json stays next to it)
        String workDir = new java.io.File(jarPath).getParent();
        String javaExe  = ProcessHandle.current().info().command().orElse("java");
        String os = System.getProperty("os.name", "").toLowerCase();

        if (os.contains("win")) {
            setupWindowsTask(javaExe, jarPath, workDir);
        } else {
            setupCronJob(javaExe, jarPath, workDir);
        }
    }

    private void setupWindowsTask(String javaExe, String jarPath, String workDir) {
        final String TASK_NAME = "StarsBankSalary";

        // Check if the task already exists
        try {
            Process check = new ProcessBuilder("schtasks", "/Query", "/TN", TASK_NAME)
                    .redirectErrorStream(true)
                    .start();
            check.waitFor();
            if (check.exitValue() == 0) {
                System.out.println("[Scheduler] Salary task already registered.");
                return;
            }
        } catch (Exception e) {
            // schtasks not available — skip silently
            return;
        }

        // Build the command that the task will run
        // We wrap it in cmd /c so the working directory is honoured
        String action = String.format("cmd /c \"cd /d \"%s\" && \"%s\" -cp \"%s\" ScheduledSalary\"",
                workDir, javaExe, jarPath);

        try {
            Process create = new ProcessBuilder(
                    "schtasks", "/Create",
                    "/TN", TASK_NAME,
                    "/TR", action,
                    "/SC", "DAILY",
                    "/ST", "08:00",
                    "/F"          // overwrite if somehow it exists
            ).redirectErrorStream(true).start();

            String output = new String(create.getInputStream().readAllBytes());
            create.waitFor();

            if (create.exitValue() == 0) {
                System.out.println("[Scheduler] Daily salary task registered successfully (runs at 08:00).");
            } else {
                System.out.println("[Scheduler] Could not register task (run as Administrator to enable automatic salary payments).");
                System.out.println("[Scheduler] " + output.trim());
            }
        } catch (Exception e) {
            System.out.println("[Scheduler] Failed to create scheduled task: " + e.getMessage());
        }
    }

    private void setupCronJob(String javaExe, String jarPath, String workDir) {
        final String MARKER = "# StarsBank salary";
        String cronLine = String.format("0 8 * * * cd \"%s\" && \"%s\" -cp \"%s\" ScheduledSalary %s",
                workDir, javaExe, jarPath, MARKER);

        try {
            // Read existing crontab
            Process read = new ProcessBuilder("crontab", "-l")
                    .redirectErrorStream(true)
                    .start();
            String existing = new String(read.getInputStream().readAllBytes());
            read.waitFor();

            if (existing.contains(MARKER)) {
                System.out.println("[Scheduler] Salary cron job already registered.");
                return;
            }

            // Append our line and write back
            String updated = existing + "\n" + cronLine + "\n";
            Process write = new ProcessBuilder("crontab", "-")
                    .redirectErrorStream(true)
                    .start();
            write.getOutputStream().write(updated.getBytes());
            write.getOutputStream().close();
            write.waitFor();

            if (write.exitValue() == 0) {
                System.out.println("[Scheduler] Daily salary cron job registered (runs at 08:00).");
            } else {
                System.out.println("[Scheduler] Could not register cron job.");
            }
        } catch (Exception e) {
            System.out.println("[Scheduler] Failed to create cron job: " + e.getMessage());
        }
    }
}

