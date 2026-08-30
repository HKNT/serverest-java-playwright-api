package tests.Serverest.base;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import java.util.HashMap;
import java.util.Map;

import io.github.cdimascio.dotenv.Dotenv;

public class BaseAPITest {
    protected static APIRequestContext request;
    protected static Playwright play;

    @BeforeAll
    public static void setup(){
        Dotenv dotenv      = Dotenv.configure().ignoreIfMissing().load();
        String urlAmbiente = dotenv.get("BASE_URL", "https://serverest.dev");

        play = Playwright.create();
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type", "Application/json");
        header.put("Accept", "Application/json");
        request = play.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL(urlAmbiente)
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
