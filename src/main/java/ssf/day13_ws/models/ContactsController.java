package ssf.day13_ws.models;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import ssf.day13_ws.Day13WsApplication;

import static ssf.day13_ws.models.Contacts.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping
public class ContactsController {

    @PostMapping("/contact")
    public String postContact(Model model,
        @Valid @ModelAttribute Contacts contacts,
        BindingResult bindings) {

        if(bindings.hasErrors())
            return "index";

        DateFormat df = new SimpleDateFormat("dd MM yyyy");
        String dob = df.format(contacts.getDob());
        try {
            Date dob1 = df.parse(dob);
            Date currTime = df.parse(df.format(new Date()));
            long diff = currTime.getTime() - dob1.getTime();
            long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            System.out.println("Days: " + days);
            if(days < 3650L || days > 36500L) {
                FieldError err = new FieldError("contacts", "dob", "User must be between 10 to 100 years old");
                bindings.addError(err);
                return "index";
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        String randHex = genRandHex();
        String filePath = createFile(randHex + ".txt");
        writeData(contacts, filePath);

        model.addAttribute("name", contacts.getName());
        model.addAttribute("id", randHex);

        return "created";
    }

    @GetMapping("/contact/{id}")
    public String getId(
        @PathVariable String id, 
        Model model) {

            String filePath = String.join("/", Day13WsApplication.dataDir, (id + ".txt"));
            File file = new File(filePath);
            if(!file.exists()) {
                return "error";
            } 
            Map<String, String> contactDetails = readFile(file);

            model.addAttribute("id", id);
            model.addAttribute("contactDetails", contactDetails);
            return "id";
    }

    @GetMapping("/contacts")
    public String getContacts(Model model) {
        Map<String, String> link_name = getLinksNNames();
        
        model.addAttribute("userList", link_name);
        return "contacts";
    }

    
}
