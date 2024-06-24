package Api;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ApiConnector {

    private static String baseUrl = "http://10.3.0.71:8080/mhubx-cc";
    private static Header authHeader = new BasicHeader("Authorization", "Basic dGVzdGVyOnRyYWluaW5n");

    public static HttpResponse<String> getAllAlarms() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(baseUrl+"/module/juwi/action?page=Logic.Interface&name=getAlarms&source=system&system_id=cps1"))
                .header(authHeader.getName(), authHeader.getValue())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response;
        }
        return null;
    }
}
