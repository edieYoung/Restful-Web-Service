import com.google.gson.Gson;


import static spark.Spark.*;

public class SparkRestExample {
    public static void main(String[] args){
        UserServiceImple userService = new UserServiceImple();
        //add a user
        post("/user", (request, reponse) ->{
            reponse.type("application/json");
            User user = new Gson().fromJson(request.body(), User.class);
            userService.addUser(user);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCESS));
        });
        //get all user
        get("/user", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCESS, new Gson().toJsonTree(userService.getUsers())));
        });

        get("/user/:id", (request, response) ->{
            response.type("application/json");
            User user = userService.getUser(request.params(":id"));
            if(user==null){
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, new Gson().toJson("No user found.")));
            }else{
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCESS, new Gson().toJsonTree(user)));
            }
        });
        put("/users/:id", (request, response)->{
            response.type("application/json");
            User toEdit = new Gson().fromJson(request.body(), User.class);
            User editedUser  = userService.editUser(toEdit);
            if(editedUser != null){
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCESS, new Gson().toJsonTree(editedUser)));
            }else{
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, new Gson().toJson("User not found or error in edit")));
            }

        });



    }
}
