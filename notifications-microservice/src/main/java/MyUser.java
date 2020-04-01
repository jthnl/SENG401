import java.util.UUID;

public class MyUser {
    private String user_id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public MyUser(String username, String password, String firstName, String lastName){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        setUser_id(uuid);
        setUsername(username);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
    }

    public MyUser(String username, String password){
        //not written yet
    }

//    public MyUser createUser(String username, String password, String firstName, String lastName){
//        String uuid = UUID.randomUUID().toString().replace("-", "");
//        MyUser newUser = new MyUser(uuid, username, password, firstName, lastName);
//        return newUser;
//    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
