package codeu.controller;

import codeu.model.data.BotRequest;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;

import java.time.Instant;
import java.util.UUID;

/**
 * Handles the Rocket Bot. This bot is triggered by the word "rocket" and responds with a
 * hard-coded message: "3...2...1...BLASTOFF!". The handler first creates a User for the
 * bot, then handles its actions as outlined in BotHandlerInterface.
 */
public class RocketBotHandler implements BotHandlerInterface {

  static final String BOT_NAME = "RocketBot";
  static final String BOT_OUTPUT = "3...2...1...BLASTOFF!";
  static UUID messageId = UUID.randomUUID();
  static Instant messageTime = Instant.now();

  /** Handles bot requests and sends as messages to conversations. */
  public void handler(BotRequest request) {
    final User botUser = userStore.getOrCreateUserWithName(BOT_NAME);
    final UUID BOT_USER_ID = botUser.getId();

    // Convert output and required arguments into Message
    Message message = new Message(
        messageId,
        request.getConversationId(),
        BOT_USER_ID,
        BOT_OUTPUT,
        messageTime);

    // Send message to conversation
    messageStore.addMessage(message);
  }

  /** Accessor class that allows test UUIDs to match, even when randomly assigned. */
  UUID getBotMessageId() {
    return messageId;
  }

  /** Accessor class that allows test Instant to match current Instant. */
  Instant getBotMessageTime() {
    return messageTime;
  }

  /** Store class that gives access to Messages. */
  private MessageStore messageStore = MessageStore.getInstance();

  /** Store class that gives access to Users. */
  private UserStore userStore = UserStore.getInstance();

  public void setUserStore(UserStore value) {
    userStore = value;
  }

  public void setMessageStore(MessageStore value) {
    messageStore = value;
  }

}
