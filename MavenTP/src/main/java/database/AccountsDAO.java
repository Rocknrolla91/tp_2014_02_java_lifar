package database;

import exception.DataServiceException;
import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.criterion.Restrictions;

/**
 * Created by Alena on 10.03.14.
 */
public class AccountsDAO implements interfaceDAO {
    private SessionFactory sessionFactory;

    public AccountsDAO(SessionFactory session)
    {
        this.sessionFactory = session;
    }

    @Override
    public AccountsDataSet getAccountById(long id)
    {
        Session session = sessionFactory.openSession();
        return (AccountsDataSet) session.load(AccountsDataSet.class, id);
    }

    @Override
    public AccountsDataSet getAccount(String name)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(AccountsDataSet.class);
        return (AccountsDataSet) criteria.add(Restrictions.eq("login", name)).uniqueResult();
    }

    @Override
    public boolean saveAccount(AccountsDataSet dataSet) throws DataServiceException
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try
        {
            session.save(dataSet);
            transaction.commit();
        }
        catch (ConstraintViolationException e)
        {
            throw new DataServiceException("This user already exists");
        }
        catch (HibernateException e)
        {
            session.close();
            return false;
        }

        session.close();
        return true;
    }
}
