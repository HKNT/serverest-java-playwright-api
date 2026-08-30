package tests.Serverest.base;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;

public class BaseAPITest {
    protected static APIRequestContext request;
    protected static Playwright play;

    @BeforeAll
    public static void setup(){
        play = Playwright.create();
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type", "Application/json");
        header.put("Accept", "Application/json");
        request = play.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL("https://serverest.dev")
                        .setExtraHTTPHeaders(header)
        );
    }

    @AfterAll
    public static void tearDown(){
        if(request != null){
            request.dispose();
        }
        if(play != null){
            play.close();
        }
    }
}
