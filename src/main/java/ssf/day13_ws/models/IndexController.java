package ssf.day13_ws.models;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping
public class IndexController {
    
    @GetMapping(path={"/", "index.html"})
    public String getIndex(Model model) {
        model.addAttribute("contacts", new Contacts());
        return "index";
    }
}
