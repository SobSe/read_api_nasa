import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Parser {
    private ObjectMapper mapper = new ObjectMapper();
    public APOD parseAPOD(InputStream inputStream) {
        APOD apod = null;
        try {
            apod = mapper.readValue(inputStream, new TypeReference<APOD>() {
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return apod;
    }
}
