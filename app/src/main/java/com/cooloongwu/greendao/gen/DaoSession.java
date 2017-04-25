package com.cooloongwu.greendao.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.cooloongwu.coolarithmetic.entity.Advance;

import com.cooloongwu.greendao.gen.AdvanceDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 *
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig advanceDaoConfig;

    private final AdvanceDao advanceDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        advanceDaoConfig = daoConfigMap.get(AdvanceDao.class).clone();
        advanceDaoConfig.initIdentityScope(type);

        advanceDao = new AdvanceDao(advanceDaoConfig, this);

        registerDao(Advance.class, advanceDao);
    }

    public void clear() {
        advanceDaoConfig.clearIdentityScope();
    }

    public AdvanceDao getAdvanceDao() {
        return advanceDao;
    }

}
