package vttp.batch5.csf.assessment.server.services;


import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.bson.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.JsonObject;
import vttp.batch5.csf.assessment.server.repositories.OrdersRepository;
import vttp.batch5.csf.assessment.server.repositories.RestaurantRepository;

@Service
public class RestaurantService {

  @Autowired
  private RestaurantRepository restaurantRepo;

  @Autowired
  private OrdersRepository ordersRepo;

  // TODO: Task 2.2
  // You may change the method's signature
  public Optional<List<JsonObject>> getMenu() {

    return ordersRepo.getMenu();
  }
  
  // TODO: Task 4

  public Boolean ValidateUser(String username, String password){
    return restaurantRepo.validateUser(username, password);
  }

  public String generateRandomString() {
  String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
  StringBuilder randString = new StringBuilder();
  Random rnd = new Random();
  while (randString.length() < 8) { // length of the random string.
      int index = (int) (rnd.nextFloat() * chars.length());
      randString.append(chars.charAt(index));
  }
  String orderId = randString.toString();
  return orderId;

}

public String makePayment(JsonObject paymentObject){
  RestTemplate template = new RestTemplate();

  String paymentUrl="https://payment-service-production-a75a.up.railway.app/";
  HttpHeaders headers = new HttpHeaders();
  headers.set("X-Authenticate", paymentObject.getString("payer"));
  // HttpEntity entity = new HttpEntity(headers);

  RequestEntity<String> req = RequestEntity.post(paymentUrl)
                                          .header("X-Authenticate",paymentObject.getString("payer"))
                                          .body(paymentObject.toString());
                                          
                                          
  ResponseEntity<String> resp = template.exchange(req, String.class);
  String response = resp.getBody();
  return response;
}


}
