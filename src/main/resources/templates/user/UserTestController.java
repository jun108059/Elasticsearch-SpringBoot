package templates.user;

//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserTestController {
	
    @GetMapping("/test")
    public String getUser(Model model) {
        User user = new User("kkaok", "Å×½ºÆ®", "web") ;
        model.addAttribute("user", user);
        return "test";
    }
}
