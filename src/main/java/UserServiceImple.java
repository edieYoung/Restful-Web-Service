import java.io.*;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import org.json.simple.JSONObject;


public class UserServiceImple implements UserService {
    @Override
    public void addUser(User user) {
        JSONObject obj = new JSONObject();
        obj.put("id", user.getId());
        obj.put("email", user.getEmail());
        obj.put("firstName", user.getFirstName());
        obj.put("lastName", user.getLastName());

        try(FileWriter file = new FileWriter("./users.json", true)){
            file.write(obj.toJSONString()+System.getProperty("line.separator"));
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
            in.close();
            return allUsers;
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
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
                    in.close();
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
        try{
            File file = new File("./users.json");
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line;
            while((line = in.readLine())!=null){
                User userToEdit = new Gson().fromJson(line, User.class);
                if(userToEdit.getId().equals(user.getId())){
                    userToEdit.setEmail(user.getEmail()==null? userToEdit.getEmail():user.getEmail());
                    userToEdit.setFirstName(user.getFirstName()==null? userToEdit.getFirstName(): user.getFirstName());
                    userToEdit.setLastName(user.getLastName()==null? userToEdit.getLastName():user.getLastName());
                    deleteUser(user.getId());
                    FileWriter writer = new FileWriter("./users.json", true);
                    writer.write(new Gson().toJson(userToEdit)+System.getProperty("line.separator"));
                    writer.flush();
                    writer.close();
                    in.close();
                    return userToEdit;
                }
            }
            return null;
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteUser(String id) {
        try{
            File file = new File("./users.json");
            File newFile = new File("./temp.json");
            BufferedReader out = new BufferedReader(new FileReader(file));
            FileWriter in = new FileWriter(newFile, true);
            String line;
            while((line = out.readLine())!=null){
                User user = new Gson().fromJson(line, User.class);
                if(!user.getId().equals(id)) {
                    in.write(line+System.getProperty("line.separator"));
                }
            }
            in.flush();
            in.close();
            out.close();
            newFile.renameTo(file);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean userExist(String id) {
        try{
            File file = new File("./users.json");
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line;
            while((line = in.readLine())!=null){
                User user = new Gson().fromJson(line, User.class);
                if(user.getId().equals(id)){
                    return true;
                }
            }
            return false;
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
}
