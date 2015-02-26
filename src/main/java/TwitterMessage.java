import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * Created by mahmoud on 1/29/15.
 */
public class TwitterMessage {
    public long id;
    public String idStr;

    public int favoriteCount, retweetCount;

    public String inReplyToScreenName;
    public boolean retweeted, favorited, possiblySensitive, truncated;

    public String text;
    public String lang;
    public String source;
    public String filterLevel;

    public Date createdAt;
    public String timestampMs;

    public int[] contributors;

    public long inReplyToStatusId, inReplyToUserId;
    public String inReplyToStatusIdStr, inReplyToUserIdStr;

    public TwitterPlace place;
    public TwitterGeo geo;
    public TwitterCoordinates coordinates;
    public TwitterEntities entities, extendedEntities;
    public TwitterUser user;

    public TwitterMessage(){
        super();
        place = new TwitterPlace();
        geo = new TwitterGeo();
        coordinates = new TwitterCoordinates();
        entities = new TwitterEntities();
        extendedEntities = new TwitterEntities();
        user = new TwitterUser();
    }

    public static void main( String[] args){
        ArrayList<String> jsonFileNames = new ArrayList<>();
        String directory = "/Users/mahmoud/europa/20150127-004709/";
        File dir = new File(directory);
        File[] filesList = dir.listFiles();
        assert filesList != null;
        for (File file : filesList) {
            if (file.isFile()) {
                jsonFileNames.add(file.getName());
            }
        }

//
//        TigerJson tj = new TigerJson();
//        for(String jsonFileName:jsonFileNames) {
////            System.out.println("File: " + jsonFileName);
//            String json = Tiger.readFile(directory + jsonFileName);
////            System.out.println(json);
//            TwitterMessage message = tj.twitterify(json);
//            if(null == message){
//                System.err.println(json);
//                System.out.println("File: " + jsonFileName);
//
//            }
//        }

    }


}
