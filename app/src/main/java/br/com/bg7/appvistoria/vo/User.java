package br.com.bg7.appvistoria.vo;

import com.orm.SugarRecord;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.com.bg7.appvistoria.service.dto.UserResponse;

/**
 * Created by: elison
 * Date: 2017-07-12
 */
public class User extends SugarRecord<User> {

    private String fullName;
    private String userName;
    private String email;
    private String password;
    private Token token;

    public User() {}

    public User(UserResponse user, br.com.bg7.appvistoria.service.dto.Token tokenFromService, String password) {
        fullName = user.getUserAccounts().get(0).getBasicInfo().getFullName();
        userName = user.getUserAccounts().get(0).getCredentials().getLogin();
        email = user.getUserAccounts().get(0).getBasicInfo().getEmail().getAddress();
        this.password = getHashPassword(password);
        token = new Token(tokenFromService);
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    /**
     *  Return the hash of password to be stored
     *
     * @return
     */
    public static String getHashPassword(String s)
    {
        MessageDigest digest;
        try
        {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(Charset.forName("US-ASCII")),0,s.length());
            byte[] magnitude = digest.digest();
            BigInteger bi = new BigInteger(1, magnitude);
            String hash = String.format("%0" + (magnitude.length << 1) + "x", bi);
            return hash;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return "";
    }

}
