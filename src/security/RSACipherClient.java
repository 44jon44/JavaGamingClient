/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

/**
 * Esta clase se ocupa del encriptado y desencriptado de la informacion que le
 * llega al servidor y que sale del mismo.
 *
 * @author Alex Hurtado , Ibai Arriola
 */
public class RSACipherClient {

    private static byte[] salt = "this is the salt".getBytes();
    private static String key;
    private static byte[] privateKey;
    private static final ResourceBundle RB = ResourceBundle.getBundle("security.rsa");
    //Logger para la clase en encriptacion.
    private static final Logger LOG = Logger.getLogger(RSACipherClient.class.getName());

    /**
     * Este metodo sirve para convertir un array de bytes en una cadena
     * haxedecimal.
     *
     * @param bytes
     * @return String Cadena hexadecimal que representa al array de bytes pasado
     * como parámetro.
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return sb.toString();
    }

    /**
     * Este método encripta una cadena de texto pasada como parámetro.
     *
     * @param plainText Texto que se quiere encriptar.
     * @return byte[] Array de bytes del texto encriptado.
     */
    public static String encrypt(byte[] plainText) throws IllegalBlockSizeException, BadPaddingException, IOException {
        Cipher cipher;
        String bs = null;
        PublicKey key;
        String filePubKey = RB.getString("PUBLIC_KEY_FILE");
        try {
            // Leemos la clave publica del archivo en el cual lo hemos escrito            
            InputStream is = RSACipherClient.class.getResourceAsStream(filePubKey);
            byte[] encKey = toByteArray(is);
            is.close();
            
            key = readPublicKey(encKey);
            
            // Obtenemos una instancide de Cipher con el algoritmos que vamos a usar "RSA/ECB/OAEPWithSHA1AndMGF1Padding"
            cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            // Iniciamos el Cipher en ENCRYPT_MODE y le pasamos la clave publica
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // Le decimos que cifre (método doFinal(mensaje))
            //bs = bytesToHexString(cipher.doFinal(plainText));
            bs=DatatypeConverter.printHexBinary(cipher.doFinal(plainText));
            System.out.println("Contraseña Cifrada " + bs);
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
            LOG.log(Level.SEVERE, null, ex.getMessage());
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(RSACipherClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return bs;
        
    }
        public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        // read bytes from the input stream and store them in buffer
        while ((len = in.read(buffer)) != -1) {
            // write bytes from the buffer into output stream
            os.write(buffer, 0, len);
        }
        return os.toByteArray();
    }
        public static byte[] decrypt(byte[] ciphertext) {
        Cipher cipher;
        byte[] bs = null;
        PrivateKey key;
        try
        {
            // Leemos la clave publica del archivo en el cual lo hemos escrito
            key = readPrivateKey("C:\\Users\\jonma\\Documents\\NetBeansProjects\\JavaGamingServer\\src\\java\\security\\RSAPrivate.key");
            // Obtenemos una instancide de Cipher con el algoritmos que vamos a usar "RSA/ECB/OAEPWithSHA1AndMGF1Padding"
            cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            // Iniciamos el Cipher en DECRYPT_MODE y le pasamos la clave privada
            cipher.init(Cipher.DECRYPT_MODE, key);
            // Le decimos que cifre (método doFinal(mensaje))
            bs = cipher.doFinal(ciphertext);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException | InvalidKeySpecException ex)
        {
            LOG.log(Level.SEVERE, null, ex.getMessage());
        }
        return bs;
    }

    /**
     * Este método devuelve la clave privada almacenada en un fichero a partir
     * de la ruta relativa del mismo.
     *
     * @param filePath Path relativo del fichero que contiene la clave.
     * @return PrivateKey Clave privada almacenada el fichero.
     * @throws IOException Excepción que se lanza si no existe el fichero.
     * @throws NoSuchAlgorithmException Excepcion que se lanza si el algoritmo
     * especificado en el getInstance(String algorithm) no existe.
     * @throws InvalidKeySpecException Excepción que se lanza si la
     * especificación de la clave es inapropiada para el keySpec especificado.
     */
    public static PrivateKey readPrivateKey(String filePath) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(fileReader(filePath));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * Este método devuelve la clave pública almacenada en un fichero a partir
     * de la ruta relativa del mismo.
     *
     * @param filePath Path relativo del fichero que contiene la clave.
     * @return PublicKey Clava privada almacenada en un fichero.
     * @throws IOException Excepción que se lanza si no existe el fichero.
     * @throws NoSuchAlgorithmException Excepcion que se lanza si el algoritmo
     * especificado en el getInstance(String algorithm) no existe.
     * @throws InvalidKeySpecException Excepción que se lanza si la
     * especificación de la clave es inapropiada para el keySpec especificado.
     */
    public static PublicKey readPublicKey(byte[] key) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(publicSpec);
    }

    /**
     * Este método devuelve un array de bytes con el contenido del fichero cuyo
     * path se ha pasado como parámetro.
     *
     * @param path Path relativo del fichero que se quiere leer.
     * @return byte[] Array de bytes del contenido del fichero.
     */
    private static byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex.getMessage());
        }
        return ret;
    }
}

