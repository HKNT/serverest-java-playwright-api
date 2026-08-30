package org.Serverest;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class ProductClient {
    private final APIRequestContext request;
    private final String endpoint = "/produtos";

    public ProductClient(APIRequestContext request){
        this.request = request;
    }

    @Step("Busca produto por ID")
    public APIResponse searchProduct(String idProduct){
        try{
            RequestOptions param = RequestOptions.create().setQueryParam("_id", idProduct);
            APIResponse resp     = request.get(endpoint,param);

            Allure.addAttachment("Request Body", "application/json", "GET "+endpoint, ".json");
            return resp;
        }catch (Exception e){
            throw new RuntimeException("Erro na busca por produto! -searchProduct-\n"+e);
        }
    }

    @Step("Deleta produto por ID")
    public APIResponse deleteProduct(String idProduct){
        try{
            RequestOptions param = RequestOptions.create().setQueryParam("_id", idProduct);
            APIResponse resp     = request.delete(endpoint,param);

            Allure.addAttachment("Request Body", "application/json", "DELETE "+endpoint, ".json");
            Allure.addAttachment("Response Body", "application/json", resp.text(), ".json");
            return resp;
        }catch (Exception e){
            throw new RuntimeException("Erro na busca por produto! -searchProduct-\n"+e);
        }
    }

    @Step("Cria produto por ID")
    public APIResponse createProduct(String name, int price, String description, int qtd, String token){
        try{
            Map<String, Object> json = new HashMap<>();
            json.put("nome",name);
            json.put("preco",price);
            json.put("descricao",description);
            json.put("quantidade", qtd);

            RequestOptions options = RequestOptions.create().setHeader("Authorization", token).setData(json);
            APIResponse resp       = request.post(endpoint,options);

            String jsonRequest = new ObjectMapper().writeValueAsString(json);
            Allure.addAttachment("Request Body", "application/json", jsonRequest, ".json");
            Allure.addAttachment("Response Body", "application/json", resp.text(), ".json");

            return resp;
        }catch (Exception e){
            throw new RuntimeException("Erro ao tentar criar produto!\n",e);
        }
    }
}
