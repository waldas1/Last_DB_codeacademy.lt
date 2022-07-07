package lt.codeacademy.provider;

import lt.codeacademy.entity.Questions;
import lt.codeacademy.entity.Test;
import lt.codeacademy.entity.TestInfo;
import lt.codeacademy.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

public class SessionFactoryProvider {
    private static SessionFactoryProvider instance;
    private final SessionFactory sessionFactory;

    private SessionFactoryProvider() {

        Configuration configuration = new Configuration();
        configuration.setProperties(createHibernateProperties());

        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Test.class);
        configuration.addAnnotatedClass(Questions.class);
        configuration.addAnnotatedClass(TestInfo.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    public static SessionFactoryProvider getInstance() {
        if (instance == null) {
            instance = new SessionFactoryProvider();
        }
        return instance;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private Properties createHibernateProperties() {
        Properties p = new Properties();

        p.put(DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        p.put(DRIVER, "org.postgresql.Driver");
        p.put(URL, "jdbc:postgresql://localhost/egzamdb");
        p.put(USER, "postgres");
        p.put(PASS, "Waldas1995");
        p.put(SHOW_SQL, "true");
        p.put(HBM2DDL_AUTO, "update");//create-drop

        return p;
    }

}
