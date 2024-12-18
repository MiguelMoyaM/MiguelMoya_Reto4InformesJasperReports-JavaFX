package com.example.miguelmoya_retoconjuntodiad_2parte;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public static SessionFactory sessionFactory;

    static{
        sessionFactory = new Configuration()
                .configure()
                .setProperty("hibernate.connection.password",System.getenv("hibernate_password"))
                .setProperty("hibernate.connection.username",System.getenv("hibernate_username"))
                .buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
