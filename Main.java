import java.io.UnsupportedEncodingException;
import database.PoolDatabase;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import ui.PoolFrame;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException, JacksonUtilityException, FirebaseException{
        PoolDatabase database=new PoolDatabase();
        //PoolDatabase.createAccount("User1", "user1234");
        //PoolDatabase.createAccount("User2", "player1234");
        //PoolDatabase.levelUpAccount("User1");
        PoolFrame frame = new PoolFrame("User1", "User2");
        frame.start();
    }
}
