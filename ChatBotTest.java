package registrationloginapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class ChatBotTest {
    
    public ChatBotTest() {
    }

    //runs before every test: clears the arrays and loads the test data
    @BeforeEach
    public void setUp() {
        ChatBot.resetData();
        ChatBot.loadTestData();
    }

    //Test 1: Sent Messages array correctly populated
    @Test
    public void testSentMessagesArrayPopulated() {

        assertEquals(2, ChatBot.getSentCount());
        assertEquals("Did you get the cake?",
                ChatBot.getSentMessages()[0].getContent());
        assertEquals("It is dinner time !",
                ChatBot.getSentMessages()[1].getContent());
    }

    //Test 2: Display the longest message
    @Test
    public void testLongestMessage() {

        assertEquals("Where are you? You are late! I have asked you to be on time.",
                ChatBot.getLongestMessage());
    }

    //Test 3: Search for message ID (test message 4)
    @Test
    public void testSearchByMessageId() {

        String id = ChatBot.getSentMessages()[1].getMessageId();

        assertEquals("Recipient: 0838884567, Message: It is dinner time !",
                ChatBot.searchByMessageId(id));
    }

    //Test 4: Search all messages for a particular recipient
    @Test
    public void testSearchByRecipient() {

        String expected = "Where are you? You are late! I have asked you to be on time.\n"
                + "Ok, I am leaving without you.\n";

        assertEquals(expected, ChatBot.searchByRecipient("+27838884567"));
    }

    //Test 5: Delete a message using the message hash (test message 2)
    @Test
    public void testDeleteByHash() {

        String hash = ChatBot.getStoredHashes()[0];

        assertEquals("Message \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
                ChatBot.deleteByHash(hash));

        //only test message 5 should be left in the stored array
        assertEquals(1, ChatBot.getStoredCount());
    }

    //Test 6: Display report
    @Test
    public void testDisplayReport() {

        String expected = "===== STORED MESSAGES REPORT =====\n"
                + "Message 1\n"
                + "Message ID: " + ChatBot.getStoredIds()[0] + "\n"
                + "Message Hash: " + ChatBot.getStoredHashes()[0] + "\n"
                + "Recipient: +27838884567\n"
                + "Message: Where are you? You are late! I have asked you to be on time.\n\n"
                + "Message 2\n"
                + "Message ID: " + ChatBot.getStoredIds()[1] + "\n"
                + "Message Hash: " + ChatBot.getStoredHashes()[1] + "\n"
                + "Recipient: +27838884567\n"
                + "Message: Ok, I am leaving without you.\n\n";

        assertEquals(expected, ChatBot.getReport());
    }
    
}