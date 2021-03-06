package bacit.web.bacit_web;
import bacit.web.bacit_models.RegisterUserModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "registerUserServlet", value = "/register_user")
public class RegisterUserServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        writeCreateUserForm(out,null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        RegisterUserModel user = new RegisterUserModel();
        user.setFullName(request.getParameter("fullName"));
        user.setPhoneNumber(request.getParameter("phoneNumber"));
        PrintWriter out = response.getWriter();
        if(validateUser(user)){
            try{
                writeUserToDb(user, out);
            }
            catch (SQLException | ClassNotFoundException ex)
            {
                out.println(ex.getMessage());
            }
            writeHtmlStart(out, "Register user response");
            out.println("User with fullname: "+user.getFullName()+" and phone number: "
                    +user.getPhoneNumber()+" registered");
            out.println("<br/><a href='register_user'>Register user Servlet</a>");
            writeHtmlEnd(out);
        }
        else
        {
            writeCreateUserForm(out, "Validation failed");
        }
    }
    private void writeUserToDb(RegisterUserModel user,PrintWriter out) throws SQLException, ClassNotFoundException {
        Connection db = DBUtils.getINSTANCE().getConnection(out);
        String insertUserCommand = "insert into MytestDB.user (User_fullName, User_phoneNumber) values(?,?);";
        PreparedStatement statement = db.prepareStatement(insertUserCommand);
        statement.setString(1, user.getFullName());
        statement.setString(2, user.getPhoneNumber());

        statement.executeUpdate();
    }
    private void writeCreateUserForm(PrintWriter out, String errorMessage) {
        writeHtmlStart(out, "Registrer bruker");
        if(errorMessage!=null)
        {
            out.println("<h3>"+errorMessage+"</h3>");
        }
        out.println("<form action='register_user' method='POST'>");
        out.println("<label for='fullName'>Fullt navn</label> ");
        out.println("<input type='text' name='fullName'/>");
        out.println("<label for='phoneNumber'>Telefonnummer</label> ");
        out.println("<input type='tel' name='phoneNumber'/>");
        out.println("<input type='submit' value='Registrer bruker'/>");
        out.println("</form>");
        writeHtmlEnd(out);
    }

    private void writeHtmlStart(PrintWriter out, String title) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>"+title+"</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>"+title+"</h2>");
    }
    private void writeHtmlEnd(PrintWriter out) {
        out.println("</body>");
        out.println("</html>");
    }
    private Boolean validateUser(RegisterUserModel model){
        if(model.getFullName()==null)
            return false;
        if(model.getFullName().trim().equalsIgnoreCase(""))
            return false;
        if(model.getPhoneNumber()==null)
            return false;
        if(model.getPhoneNumber().trim().equalsIgnoreCase(""))
            return false;
        return true;
    }
}
