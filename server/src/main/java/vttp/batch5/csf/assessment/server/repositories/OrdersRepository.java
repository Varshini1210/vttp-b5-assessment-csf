package vttp.batch5.csf.assessment.server.repositories;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;


@Repository
public class OrdersRepository {

  @Autowired
  private MongoTemplate template;

  // TODO: Task 2.2
  // You may change the method's signature
  // Write the native MongoDB query in the comment below
  //
  //  Native MongoDB query here
  //db.menus.find().sort({name:1})
  public Optional<List<JsonObject>> getMenu() {
    List<JsonObject> menuItems = new ArrayList<>();
    // Query query = new Query();
    // List<Document> results = template.find(query, Document.class, "menus");
    try{
       Query query = new Query()
        .with(Sort.by(Sort.Direction.ASC,"name"));
    List<Document> results = template.find(query, Document.class, "menus");
      // List<Document> results = template.findAll(Document.class, "menus");

      
  
      for (Document doc: results){
        JsonObject menuObj = Json.createObjectBuilder()
                                  .add("id",doc.getString("_id"))
                                  .add("name",doc.getString("name"))
                                  .add("description", doc.getString("description"))
                                  .add("price", doc.getDouble("price"))
                                  .add("quantity",0)
                                  .build();
        menuItems.add(menuObj);
        
    }
   

    }
    catch(Exception e){
      
      JsonObject menuObj1 = Json.createObjectBuilder()
                                  .add("id","9ab618a")
                                  .add("name","Tofu Bao")
                                  .add("description", "Crispy, fried tofu bites mixed in a spicy, topped Sausage")
                                  .add("price", 3.80)
                                  .build();

      JsonObject menuObj2 = Json.createObjectBuilder()
                                  .add("id","635ecb")
                                  .add("name","Tacos De alambre")
                                  .add("description", "Chicken cooked with bacon, topped with Oaxaca cheese")
                                  .add("price", 4.60)
                                  .build();
        menuItems.add(menuObj1);
        menuItems.add(menuObj2);
        return Optional.of(menuItems);
      
    }

    return Optional.of(menuItems);
  }

  // TODO: Task 4
  // Write the native MongoDB query for your access methods in the comment below
  //
  //  Native MongoDB query here
  
}
