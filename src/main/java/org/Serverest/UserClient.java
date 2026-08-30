package org.Serverest;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class UserClient {
    private final APIRequestContext request;
    private final String endpoint = "/usuarios";

    public UserClient(APIRequestContext request){
        this.request = request;
    }

    @Step("Consulta usuário por email")
    public APIResponse getUser(String email){
        try{
            RequestOptions parameters = RequestOptions.create().setQueryParam("email",email);
            APIResponse resp          = request.get(endpoint,parameters);

            Allure.addAttachment("Request Body", "application/json", "GET "+endpoint,".json");
            Allure.addAttachment("Response Body", "application/json", resp.text(),".json");
            return resp;
        }catch (Exception e){
            throw new RuntimeException("Erro ao consultar usuário!\n",e);
        }
    }

    @Step("Deleta usuário por userID")
    public APIResponse deleteUser(String userID){
        try{
            APIResponse resp = request.delete(endpoint+"/"+userID);

            Allure.addAttachment("Request Body", "application/json", "DELETE "+endpoint+"/"+userID,".json");
            Allure.addAttachment("Response Body", "application/json", resp.text(),".json");

            return resp;
        }catch (Exception e){
            throw new RuntimeException("Erro ao deletar usuário!\n",e);
        }
    }

    @Step("Cria novo usuário")
    public APIResponse createUser(String name, String email, String pass, Boolean isAdmin){
        try{
            Map<String, String> json = new HashMap<>();
            json.put("nome", name);
            json.put("email", email);
            json.put("password", pass);
            json.put("administrador", isAdmin.toString());

            APIResponse resp = request.post(endpoint, RequestOptions.create().setData(json));

            String jsonRequest = new ObjectMapper().writeValueAsString(json);
            Allure.addAttachment("Request Body", "application/json", jsonRequest, ".json");
            Allure.addAttachment("Response Body", "application/json", resp.text(), ".json");
            return resp;
        }catch (Exception e){
            throw new RuntimeException("Erro ao tentar criar um usuario! -createUser-\n",e);
        }
    }
}
