package com.blue.code.msgBoard.utils;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
public class EncryptionUtility
{
private EncryptionUtility(){}
public static String getKey()
{
String key=null;
try
{
KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");
keyGenerator.init(256);
SecretKey secretKey=keyGenerator.generateKey();
byte[] keyBytes=secretKey.getEncoded();
return Base64.getEncoder().encodeToString(keyBytes);
}catch(Exception exception)
{
System.out.println(exception);
}
return key;
}
public static String encrypt(String password,String key)
{
String encryptedPassword=null;
try
{
SecretKeySpec secretKeySpec=new SecretKeySpec(Base64.getDecoder().decode(key),"AES");
Cipher cipher=Cipher.getInstance("AES");
cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
byte[] encryptedBytes=cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
encryptedPassword=Base64.getEncoder().encodeToString(encryptedBytes);
}catch(Exception exception)
{
System.out.println(exception);
}
return encryptedPassword;
}
public static String decrypt(String encryptedPassword,String key)
{
String password=null;
try
{
SecretKeySpec secretKeySpec=new SecretKeySpec(Base64.getDecoder().decode(key),"AES");
Cipher cipher=Cipher.getInstance("AES");
cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
byte[] encryptedBytes=Base64.getDecoder().decode(encryptedPassword);
byte[] decryptedBytes=cipher.doFinal(encryptedBytes);
password=new String(decryptedBytes,StandardCharsets.UTF_8);
}catch(Exception exception)
{
System.out.println(exception);
}
return password;
}
}
