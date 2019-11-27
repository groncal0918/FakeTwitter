1. Begin by going to https://start.spring.io/ and generating a new Spring Boot project with the Web, Thymeleaf, JPA, and H2 dependencies
*** Also create a new, empty Git project on GitHub

2. Create a new Eclipse project, copying the contents of the downloaded jar into the project folder
*** Fix the build path if neccesary!
*** Configure as a Maven project (Configure > Convert to Maven Project) if neccesary!

3. Add a 'Login' page named login.html
*** The page should be located in src/main/resources/templates
*** For the moment, make it a simple 'Hello World' page; don't worry, we'll make it look better shortly!

4. Add LoginController.java
*** This class will be annotated as a @Controller
*** This class will have a method, annotated as a @GetMapping, that handles GET requests to the root and to /login
*** This class will return a String reference to the login page

5. Verify your login page loads, by running the Application and navigating to http://localhost:8080
*** If you're successful, commit and push to git!

6. Build the backend support needed to handle logging in to fake twitter
*** Add a new class, that represents login requests, named LoginRequest
*** This class should be a simple POJO with username and password
*** Add a new method to LoginController, annotated as a @PostMapping, that handles POST requests to /login, with a parameter of LoginRequest annotated with @ModelAttribute and a parameter of Model
*** It should put the username from the LoginRequest into the Model as an attribute, and return a page named 'main'

7. Build and style the login page
*** It should have a form to submit the user's username and password
*** Set the ACTION of the form to /login
*** Set the METHOD of the form to POST
*** Use bootstrap styling to make it look decent! 
*** *** https://getbootstrap.com/docs/4.3/getting-started/introduction/
*** *** https://getbootstrap.com/docs/4.3/components/forms/

8. Add a 'Main' page named main.html
*** The page should be located in src/main/resources/templates
*** Add a thymeleaf templated message, welcoming the user by username to the page.
*** *** <span th:text="'Hello, ' + ${userName}"></span>

9. Verify you can login by loading the page and submitting a username/password, then seeing that you see the main page with your username
*** If you're successful, commit and push to git!

10. Bit ugly that the '/main' path isn't what we see after logging in. Let's fix that with the power of REDIRECT!

11. Add a new class, MainController, which should be annotated with @Controller

12. Add a new method to MainController, GetMain, which is annotated with @GetController and accepts a Model and an HttpSession as parameters

13. Get an attribute named 'userName' from the HttpSession, and put that attribute into the Model.

14. Return "main"

15. Back in LoginController#postLogin...
*** Replace Model with HttpSession
*** Replace adding the userName to the Model with setting the userName on the HttpSession
*** Change the return type to ModelAndView
*** Change the returned value to new ModelAndView("redirect:/main")

16. Verify you can login by loading the page and submitting a username/password, then seeing that you are redirected to /main and still see your userName displayed
*** If you're successful, commit and push to git!

### The basic login is now complete!
### Stretch goals: 
### * Change MainCotnroller#getMain to redirect the user to the login page if they have not logged in
### * Change LoginController#postLogin to only redirect to main for a specific userName, otherwise go back to the login page and display an error
### * Committ early and often!

### NEXT STEP: PERSISTENCE!

1. Set up the application.properties to support using and viewing the h2 database and console
*** application.properties should be in src/main/resource/application.properties

2. Verify your setup, by running the Application and connecting to http://localhost:8080/console, then logging in to the h2 database
*** Don't forget, you'll need to update the JDBC url!