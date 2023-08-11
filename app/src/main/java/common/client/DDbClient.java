package common.client;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DDbClient {

     private static DynamoDbClient dynamoDbClient;

     private DDbClient() {

     }

     public static synchronized DynamoDbClient getInstance() {
          if(dynamoDbClient == null) {
               return DynamoDbClient.builder()
               .region(Region.US_EAST_1).build();
          }
          return dynamoDbClient;
     }
}
