package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller  // needs to be @Controller not @RestController or else templates won't load
@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
public class WebController {

    @Autowired
    private CodeService codeService;

    @GetMapping(path = "/code/new")
    public String addCode() {
        return "create";
    }

    @GetMapping(path = "/code/{id}")
    public String getCodeById(@PathVariable final UUID id, final Model model) {
        model.addAttribute("code", codeService.getCodeById(id));
        return "code";
    }

    @GetMapping(path = "/code/latest")
    public String get10LatestCodes(final Model model) {
        model.addAttribute("codes", codeService.getLatestCodes());
        return "latest";
    }
}
