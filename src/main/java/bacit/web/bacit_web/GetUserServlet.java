package bacit.web.bacit_web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GetUserServlet", value = "/GetUserServlet")
public class GetUserServlet extends HttpServlet {
    private String message = "User from database";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        PrintWriter out = response.getWriter();
        try {
            UserModel model = getUser(fullName, out);

            out.println("<h1>"+ message +"</h1>");
            out.println(model.getUser());
            out.println("<br/><a href='hello-servlet'>Hello Servlet</a>");
        } catch (SQLException | ClassNotFoundException | NullPointerException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    }


    private UserModel getUser(String fullName, PrintWriter out) throws SQLException, NullPointerException, ClassNotFoundException {
        Connection db = DBUtils.getINSTANCE().getConnection(out);

        String query = "select * from MytestDB.user where User_fullName = ?";
        PreparedStatement statement = db.prepareStatement(query);
        statement.setString(1, fullName);
        ResultSet rs =  statement.executeQuery();
        UserModel model = null;
        while (rs.next()) {
            model =
                new UserModel(rs.getString("User_fullName"), rs.getString("User_phoneNumber"));
        }
        return model;
    }
}
