package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {

    @Autowired
    private CodeService codeService;

    @GetMapping(path = "/code/{id}")
    public Code getCodeById(@PathVariable final UUID id) {
        return codeService.getCodeById(id);
    }

    @GetMapping(path = "/code/latest")
    public List<Code> getLatestCode() {
        return codeService.getLatestCodes();
    }

    @PostMapping(path = "/code/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, UUID> addCode(@RequestBody final Code Code) {
        final UUID id = codeService.addCode(Code);
        return Map.of("id", id);
    }
}
