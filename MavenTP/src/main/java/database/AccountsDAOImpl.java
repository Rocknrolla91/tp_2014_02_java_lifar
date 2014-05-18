package database;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

/**
 * Created by Alena on 10.03.14.
 */
public class AccountsDAOImpl implements AccountsDAO {

    private SessionFactory sessionFactory;

    public AccountsDAOImpl(DatabaseConnector databaseConnector)
    {
        this.sessionFactory = databaseConnector.getSessionFactory();
    }

    @Override
    public AccountsDataSet getAccount(String name)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(AccountsDataSet.class);
        AccountsDataSet result = (AccountsDataSet) criteria.add(Restrictions.eq("login", name)).uniqueResult();
        return result;
    }

    @Override
    public boolean saveAccount(AccountsDataSet dataSet)
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try
        {
            session.save(dataSet);
            transaction.commit();
        }
        catch (HibernateException e)
        {
            session.close();
            return false;
        }

        session.close();
        return true;
    }

    @Override
    public boolean deleteAccount(String login)
    {
        AccountsDataSet account = getAccount(login);
        if(account != null)
        {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            try
            {
                session.delete(account);
                transaction.commit();
            }
            catch (HibernateException e)
            {
                session.close();
                e.printStackTrace();
            }
            session.close();
            return true;
        }
        return false;
    }
}
