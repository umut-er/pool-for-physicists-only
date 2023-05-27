package database;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;
import org.json.*;

public class PoolDatabase {
    private static Firebase firebase;
    private static int accountCount;

    public PoolDatabase() throws FirebaseException{
        PoolDatabase.firebase=new Firebase("https://pool-for-physicists-only-default-rtdb.europe-west1.firebasedatabase.app/", true);
        accountCount=0;
    }

    public static boolean initialized(){
        return firebase != null;
    }

    public int getCurrentAccountCount(){
        return accountCount;
    }

    public static void createAccount(String username, String password, String backUpQuestion, String backUpAnswer) throws UnsupportedEncodingException, JacksonUtilityException, FirebaseException{
        accountCount++;
        Map<String, Object> accountInfo=new LinkedHashMap<String, Object>();
        Map<String, Object> newAccount=new LinkedHashMap<String, Object>();
        accountInfo.put( "Password", password);
        accountInfo.put( "Level", 1);
        accountInfo.put( "BackUpQuestion", backUpQuestion);
        accountInfo.put( "BackUpAnswer", backUpAnswer);
		newAccount.put(username, accountInfo);
        firebase.patch(newAccount);
    }

    public static String getAccountPassword(String username) throws UnsupportedEncodingException, FirebaseException{
        FirebaseResponse response=firebase.get(username);
        if(!response.getRawBody().equals("null"))
        {
            JSONObject object = new JSONObject(response.getRawBody());
            return object.get("Password").toString();
        }
        return "";
    }

    public static String getAccountBackUpQuestion(String username) throws UnsupportedEncodingException, FirebaseException{
        FirebaseResponse response=firebase.get(username);
        if(!response.getRawBody().equals("null"))
        {
            JSONObject object = new JSONObject(response.getRawBody());
            return object.get("BackUpQuestion").toString();
        }
        return "";
    }

    public static String getAccountBackUpAnswer(String username) throws UnsupportedEncodingException, FirebaseException{
        FirebaseResponse response=firebase.get(username);
        if(!response.getRawBody().equals("null"))
        {
            JSONObject object = new JSONObject(response.getRawBody());
            return object.get("BackUpAnswer").toString();
        }
        return "";
    }

    public static int getAccountLevel(String username) throws UnsupportedEncodingException, FirebaseException{
        FirebaseResponse response=firebase.get(username);
        if(!response.getRawBody().equals("null"))
        {
            JSONObject object = new JSONObject(response.getRawBody());
            return Integer.parseInt(object.get("Level").toString());
        }
        return -1;
    }

    public static void changePassword(String username, String newPassword) throws UnsupportedEncodingException, JacksonUtilityException, FirebaseException{
        Map<String, Object> userData = firebase.get(username).getBody();
        userData.put("Password", newPassword);
        firebase.put(username, userData);
    }

    public static void levelUpAccount(String username, int levelUpCount) throws UnsupportedEncodingException, FirebaseException, JacksonUtilityException{
        int level=getAccountLevel(username);
        level=level+levelUpCount;
        Map<String, Object> userData = firebase.get(username).getBody();
        userData.put("Level", level);
        firebase.put(username, userData);
    }

    public static void levelUpAccount(String username) throws UnsupportedEncodingException, FirebaseException, JacksonUtilityException{
        levelUpAccount(username, 1);
    }

    public static void deleteAccount(String username) throws UnsupportedEncodingException, FirebaseException{
        accountCount--;
        firebase.delete(username);
    }

    public static boolean loginAccount(String username, String password) throws UnsupportedEncodingException, FirebaseException{
        FirebaseResponse response=firebase.get(username);
        if(!response.getRawBody().equals("null"))
        {
            String correctPassword=getAccountPassword(username);
            if(correctPassword.equals(password))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean doesBackUpAnswersMatch(String username, String answer) throws UnsupportedEncodingException, FirebaseException{
        FirebaseResponse response=firebase.get(username);
        if(!response.getRawBody().equals("null"))
        {
            String backUpAnswer=getAccountBackUpAnswer(username).toLowerCase();
            if(backUpAnswer.equals(answer.toLowerCase()))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean accountNotExists(String username) throws UnsupportedEncodingException, FirebaseException{
        FirebaseResponse response=firebase.get(username);
        if(response.getRawBody().equals("null"))
        {
            return true;
        }
        return false;
    }
}
