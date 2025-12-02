import java.util.Base64;
import java.security.SecureRandom;

public class GenerateSecret {
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        String secret = Base64.getEncoder().encodeToString(bytes);
        System.out.println("=== SUA CHAVE JWT ===");
        System.out.println(secret);
        System.out.println("=====================");
        System.out.println("Copie esta chave para o arquivo .env");
    }
}