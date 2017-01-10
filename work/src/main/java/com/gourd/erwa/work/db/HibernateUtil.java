package com.gourd.erwa.work.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;

/**
 * @author wei.Li
 */
class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY;
    private static final ThreadLocal<Session> SESSION = new ThreadLocal<>();

    private static final String HIBERNATE_CFG_XML = "hibernate.cfg.xml";

    static {
        try {
            final URL resource = HibernateUtil.class.getClassLoader().getResource("./");
            if (resource == null) {
                throw new FileNotFoundException("resource ./");
            }
            SESSION_FACTORY = new Configuration().configure(new File(resource.getPath() + HIBERNATE_CFG_XML))
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Session currentSession() throws HibernateException {
        Session s = SESSION.get();
        if (s == null) {
            s = SESSION_FACTORY.openSession();
            SESSION.set(s);
        }
        return s;
    }

    private static void closeSession() throws HibernateException {
        final Session s = SESSION.get();
        if (s != null) {
            s.close();
        }
        SESSION.set(null);
    }

    static void insertBySql(final String nativeQuerySql, final List<Object[]> values) {

        if (values == null) {
            return;
        }

        final Session session = currentSession();


        final Transaction transaction = session.beginTransaction();
        //transaction.begin();
        NativeQuery nativeQuery;

        final int size = values.size();
        for (int i1 = 0; i1 < size; i1++) {
            nativeQuery = session.createNativeQuery(nativeQuerySql);
            Object[] value = values.get(i1);
            for (int i = 0; i < value.length; i++) {
                nativeQuery.setParameter(i + 1, value[i]);
            }
            final int i = nativeQuery.executeUpdate();
            if (i1 % 100 == 0) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(i);
        }
        transaction.commit();
        closeSession();
    }

}
