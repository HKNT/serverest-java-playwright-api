package tests.Serverest.tests;

import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.Serverest.CartClient;
import org.Serverest.LoginClient;
import org.Serverest.ProductClient;
import org.Serverest.UserClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.Serverest.base.BaseAPITest;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import utils.DataGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartTest extends BaseAPITest {
    ObjectMapper objMap = new ObjectMapper();
    String userToken    = "";
    String productID    = "";
    String cartID       = "";
    String userID       = "";

    @Test
    @Tag("smoke")
    @Tag("regressao")
    @Epic("Gestão de Carrinhos")
    @Feature("Criar Carrinho e Finaliza-lo com Sucesso")
    public void testCriarEncerrarCarrinho(){
//       Criando usuario
        UserClient user                  = new UserClient(request);
        Map<String, Object> dadosUser    = DataGenerator.generatedUser(true);
        Map<String, Object> dadosProduct = DataGenerator.generatedProduct();

//        Fazendo cadastro
        APIResponse respUser = user.createUser(
                dadosUser.get("nome").toString(),
                dadosUser.get("email").toString(),
                dadosUser.get("password").toString(),
                Boolean.valueOf(dadosUser.get("administrador").toString()));
        JsonNode jsonResponseUser = objMap.readTree(respUser.text());

//        Validações
        PlaywrightAssertions.assertThat(respUser).isOK();
        Assertions.assertTrue(jsonResponseUser.get("message").asString().contains("Cadastro realizado com sucesso"));
        Assertions.assertTrue(jsonResponseUser.get("_id").isString());

        userID = jsonResponseUser.get("_id").asString();

//       Fazendo login
        LoginClient login     = new LoginClient(request);
        APIResponse respLogin = login.fazerLogin(dadosUser.get("email").toString(),
                                         dadosUser.get("password").toString());

        JsonNode jsonResponseLogin = objMap.readTree(respLogin.text());
        userToken                  = jsonResponseLogin.get("authorization").asString();

//        Validações
        PlaywrightAssertions.assertThat(respLogin).isOK();
        Assertions.assertTrue(jsonResponseLogin.get("message").asString().contains("Login realizado com sucesso"));
        Assertions.assertFalse(jsonResponseLogin.get("authorization").asString().isBlank(), "Verificar se o campo não esta vazio");
        Assertions.assertNotNull(userToken,"Verificar se o campo não esta nulo");

//        criando produto novo
        ProductClient product   = new ProductClient(request);
        APIResponse respProduct = product.createProduct(
                dadosProduct.get("nome").toString(),
                Integer.parseInt(dadosProduct.get("preco").toString()),
                dadosProduct.get("descricao").toString(),
                Integer.parseInt(dadosProduct.get("quantidade").toString()),
                userToken);

        JsonNode jsonResponseProduct = objMap.readTree(respProduct.text());
        productID                   = jsonResponseProduct.get("_id").asString();

        Map<String, Object> bodyRequest        = new HashMap<>();
        List<Map<String, Object>> listProducts = new ArrayList<>();

        Map<String, Object> prod1 = new HashMap<>();
        prod1.put("idProduto", productID);
        prod1.put("quantidade", dadosProduct.get("quantidade"));
        listProducts.add(prod1);

        bodyRequest.put("produtos", listProducts);

//        validando se produto foi criado com sucesso.
        PlaywrightAssertions.assertThat(respProduct).isOK();
        Assertions.assertFalse(jsonResponseProduct.get("_id").asString().isEmpty(), "Verificar se o Campo '_id' Existe");
        Assertions.assertTrue(jsonResponseProduct.get("message").asString().contains("com sucesso"), "Verificar se o cadastro foi bem sucedido");

//      Criando o carrinho
        CartClient cart      = new CartClient(request);
        APIResponse respCart = cart.registerCart(bodyRequest,userToken);

        JsonNode jsonResponseCart = objMap.readTree(respCart.text());
        cartID                    = jsonResponseCart.get("_id").asString();

//        Validando carrinho
        PlaywrightAssertions.assertThat(respCart).isOK();
        Assertions.assertFalse(jsonResponseCart.get("_id").asString().isEmpty(), "Verificar se o campo '_id' existe");
        Assertions.assertTrue(jsonResponseCart.get("message").asString().contains("com sucesso"), "Verificar se o cadastro foi bem sucedido");
        Assertions.assertNotNull(cartID, "Verificar se campo não é nulo");

//      Fechando o carrinho
        APIResponse respCartClose      = cart.closeCart(cartID,userToken);
        JsonNode jsonResponseCloseCart = objMap.readTree(respCartClose.text());

//      Validando o fechamento do carrinho
        PlaywrightAssertions.assertThat(respCartClose).isOK();
        Assertions.assertTrue(jsonResponseCloseCart.get("message").asString().contains("Registro excluído com sucesso"));
    }

    @Test
    @Tag("regressao")
    @Epic("Gestão de Carrinhos")
    @Feature("Criar e Deletar Carrinho com Sucesso")
    public void testCriarDeletarCarrinho(){
//       Criando usuario
        UserClient user                  = new UserClient(request);
        Map<String, Object> dadosUser    = DataGenerator.generatedUser(true);
        Map<String, Object> dadosProduct = DataGenerator.generatedProduct();

//        Fazendo cadastro
        APIResponse respUser = user.createUser(
                dadosUser.get("nome").toString(),
                dadosUser.get("email").toString(),
                dadosUser.get("password").toString(),
                Boolean.valueOf(dadosUser.get("administrador").toString()));
        JsonNode jsonResponseUser = objMap.readTree(respUser.text());

//        Validações
        PlaywrightAssertions.assertThat(respUser).isOK();
        Assertions.assertTrue(jsonResponseUser.get("message").asString().contains("Cadastro realizado com sucesso"));
        Assertions.assertTrue(jsonResponseUser.get("_id").isString());

        userID = jsonResponseUser.get("_id").asString();

//       Fazendo login
        LoginClient login     = new LoginClient(request);
        APIResponse respLogin = login.fazerLogin(dadosUser.get("email").toString(),
                dadosUser.get("password").toString());

        JsonNode jsonResponseLogin = objMap.readTree(respLogin.text());
        userToken                  = jsonResponseLogin.get("authorization").asString();

//        Validações
        PlaywrightAssertions.assertThat(respLogin).isOK();
        Assertions.assertTrue(jsonResponseLogin.get("message").asString().contains("Login realizado com sucesso"));
        Assertions.assertFalse(jsonResponseLogin.get("authorization").asString().isBlank(), "Verificar se o campo não esta vazio");
        Assertions.assertNotNull(userToken,"Verificar se o campo não esta nulo");

//        criando produto novo
        ProductClient product   = new ProductClient(request);
        APIResponse respProduct = product.createProduct(
                dadosProduct.get("nome").toString(),
                Integer.parseInt(dadosProduct.get("preco").toString()),
                dadosProduct.get("descricao").toString(),
                Integer.parseInt(dadosProduct.get("quantidade").toString()),
                userToken);

        JsonNode jsonResponseProduct = objMap.readTree(respProduct.text());
        productID                   = jsonResponseProduct.get("_id").asString();

        Map<String, Object> bodyRequest        = new HashMap<>();
        List<Map<String, Object>> listProducts = new ArrayList<>();

        Map<String, Object> prod1 = new HashMap<>();
        prod1.put("idProduto", productID);
        prod1.put("quantidade", dadosProduct.get("quantidade"));
        listProducts.add(prod1);

        bodyRequest.put("produtos", listProducts);

//        validando se produto foi criado com sucesso.
        PlaywrightAssertions.assertThat(respProduct).isOK();
        Assertions.assertFalse(jsonResponseProduct.get("_id").asString().isEmpty(), "Verificar se o campo '_id' existe");
        Assertions.assertTrue(jsonResponseProduct.get("message").asString().contains("com sucesso"), "Verificar se o cadastro foi bem sucedido");

//      Criando o carrinho
        CartClient cart      = new CartClient(request);
        APIResponse respCart = cart.registerCart(bodyRequest,userToken);

        JsonNode jsonResponseCart = objMap.readTree(respCart.text());
        cartID                    = jsonResponseCart.get("_id").asString();

//        Validando carrinho
        PlaywrightAssertions.assertThat(respCart).isOK();
        Assertions.assertFalse(jsonResponseCart.get("_id").asString().isEmpty(), "Verificar se o campo '_id' existe");
        Assertions.assertTrue(jsonResponseCart.get("message").asString().contains("com sucesso"), "Verificar se o cadastro foi bem sucedido");
        Assertions.assertNotNull(cartID, "Verificar se campo não é nulo");

//      Fechando o carrinho
        APIResponse respCartDelete     = cart.deleteCart(cartID,userToken);
        JsonNode jsonResponseCloseCart = objMap.readTree(respCartDelete.text());

//      Validando o fechamento do carrinho
        PlaywrightAssertions.assertThat(respCartDelete).isOK();
        Assertions.assertTrue(jsonResponseCloseCart.get("message").asString().contains("Registro excluído com sucesso"));
    }

    @AfterEach
    public void tearDownDadosCriados(){
        if(userToken != null){
            UserClient user      = new UserClient(request);
            APIResponse respUser = user.deleteUser(userID);
            if(respUser.text().contains("idCarrinho")){
                CartClient cart      = new CartClient(request);
                APIResponse respCart = cart.deleteCart(cartID,userToken);
                if(respCart.text().contains("Registro excluído com sucesso")){
                    user.deleteUser(userID);
                }
            }
            System.out.println("*** "+respUser.text());
            Assertions.assertTrue(respUser.text().contains("Registro excluído com sucesso") ||
                                           respUser.text().contains("Nenhum registro excluído"));
        }

    }
}
