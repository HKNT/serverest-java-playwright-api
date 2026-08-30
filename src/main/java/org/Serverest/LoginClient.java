package org.Serverest;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class LoginClient {
    private final APIRequestContext request;
    private final String endpoint = "/login";

    public LoginClient(APIRequestContext request){
        this.request = request;
    }

    @Step("Efetua login")
    public APIResponse fazerLogin(String email, String password){
        try{
            Map<String, String> json = new HashMap<>();
            json.put("email", email);
            json.put("password", password);

            APIResponse resp = request.post(endpoint, RequestOptions.create().setData(json));

            String jsonRequest = new ObjectMapper().writeValueAsString(json);
            Allure.addAttachment("Request Body", "application/json", jsonRequest, ".json");
            Allure.addAttachment("Response Body", "application/json", resp.text(), ".json");
            return resp;

        }catch (Exception e){
            throw new RuntimeException("Erro ao tentar fazer login! -LoginClient-\n",e);
        }
    }
}
