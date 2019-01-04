import com.google.gson.Gson;


import static spark.Spark.*;

public class SparkRestExample {
    public static void main(String[] args){
        UserServiceImple userService = new UserServiceImple();
        //add a user
        post("/user", (request, response) ->{

            response.type("application/json");
            User user = new Gson().fromJson(request.body(), User.class);
            userService.addUser(user);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCESS, new Gson().toJson(user)));
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
        put("/user/:id", (request, response)->{
            response.type("application/json");
            User toEdit = new Gson().fromJson(request.body(), User.class);
            toEdit.setId(request.params(":id"));
            User editedUser  = userService.editUser(toEdit);
            if(editedUser != null){
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCESS, new Gson().toJsonTree(editedUser)));
            }else{
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, new Gson().toJson("User not found or error in edit")));
            }

        });
        delete("/user/:id", (request, response)->{
            response.type("application/json");
            userService.deleteUser(request.params(":id"));
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCESS, "user has been deleted"));
        });
        options("/user/:id", (request, response)->{
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCESS, userService.userExist(request.params(":id"))?"User exists!":"User doesn't exist!"));
            }
        );
        



    }
}
