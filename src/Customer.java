import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.HexFormat;
import java.util.UUID;
public class Customer {
    private String name = "";
    private String id = UUID.randomUUID().toString();
    private String birthDate = "";
    private String phoneNumber = "";
    private String address = "";
    private String emailAddress = "";
    private byte[] passwordHash = null;
    private double salary = 0;
    private String ribOfAccountToCreditBySalary;

    public double getSalary() {
        return salary;
    }
    public final static byte[] salt = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");
    public Customer(String id, String name, String emailAddress, String password, String phoneNumber, String customerBornDay, String address, String ribOfAccountToCreditBySalary, double salary){
        this.id = id;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.birthDate = customerBornDay;
        this.emailAddress = emailAddress;
        this.salary = salary;
        this.passwordHash = getHash(password);
        this.ribOfAccountToCreditBySalary = ribOfAccountToCreditBySalary;
    }

    public static byte @Nullable [] getHash(@NotNull String pass) {
        SecureRandom random = new SecureRandom();

        KeySpec spec = new PBEKeySpec(pass.toCharArray(), Customer.salt, 65536, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return factory.generateSecret(spec).getEncoded();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println("Requested crypto algorithm not available!");
        } catch (java.security.spec.InvalidKeySpecException e) {
            System.out.println("Requested Key Spec not available!");
        }

        return null;
    }

    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }

    public String getBirthDate() {
        return birthDate;
    }
    public String getAddress() {
        return address;
    }
    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public String getRibOfAccountToCreditBySalary() {
        return ribOfAccountToCreditBySalary;
    }

    public void setRibOfAccountToCreditBySalary(String ribOfAccountToCreditBySalary) {
        this.ribOfAccountToCreditBySalary = ribOfAccountToCreditBySalary;
    }
}

