package codeu.controller;

import codeu.model.data.BotRequest;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.persistence.PersistentStorageAgent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;

public class RocketBotHandlerTest {
  private UserStore mockUserStore;
  private MessageStore mockMessageStore;
  private final UUID CONVERSATION_ID_ONE = UUID.randomUUID();
  private final UUID USER_ID_ONE = UUID.randomUUID();
  private RocketBotHandler rocketBot;
  private final String OUTPUT_ONE = "3...2...1...BLASTOFF!";
  private final UUID MESSAGE_ID_ONE = UUID.randomUUID();

  @Before
  public void setup() {
    PersistentStorageAgent mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);
    mockUserStore = UserStore.getTestInstance(mockPersistentStorageAgent);
    mockMessageStore = MessageStore.getTestInstance(mockPersistentStorageAgent);
    rocketBot = new RocketBotHandler();
    rocketBot.setUserStore(mockUserStore);
    rocketBot.setMessageStore(mockMessageStore);
  }

  /**
   * This only tests that a User was not created, and does not check on the other functionality
   * of the implementation. I'd like some feedback on the next logical steps to take.
   */
  @Test
  public void testHandleExistingUser() {
    User existingUser = new User(
        USER_ID_ONE,
        "RocketBot",
        "unusable-password-hash",
        Instant.ofEpochMilli(1000));
    final List<User> userList = new ArrayList<>();
    userList.add(existingUser);
    mockUserStore.setUsers(userList);
    ArrayList<String> args = new ArrayList<>();
    BotRequest request = new BotRequest("rocket",args,CONVERSATION_ID_ONE);

    rocketBot.handler(request);
    Assert.assertEquals(mockUserStore.getUser("RocketBot"),existingUser);
  }

  /**
   * This only tests that a User was created when the handler was called for a bot that
   * had never been called before. Other checks must be added to test for other functionality
   * of the implementation.
   */
  @Test
  public void testHandleNewUser() {
    final List<User> userList = new ArrayList<>();
    mockUserStore.setUsers(userList);
    ArrayList<String> args = new ArrayList<>();
    BotRequest request = new BotRequest("rocket",args,CONVERSATION_ID_ONE);

    Assert.assertNull(mockUserStore.getUser("RocketBot"));
    rocketBot.handler(request);
    Assert.assertNotNull(mockUserStore.getUser("RocketBot"));
  }

  @Test
  public void testMessageSent() {
    User existingUser = new User(
        USER_ID_ONE,
        "RocketBot",
        "unusable-password-hash",
        Instant.ofEpochMilli(1000));
    final List<User> userList = new ArrayList<>();
    userList.add(existingUser);
    mockUserStore.setUsers(userList);
    ArrayList<String> args = new ArrayList<>();
    BotRequest request = new BotRequest("rocket",args,CONVERSATION_ID_ONE);

    Message testMessage = new Message(
        rocketBot.getBotMessageId(),
        request.getConversationId(),
        USER_ID_ONE,
        OUTPUT_ONE,
        rocketBot.getBotMessageTime());

    rocketBot.handler(request);

    List<Message> messagesInConversation = mockMessageStore.getMessagesInConversation(request.getConversationId());
    int numMessages = mockMessageStore.getNumMessages();
    System.out.print("numMessages = ");
    System.out.println(numMessages);

    Assert.assertEquals(messagesInConversation.get(0).getAuthorId(),USER_ID_ONE);
    Assert.assertEquals(messagesInConversation.get(0).getCreationTime(),rocketBot.getBotMessageTime());
    Assert.assertEquals(messagesInConversation.get(0).getContent(),OUTPUT_ONE);
    Assert.assertEquals(messagesInConversation.get(0).getId(),rocketBot.getBotMessageId());
    Assert.assertEquals(messagesInConversation.get(0).getConversationId(),request.getConversationId());

    //Assert.assertEquals(messagesInConversation.get(0), testMessage);
  }
}
