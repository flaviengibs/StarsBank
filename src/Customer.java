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

    public final static byte[] salt = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");


    public Customer(String id, String name, String emailAddress, String password, String phoneNumber, String customerBornDay, String address){
        this.id = id;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.birthDate = customerBornDay;
        this.emailAddress = emailAddress;
        this.passwordHash = getHash(password);
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

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public byte[] getPasswordHash() {
        return passwordHash;
    }

}

