import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.HexFormat;

public class Admin {
    private byte[] passwordHash = null;

    public final static byte[] salt = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");

    public Admin(String password) {
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

    public byte[] getPwdHash() {
        return passwordHash;
    }
}
