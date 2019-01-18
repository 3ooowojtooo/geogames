package com.kurocho.geogames.utils.play_game;

import android.util.Log;
import com.google.crypto.tink.Aead;
import com.google.crypto.tink.aead.AeadConfig;
import com.google.crypto.tink.subtle.AesGcmJce;
import com.kurocho.geogames.data.my_games.DecryptedLevelEntity;
import com.kurocho.geogames.data.my_games.EncryptedLevelEntity;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Singleton
public class GameDecryptUtils {

    public interface DecryptLevelCallback{
        void onSuccess(DecryptedLevelEntity decryptedLevelEntity);
        void onIncorrectPassword();
    }


    private MessageDigest messageDigest;

    @Inject
    GameDecryptUtils(){
        try {
            AeadConfig.register();
            messageDigest = MessageDigest.getInstance("SHA-256");
        }  catch(NoSuchAlgorithmException e){
            throw new RuntimeException("SHA-256 algorithm unsupported.", e);
        } catch(GeneralSecurityException e){
            throw new RuntimeException("Could not register aead.", e);
        }
    }

    public void decryptEncryptedLevelEntity(EncryptedLevelEntity encryptedLevelEntity, String password, DecryptLevelCallback callback) {
        Log.i("PLAY", "password: " + password);
        Log.i("PLAY", "ord: " + encryptedLevelEntity.getOrd());
        Log.i("PLAY", "desc: " + encryptedLevelEntity.getEncryptedDescription());
        password = password.toLowerCase().replaceAll("\\s","");
        byte[] hashedPassword = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        byte[] decryptedDescriptionBytes;
        try {
            Aead aead = new AesGcmJce(hashedPassword);
            decryptedDescriptionBytes = aead.decrypt(hexStringToByteArray(encryptedLevelEntity.getEncryptedDescription()), null);
        } catch (GeneralSecurityException e){
            e.printStackTrace();
            callback.onIncorrectPassword();
            return;
        }
        String decryptedDescription;
        try{
            decryptedDescription = new String(decryptedDescriptionBytes, "UTF-8");
        } catch(UnsupportedEncodingException e){
            throw new RuntimeException("UTF-8 encoding unsupported", e);
        }

        callback.onSuccess(createDecryptedLevelEntity(encryptedLevelEntity, decryptedDescription));
    }


    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    private DecryptedLevelEntity createDecryptedLevelEntity(EncryptedLevelEntity encryptedLevelEntity, String decryptedDescription){
        DecryptedLevelEntity entity = new DecryptedLevelEntity();
        entity.setOrd(encryptedLevelEntity.getOrd());
        entity.setCoordinates(encryptedLevelEntity.getCoordinates());
        entity.setGameId(encryptedLevelEntity.getGameId());
        entity.setDescription(decryptedDescription);
        return entity;
    }

}
