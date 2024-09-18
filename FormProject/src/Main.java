import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(8080);
        
        // Serve the HTML form
        get("/", (req, res) -> {
            res.redirect("/web/form.html");
            return null;
        });
        
        // Handle form submission
        post("/submit", (req, res) -> {
            String name = req.queryParams("name");
            String email = req.queryParams("email");

            // Insert the data into the database
            DatabaseConnection db = new DatabaseConnection();
            db.insertUser(name, email);

            return "User added successfully!";
        });
    }
}
