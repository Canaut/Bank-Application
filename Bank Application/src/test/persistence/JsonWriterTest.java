package persistence;

import model.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;



class JsonWriterTest extends JsonTest {
    private WorkRoom wr;
    private JsonWriter writer;

    @BeforeEach
    void runBefore() {
        wr = new WorkRoom("wr");
    }

    @Test
    void testWriterInvalidFile() {
        try {
            writer = new JsonWriter("./data/my\0Illegal:fileName.json");
            writer.open();
            fail("IOException expected");
        } catch (FileNotFoundException e) {
            // expected
        }
    }

    @Test
    void testWriterEmptyWorkRoom() {
        try {
            writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            wr = reader.read();
            assertEquals("wr", wr.getName());
            assertEquals(0, wr.numUserAccounts());
        } catch (IOException e) {
            fail("Shouldn't have thrown exception");
        }

    }

    @Test
    void testWriterGeneralWorkroom() {
        try {

            wr.addUserAccounts(getUserAccount().get(0));
            wr.addUserAccounts(getUserAccount().get(1));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            wr = reader.read();
            assertEquals("wr", wr.getName());
            List<UserAccount> userAccounts = wr.getUserAccounts();
            assertEquals(2, userAccounts.size());
            checkUserAccounts("user12", "pw1234", userAccounts.get(0));
            checkUserAccounts("user23", "pw2345", userAccounts.get(1));

            assertEquals("CAD", userAccounts.get(0).userBankAccounts.get(0).currency);
            assertEquals(500, userAccounts.get(0).userBankAccounts.get(0).checkBalance(), 0.0001);

            assertEquals("USD", userAccounts.get(1).userBankAccounts.get(0).currency);
            assertEquals(100, userAccounts.get(1).userBankAccounts.get(0).checkBalance(), 0.0001);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterSetWorkRoomFromDatabase() {
        try {
            UserAccountDatabase userAccountDatabase = new UserAccountDatabase();
            UserAccount testUser = new UserAccount("johnny", "johnny");
            ChequingBankAccount testChk = new ChequingBankAccount();
            testUser.addAccount(testChk);
            userAccountDatabase.addBankAccount(testUser);
            wr.setUserAccountDatabase(userAccountDatabase);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            wr = reader.read();
            assertEquals("wr", wr.getName());
            List<UserAccount> userAccounts = wr.getUserAccounts();
            assertEquals(1, userAccounts.size());
            checkUserAccounts("johnny", "johnny", userAccounts.get(0));

            assertEquals("CAD", userAccounts.get(0).userBankAccounts.get(0).currency);
            assertEquals(0, userAccounts.get(0).userBankAccounts.get(0).checkBalance(), 0.0001);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}


