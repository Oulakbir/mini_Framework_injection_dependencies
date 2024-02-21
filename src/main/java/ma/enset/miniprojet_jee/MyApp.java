package ma.enset.miniprojet_jee;

public class MyApp {
    public static void main(String[] args) throws Exception {
        IoCContainer container = new IoCContainer();

        // Injection via constructeur
        UserService userService = container.getObject(UserService.class);
        userService.process();

        // Injection via méthode
        UserController userController = new UserController();
        container.doInjection(userController);
        userController.showUser();

        // Injection via champ
        UserRepository userRepository = new UserRepository();
        container.doInjection(userRepository);
        userRepository.display();
    }
}

class UserService {
    private UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void process() {
        System.out.println("Processing in UserService");
        userRepository.display();
    }
}

class UserRepository {
    public void display() {
        System.out.println("Displaying from UserRepository");
    }
}

class UserController {
    private UserService userService;

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void showUser() {
        System.out.println("Showing user from UserController");
        userService.process();
    }
}
