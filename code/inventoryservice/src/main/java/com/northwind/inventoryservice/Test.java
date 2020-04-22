/*
package com.northwind.inventoryservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.northwind.inventoryservice.domain.Products;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class Test {
   static String  msg = "";
    public static void main(String[] args) throws Exception {
         ObjectMapper mapper = new ObjectMapper();
     msg = "product created\n" +
                "{\"id\":1,\"productName\":\"testCheckUpdateinRabbit1\",\"quantiyPerUnit\":\"6\",\"listPrice\":363.34,\"version\":1,\"objectId\":\"0c05d4d6-d7ca-4a0b-9060-85ce5b88586c\",\"inStock\":false,\"categoryId\":0,\"productImages\":[],\"published\":false}";
    checkCreateOrUpdate(msg, mapper);

    }
    public static void checkCreateOrUpdate(String message, ObjectMapper mapper) throws Exception {
        ProductEvent json = null;
        if(message.contains("product created")){
            String check = message.substring(message.indexOf("\n")+2,message.length()-1);
            json = mapper.readValue(check, ProductEvent.class);

            System.out.println("in created method\n"+message);
        }else if(message.contains("product updated")){
            System.out.println("in update method");
        }

    }
}
*/
