package net.thisislaz.angularwithspringboot.query;

public class UserQueries {

    // because we are using the jdbc NamedParameter class, we provide the ":firstName" instead of the "?"
        // we are the parameters from the "SQlParameterSource method declared in userrepositoryimpl class
        // whoever we decide to name it in that method, that will be the value used in the query
    public static final String INSERT_USER_QUERY = "INSERT INTO Users (first_name, last_name, email, password) VALUES (:firstName, :lastName, :email, :password)";
    public static final String COUNT_USER_EMAIL_QUERY = "SELECT COUNT(*) FROM Users WHERE email = :email";
    public static final String INSERT_ACCOUNT_VERIFICATION_URL_QUERY = "INSERT INTO AccountVerifications (user_id, url) VALUES (:userId, :url)";
}
