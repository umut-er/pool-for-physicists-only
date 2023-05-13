import java.io.UnsupportedEncodingException;
import database.PoolDatabase;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import ui.MenuFrame;
import ui.PoolFrame;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException, JacksonUtilityException, FirebaseException{
        PoolDatabase database=new PoolDatabase();
        MenuFrame menu=new MenuFrame();
        //PoolFrame frame = new PoolFrame("User1", "User2");
        //frame.start();
    }
}
