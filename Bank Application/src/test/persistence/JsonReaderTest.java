package persistence;

import model.UserAccountDatabase;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    private JsonReader reader;

    @Test
    void testReaderNonExistentFile() {
        try {
            reader = new JsonReader("./data/doesNotExist.json");
            WorkRoom wr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkRoom.json");
        try {
            WorkRoom wr = reader.read();
            assertEquals("wr", wr.getName());
            assertEquals(0, wr.numUserAccounts());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderSomeWorkRooms() {

        try {
            JsonReader reader = new JsonReader("./data/testReaderSomeWorkRoom.json");
            WorkRoom wr = reader.read();
            assertEquals("wr", wr.getName());
            UserAccountDatabase userAccountDatabase = wr.getAccountDatabase();
            assertEquals(2, userAccountDatabase.userAccounts.size());
            checkUserAccounts("user12", "pw1234", userAccountDatabase.userAccounts.get(0));
            checkUserAccounts("user23", "pw2345", userAccountDatabase.userAccounts.get(1));

            assertEquals("CAD", userAccountDatabase.userAccounts.get(0).userBankAccounts.get(0).currency);
            assertEquals(500,
                    userAccountDatabase.userAccounts.get(0).userBankAccounts.get(0).checkBalance(), 0.0001);
            assertEquals("USD", userAccountDatabase.userAccounts.get(0).userBankAccounts.get(1).currency);
            assertEquals(100,
                    userAccountDatabase.userAccounts.get(0).userBankAccounts.get(1).checkBalance(), 0.0001);

            assertEquals("USD", userAccountDatabase.userAccounts.get(1).userBankAccounts.get(0).currency);
            assertEquals(100,
                    userAccountDatabase.userAccounts.get(1).userBankAccounts.get(0).checkBalance(), 0.0001);
            assertEquals("CAD", userAccountDatabase.userAccounts.get(1).userBankAccounts.get(1).currency);
            assertEquals(500,
                    userAccountDatabase.userAccounts.get(1).userBankAccounts.get(1).checkBalance(), 0.0001);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
