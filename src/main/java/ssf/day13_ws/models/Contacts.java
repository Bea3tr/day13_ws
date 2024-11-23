package ssf.day13_ws.models;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.*;
import static ssf.day13_ws.Day13WsApplication.*;

public class Contacts {

    @NotNull(message="Name cannot be null")
    @NotEmpty(message="Name cannot be empty")
    @Size(min=3, max=64, message="Name must be between 3 and 64 characters")
    private String name;

    @NotNull(message="Email cannot be null")
    @NotEmpty(message="Email cannot be empty")
    @Email(message="Please enter valid email")
    private String email;

    @NotNull(message="Phone number cannot be null")
    @Pattern(regexp = "\\d+", message="Phone number should only contain digits")
    @Size(min=7, message="Phone number must be at least 7 digits")
    private String phoneNum;

    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPhoneNum() {return phoneNum;}
    public void setPhoneNum(String phoneNum) {this.phoneNum = phoneNum;}

    public Date getDob() {return dob;}
    public void setDob(Date dob) {this.dob = dob;}
    
    @Override
    public String toString() {
        return "Contacts [name=" + name + ", email=" + email + ", phoneNum=" + phoneNum + ", dob=" + dob + "]";
    }

    public static String genRandHex() {
        String randHex = UUID.randomUUID().toString().substring(0, 8);
        return randHex;
    }

    public static String createFile(String fileName) {
        String filePath = dataDir + "/" + fileName;
        File newFile = new File(filePath);
        try {
            newFile.createNewFile();
            System.out.printf("New file: %s created\n", newFile.getName());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.printf("File has been created at: " + new File(filePath).getAbsolutePath());
        return new File(filePath).getAbsolutePath();
    }

    public static void writeData(Contacts contact, String file) {
        // Write data to file
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Writer write = new FileWriter(new File(file));
            BufferedWriter bw = new BufferedWriter(write);
            bw.write(contact.getName() + "\n");
            bw.write(contact.getEmail() + "\n");
            bw.write(contact.getPhoneNum() + "\n");
            bw.write(df.format(contact.getDob()) + "\n");

            bw.flush();
            bw.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Map<String, String> readFile(File file) {
        Map<String, String> contactDetails = new HashMap<>();
        String[] details = {"Name", "Email", "Phone No.", "Date of Birth"};
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String data = "";
            int i = 0;
            while(data != null) {
                data = br.readLine();
                if(data == null) 
                    break;
                contactDetails.put(details[i], data);
                i++;
            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return contactDetails;
    }

    public static Map<String, String> getLinksNNames() {
        Map<String, String> link_name = new HashMap<>();
        File dir = new File(dataDir);
        File[] files = dir.listFiles();
        String name = "";
        for(File file : files) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                name = br.readLine();
                br.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            String id = file.getName().split("\\.")[0];
            link_name.put(name, "/contact/" + id);
        }

        return link_name;
    }
    

}
