import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        File saveDirectory = new File("images");
        if (!saveDirectory.exists()) {
            if (!saveDirectory.mkdir()) {
                throw new RuntimeException("Unable to create a folder to save pictures");
            }
        }

        String key = "uV3FhQyyqPJelg0eefxKLFxDoA8AVhwB3NsiwD0s";
        String uri = String.format("https://api.nasa.gov/planetary/apod?api_key=%s", key);
        CloseableHttpClient httpClient = HttpClient.get();
        HttpGet request = HttpRequest.get(uri);
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            Parser parser = new Parser();
            APOD apod = parser.parseAPOD(response.getEntity().getContent());
            if (apod.getMedia_type().equals("image")) {
                String[] url = apod.getUrl().split("/");
                String fileName = url[url.length - 1];
                request = HttpRequest.get(apod.getUrl());
                response = httpClient.execute(request);
                try(FileOutputStream writer = new FileOutputStream(saveDirectory.getPath() + "/" + fileName)) {
                    writer.write(response.getEntity().getContent().readAllBytes());
                }

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
