import java.io.*;
import java.nio.Buffer;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class UserServiceImple implements UserService {
    @Override
    public void addUser(User user) {
        JSONObject obj = new JSONObject();
        obj.put("id", user.getId());
        obj.put("email", user.getEmail());
        obj.put("firstName", user.getFirstName());
        obj.put("lastName", user.getLastName());


        try(FileWriter file = new FileWriter("./users.json")){
            file.write(obj.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("user " + user.getFirstName()+" has been added!");
    }

    @Override
    public Collection<User> getUsers() {
        List<User> allUsers = new ArrayList<>();
        try {
            File file = new File("./users.json");
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line;
            while ((line = in.readLine()) != null) {
                User user = new Gson().fromJson(line, User.class);
                allUsers.add(user);
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return allUsers;
    }

    @Override
    public User getUser(String id) {
        try{
            File file = new File("./users.json");
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line;
            while((line = in.readLine())!=null){
                User user = new Gson().fromJson(line, User.class);
                if(user.getId().equals(id)){
                    return user;
                }
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User editUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(String id) {

    }

    @Override
    public boolean userExist(String id) {
        return false;
    }
}
