package vttp.batch5.csf.assessment.server.controllers;

import java.io.StringReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.batch5.csf.assessment.server.services.RestaurantService;

@Controller
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

  @Autowired
  private RestaurantService restaurantSvc;

  // TODO: Task 2.2
  // You may change the method's signature
  @GetMapping(path="/menu")
  @ResponseBody
  public ResponseEntity<String> getMenus() {

    Optional<List<JsonObject>> opt = restaurantSvc.getMenu();
    List<JsonObject> menuItems = new ArrayList<>();
    if(opt.isEmpty()){
      
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
    }
    else{
      menuItems= opt.get();
    }
    JsonArrayBuilder menu = Json.createArrayBuilder();
    for (JsonObject item: menuItems){
      menu.add(item);
    }
    String menuResult = menu.build().toString();
    System.out.println(menuResult);


    return ResponseEntity.ok(menuResult);
  }

  // TODO: Task 4
  // Do not change the method's signature
  @PostMapping(path="/food_order", consumes=MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> postFoodOrder(@RequestBody String payload) {

    System.out.println(payload);

    JsonReader jReader = Json.createReader(new StringReader(payload));
    JsonObject orderObject = jReader.readObject();
    String username = orderObject.getString("username");
    String password = orderObject.getString("password");

    System.out.println(username);
   

    // MessageDigest digest = null;
    // try {
    //   digest = MessageDigest.getInstance("SHA-256");
    // } catch (NoSuchAlgorithmException e) {
      
    //   e.printStackTrace();
    // }
        
    // byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
    // // String encoded = Base64.getEncoder().encodeToString(hash);
    // String hashString = String.format("%064x", new BigInteger(1, hash));
    // System.out.println(hashString);

    Boolean validateUser = restaurantSvc.ValidateUser(username, password);
    if (validateUser == false){
      JsonObject errorResponse = Json.createObjectBuilder()
                                    .add("mesage","Invalid username/password")
                                    .build();
      System.out.println("error");
      return ResponseEntity.status(401).body(errorResponse.toString());
    }

    String orderId = restaurantSvc.generateRandomString();

    JsonObject orderPayload = Json.createObjectBuilder()
                                  .add("order_id",orderId)
                                  .add("payer",username)
                                  .add("payee", "Parameswaran Varshini")
                                  .add("payment", 23.10)
                                  .build();
    String paymentResponse = restaurantSvc.makePayment(orderPayload);

    JsonReader responseReader = Json.createReader(new StringReader(paymentResponse));
    JsonObject responseObject = jReader.readObject();

    String paymentId= responseObject.getString("payment_id");

    // Boolean insertOrder = 

    return ResponseEntity.ok("{}");
  }
}
