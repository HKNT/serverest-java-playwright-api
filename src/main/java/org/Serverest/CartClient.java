package org.Serverest;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

public class CartClient {
    private final APIRequestContext request;
    private final String endpoint = "/carrinhos";

    public CartClient(APIRequestContext request){
        this.request = request;
    }

    @Step("Consulta Carrinho")
    public APIResponse getCart(String cartID){
        try{
            APIResponse resp = request.get(endpoint+"/"+cartID);

            Allure.addAttachment("Request Body", "application/json", "GET "+endpoint+"/"+cartID, ".json");
            Allure.addAttachment("Response Body", "application/json", resp.text(), ".json");
            return resp;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar carrinho!\n",e);
        }
    }

    @Step("Criar Carrinho")
    public APIResponse registerCart(Map<String, Object> bodyRequest, String token){
        try{
            RequestOptions options = RequestOptions.create()
                    .setData(bodyRequest)
                    .setHeader("Authorization",token);
            APIResponse resp = request.post(endpoint,options);

            String jsonRequest = new ObjectMapper().writeValueAsString(bodyRequest);
            Allure.addAttachment("Request Body", "application/json", jsonRequest, ".json");
            Allure.addAttachment("Response Body", "application/json", resp.text(), ".json");

            return resp;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar um carrinho \n",e);
        }
    }

    @Step("Deleta Carrinho")
    public APIResponse deleteCart(String cartID, String token){
        try{
            RequestOptions options = RequestOptions.create().setHeader("Authorization", token).setQueryParam("_id", cartID);

            APIResponse resp = request.delete(endpoint+"/cancelar-compra", options);
            Allure.addAttachment("Request Body", "application/json", "GET "+endpoint+"/cancelar-compra", ".json");
            Allure.addAttachment("Response Body" , "application/json", resp.text(), ".json");
            return resp;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar carrinho!\n",e);
        }
    }

    @Step("Fecha Carrinho - Conclui Compra")
    public APIResponse closeCart(String cartID, String token){
        try{
            RequestOptions options = RequestOptions.create().setHeader("Authorization", token).setQueryParam("_id", cartID);

            APIResponse resp = request.delete(endpoint+"/concluir-compra", options);

            Allure.addAttachment("Request Body", "application/json", "DELETE "+endpoint+"/concluir-compra", ".json");
            Allure.addAttachment("Response Body", "application/json", resp.text(), ".json");
            return resp;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar carrinho!\n"+e);
        }
    }
}