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

    public static void createAccount(String username, String password) throws UnsupportedEncodingException, JacksonUtilityException, FirebaseException{
        accountCount++;
        Map<String, Object> accountInfo=new LinkedHashMap<String, Object>();
        Map<String, Object> newAccount=new LinkedHashMap<String, Object>();
        accountInfo.put( "Password", password);
        accountInfo.put( "Level", 1);
        accountInfo.put( "Number", accountCount);
		newAccount.put(username, accountInfo);
        firebase.patch(newAccount);
    }

    public static String getAccountPassword(String username) throws UnsupportedEncodingException, FirebaseException{
        FirebaseResponse response=firebase.get(username);
        JSONObject object = new JSONObject(response.getRawBody());
        return object.get("Password").toString();
    }

    public static int getAccountLevel(String username) throws UnsupportedEncodingException, FirebaseException{
        FirebaseResponse response=firebase.get(username);
        JSONObject object = new JSONObject(response.getRawBody());
        return Integer.parseInt(object.get("Level").toString());
    }

    public static int getAccountNumber(String username) throws UnsupportedEncodingException, FirebaseException{
        FirebaseResponse response=firebase.get(username);
        JSONObject object = new JSONObject(response.getRawBody());
        return Integer.parseInt(object.get("Number").toString());
    }

    public static void levelUpAccount(String username) throws UnsupportedEncodingException, FirebaseException, JacksonUtilityException{
        int level=getAccountLevel(username);
        level++;
        firebase.get(username)
        // TODO: FIX PROBLEM
    }
}
