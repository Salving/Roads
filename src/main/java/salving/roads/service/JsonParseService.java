package salving.roads.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import salving.roads.domain.ProblemPoint;

@Service
public class JsonParseService {

    private ObjectMapper mapper;

    public JsonParseService() {
        mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter();
    }

    public String serialize(Object value) throws JsonProcessingException {
        return mapper.writeValueAsString(value);
    }
}
