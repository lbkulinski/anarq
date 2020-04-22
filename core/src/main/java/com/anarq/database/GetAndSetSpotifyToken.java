import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

class GetAndSetSpotifyToken {

  String username;
  String spotifyToken;
  findUser user;

  public GetAndSetSpotifyToken(String username, String spotifyToken) {
    this.username = username;
    this.spotifyToken = spotifyToken;
    user = new findUser(username);
  }

  /**
   * Function to get the users' authorization-code from the database
   * @return returns the users' authorization-code or null if they do not have said code
   */
  public String getSpotifyToken() {
    Document userDetails = user.find();
    if (userDetails == null) {
      System.out.println("Couldn't find user");
      return null;
    }
    Object authCode;
    authCode = userDetails.get("authorization-code");
    if (authCode == null) {
      System.out.println("User does not have an authorization-code");
      return null;
    }
    System.out.println("\n auth code = " + authCode.toString());
    return (String) authCode;
  }

  /**
   * Function to set/update a users' authorization-code
   */
  public void setSpotifyToken() {

    MongoCollection<Document> collection;
    Bson filter;
    String fieldName = "authorization-code";
    Bson update;

    ConnectToDatabase newConnection = new ConnectToDatabase();
    MongoDatabase database = newConnection.connect();

    collection = database.getCollection("users");
    filter = Filters.regex("username", username);

    update = Updates.set(fieldName, spotifyToken);
    collection.updateOne(filter, update);
  }
}
