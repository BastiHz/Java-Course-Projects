package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller  // needs to be @Controller not @RestController or else templates won't load
@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
public class WebController {

    @Autowired
    private CodeService codeService;

    @GetMapping(path = "/code/{id}")
    public String getCodeById(@PathVariable final String id, final Model model) {
        model.addAttribute("code", codeService.getCodeById(id));
        return "code";
    }

    @GetMapping(path = "/code/latest")
    public String getLatestCodes(final Model model) {
        model.addAttribute("codes", codeService.getLatestCodes());
        return "latest";
    }

    @GetMapping(path = "/code/new")
    public String getCodeNew() {
        return "create";
    }
}
