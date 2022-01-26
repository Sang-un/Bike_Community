package bike.community.model.user;


import lombok.Data;

@Data
public class LoginUser {
    private String email;
    private String password;

    public void makeNewUser(String email, String password){
    }
}
