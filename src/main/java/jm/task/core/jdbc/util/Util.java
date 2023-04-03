package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/KataTask01";
    private static final String USERNAME = "bestuser";
    private static final String PASSWORD = "bestuser";

    private static final String DB_DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static final String DIALECT = "org.hibernate.dialect.MySQL8Dialect";
    private static final String SHOW_SQL = "true";
    private static final String HBM2DLL = "create";
    private static final String CURRENT_SESSION = "thread";



    public static SessionFactory getSessionFactory() {

        Configuration config = new Configuration();
        config.setProperty("hibernate.connector.driver_class", DB_DRIVER_NAME);
        config.setProperty("hibernate.connection.url", URL);
        config.setProperty("hibernate.connection.username", USERNAME);
        config.setProperty("hibernate.connection.password", PASSWORD);
        config.setProperty("hibernate.dialect", DIALECT);
        config.setProperty("hibernate.show_sql", SHOW_SQL);
        config.setProperty("hibernate.hbm2ddl.auto", HBM2DLL);
        config.setProperty("hibernate.current_session_context_class", CURRENT_SESSION);

        return config.addAnnotatedClass(User.class).buildSessionFactory();
    }
}




