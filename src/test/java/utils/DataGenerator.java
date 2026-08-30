package utils;

import net.datafaker.Faker;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {

    private static final Faker faker = new Faker();

    public static Map<String, Object> generatedUser(Boolean isAdmin){
        String myUuid            = UUID.randomUUID().toString().substring(0,8);
        Map<String, Object> user = new HashMap<>();

        user.put("nome", faker.name().fullName());
        user.put("email", "test_"+myUuid+"@teste.kr");
        user.put("password", "Teste1122!");
        user.put("administrador",isAdmin);

        return user;
    }

    public static Map<String, Object> generatedProduct(){
        String myUuid = UUID.randomUUID().toString().substring(0,5);
        Map<String, Object> product = new HashMap<>();

        product.put("nome", faker.commerce().productName()+ "-"+myUuid);
        product.put("preco", faker.number().numberBetween(1,900));
        product.put("descricao", faker.commerce().material());
        product.put("quantidade", ThreadLocalRandom.current().nextInt(1,100));

        return product;
    }


}