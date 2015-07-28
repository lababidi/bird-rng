package Twitter;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * Created by mahmoud on 1/29/15.
 */
public class Message {
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

    public Place place;
    public Geo geo;
    public Coordinates coordinates;
    public Entities entities, extendedEntities;
    public User user;

    public Message(){
        super();
        place = new Place();
        geo = new Geo();
        coordinates = new Coordinates();
        entities = new Entities();
        extendedEntities = new Entities();
        user = new User();
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
//            Twitter.TwitterMessage message = tj.convert(json);
//            if(null == message){
//                System.err.println(json);
//                System.out.println("File: " + jsonFileName);
//
//            }
//        }

    }


}
