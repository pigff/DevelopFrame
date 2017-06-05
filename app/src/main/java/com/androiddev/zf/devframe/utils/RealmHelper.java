package com.androiddev.zf.devframe.utils;

import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by greedy on 17/3/17.
 * createOrUpdate 系列的方法 都必须要有主键
 */

public class RealmHelper {

    public static RealmHelper sRealmHelper;
    private final Realm mRealm;

    private RealmHelper() {
        mRealm = Realm.getDefaultInstance();
    }

    public static RealmHelper getInstance() {
        if (sRealmHelper == null) {
            synchronized (RealmHelper.class) {
                if (sRealmHelper == null) {
                    sRealmHelper = new RealmHelper();
                }
            }
        }
        return sRealmHelper;
    }

    public Realm getRealm() {
        if (mRealm != null) {
            return mRealm;
        }
        return Realm.getDefaultInstance();
    }


    // ----------------------   增   -----------------------//
    public void copyToRealm(RealmObject object) {
        mRealm.beginTransaction();
        mRealm.copyToRealm(object);
        mRealm.commitTransaction();
    }

    public void copyToRealm(List<? extends RealmObject> list) {
        mRealm.beginTransaction();
        mRealm.copyToRealm(list);
        mRealm.commitTransaction();
    }

    /**
     * inser 比 copy 更快
     *
     * @param object
     */
    public void insert(RealmObject object) {
        mRealm.beginTransaction();
        mRealm.insert(object);
        mRealm.commitTransaction();
    }

    /**
     * @param collection
     */
    public void insert(Collection<? extends RealmObject> collection) {
        mRealm.beginTransaction();
        mRealm.insert(collection);
        mRealm.commitTransaction();
    }

    // -------------------------  增 / 改 ---------------------------//

    /**
     * @param collection
     */
    public void insertOrUpdate(Collection<? extends RealmObject> collection) {
        mRealm.beginTransaction();
        mRealm.insertOrUpdate(collection);
        mRealm.commitTransaction();
    }

    /**
     * @param
     */
    public void insertOrUpdate(RealmObject object) {
        mRealm.beginTransaction();
        mRealm.insertOrUpdate(object);
        mRealm.commitTransaction();
    }

    // ----------------------- 查/删 ----------------------------//

    public <T extends RealmObject> List<T> queryAllData(Class<T> clazz) {
        RealmResults<T> results = mRealm.where(clazz).findAll();
        return mRealm.copyFromRealm(results);
    }

    public <T extends RealmObject> T queryFirstData(Class<T> clazz) {
        return mRealm.where(clazz).findFirst();
    }

    public <T extends RealmObject> T queryDataById(Class<T> clazz, String id) {
        RealmResults<T> results = mRealm.where(clazz).equalTo("id", id).findAll();
        List<T> list = mRealm.copyToRealm(results);
        if (list.size() <= 0) {
            return null;
        }
        return list.get(0);
    }

    public <T extends RealmObject> T queryDataById(Class<T> clazz, int id) {
        RealmResults<T> results = mRealm.where(clazz).equalTo("id", id).findAll();
        List<T> list = mRealm.copyToRealm(results);
        if (list.size() <= 0) {
            return null;
        }
        return list.get(0);
    }

    public <T extends RealmObject> void deleteAllData(Class<T> clazz) {
        mRealm.beginTransaction();
        RealmResults<T> results = mRealm.where(clazz).findAll();
        results.deleteAllFromRealm();
        mRealm.commitTransaction();
    }

    public <T extends RealmObject> void deleteFirstData(Class<T> clazz) {
        mRealm.beginTransaction();
        T t = mRealm.where(clazz).findFirst();
        t.deleteFromRealm();
        mRealm.commitTransaction();
    }


    public <T extends RealmObject> void deleteDataById(Class<T> clazz, String id) {
        mRealm.beginTransaction();
        RealmResults<T> results = mRealm.where(clazz).equalTo("id", id).findAll();
        results.deleteAllFromRealm();
        mRealm.commitTransaction();
    }

    public <T extends RealmObject> void deleteDataById(Class<T> clazz, int id) {
        mRealm.beginTransaction();
        RealmResults<T> results = mRealm.where(clazz).equalTo("id", id).findAll();
        results.deleteAllFromRealm();
        mRealm.commitTransaction();
    }

    //------------------------ 异步系列 增 / 改 感觉还是用insertOrUpdate比较好 -------------------------//

    public RealmAsyncTask syncAddData(final Collection<? extends RealmObject> collection, final CommonCallBack callBack) {
        return mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(collection);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callBack.success();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callBack.error();
            }
        });
    }

    public RealmAsyncTask syncAddData(final RealmObject object, final CommonCallBack callBack) {
        return mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(object);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callBack.success();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callBack.error();
            }
        });
    }

    //------------------------ 异步系列 查 -------------------------//

    public <T extends RealmObject> RealmAsyncTask syncQueryData(final Class<T> clazz, final ResultCallBack<T> resultCallBack) {
        return mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<T> results = realm.where(clazz).findAll();
                resultCallBack.execute(realm.copyFromRealm(results));
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                resultCallBack.success();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                resultCallBack.error();
            }
        });
    }


    public <T extends RealmObject> RealmAsyncTask syncQueryData(final Class<T> clazz, final String id, final ResultCallBack<T> resultCallBack) {
        return mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<T> results = realm.where(clazz).equalTo("id", id).findAll();
                resultCallBack.execute(realm.copyFromRealm(results));
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                resultCallBack.success();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                resultCallBack.error();
            }
        });
    }

    /**
     * 根据id异步查找
     *
     * @param clazz
     * @param id
     * @param resultCallBack
     * @param <T>
     * @return
     */
    public <T extends RealmObject> RealmAsyncTask syncQueryData(final Class<T> clazz, final int id, final ResultCallBack<T> resultCallBack) {
        return mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<T> results = realm.where(clazz).equalTo("id", id).findAll();
                resultCallBack.execute(realm.copyFromRealm(results));
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                resultCallBack.success();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                resultCallBack.error();
            }
        });
    }

    //------------------------ 异步系列 删 -------------------------//

    public <T extends RealmObject> RealmAsyncTask syncDeleteAllData(final Class<T> clazz, final CommonCallBack callBack) {
        return mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<T> results = realm.where(clazz).findAll();
                results.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callBack.success();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callBack.error();
            }
        });
    }

    public <T extends RealmObject> RealmAsyncTask syncDeleteFirstData(final Class<T> clazz, final CommonCallBack callBack) {
        return mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                T t = realm.where(clazz).findFirst();
                t.deleteFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callBack.success();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callBack.error();
            }
        });
    }

    public <T extends RealmObject> RealmAsyncTask syncDeleteData(final Class<T> clazz, final String id, final CommonCallBack callBack) {
        return mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<T> results = realm.where(clazz).equalTo("id", id).findAll();
                results.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callBack.success();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callBack.error();
            }
        });
    }

    public <T extends RealmObject> RealmAsyncTask syncDeleteData(final Class<T> clazz, final int id, final CommonCallBack callBack) {
        return mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<T> results = realm.where(clazz).equalTo("id", id).findAll();
                results.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callBack.success();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callBack.error();
            }
        });
    }

    // --------------------- 清空数据库 --------------------- //
    public void clearDatabase() {
        mRealm.beginTransaction();
        mRealm.deleteAll();
        mRealm.commitTransaction();
    }

    public interface CommonCallBack {

        void success();

        void error();
    }

    public interface ResultCallBack<T extends RealmObject> {

        void execute(List<T> list);

        void success();

        void error();
    }


    //  -----------  将json串 或 数据流 写入数据库 感觉用不到 ---------- //
//    // ------------------------  增/改 --------------------------//
//
//    public void createAllFromJson(Class<? extends RealmObject> clazz, JSONArray jsonArray) {
//        mRealm.beginTransaction();
//        mRealm.createAllFromJson(clazz, jsonArray);
//        mRealm.commitTransaction();
//    }
//
//    public void createAllFromJson(Class<? extends RealmObject> clazz, String jsonArray) {
//        mRealm.beginTransaction();
//        mRealm.createAllFromJson(clazz, jsonArray);
//        mRealm.commitTransaction();
//    }
//
//    public void createAllFromJson(Class<? extends RealmObject> clazz, InputStream inputStream) {
//        mRealm.beginTransaction();
//        try {
//            mRealm.createAllFromJson(clazz, inputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mRealm.commitTransaction();
//    }
//
//    /**
//     * @param clazz
//     * @param jsonArray
//     * @see #createAllFromJson(Class, JSONArray) 不同的是这个的RealmObject 必须要有主键
//     */
//    public void createOrUpdateAllFromJson(Class<? extends RealmObject> clazz, JSONArray jsonArray) {
//        mRealm.beginTransaction();
//        mRealm.createOrUpdateAllFromJson(clazz, jsonArray);
//        mRealm.commitTransaction();
//    }
//
//    /**
//     * 同上
//     *
//     * @param clazz
//     * @param jsonArray
//     */
//    public void createOrUpdateAllFromJson(Class<? extends RealmObject> clazz, String jsonArray) {
//        mRealm.beginTransaction();
//        mRealm.createOrUpdateAllFromJson(clazz, jsonArray);
//        mRealm.commitTransaction();
//    }
//
//    /**
//     * 同上
//     *
//     * @param clazz
//     * @param inputStream
//     */
//    public void createOrUpdateAllFromJson(Class<? extends RealmObject> clazz, InputStream inputStream) {
//        mRealm.beginTransaction();
//        try {
//            mRealm.createOrUpdateAllFromJson(clazz, inputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mRealm.commitTransaction();
//    }
//
//    // ---------------------------       -----------------------//
//
//    public void createObjectFromJson(Class<? extends RealmObject> clazz, JSONObject jsonObject) {
//        mRealm.beginTransaction();
//        mRealm.createObjectFromJson(clazz, jsonObject);
//        mRealm.commitTransaction();
//    }
//
//    public void createObjectFromJson(Class<? extends RealmObject> clazz, String jsonObject) {
//        mRealm.beginTransaction();
//        mRealm.createObjectFromJson(clazz, jsonObject);
//        mRealm.commitTransaction();
//    }
//
//    public void createOrUpdateObjectFromJson(Class<? extends RealmObject> clazz, JSONObject jsonObject) {
//        mRealm.beginTransaction();
//        mRealm.createOrUpdateObjectFromJson(clazz, jsonObject);
//        mRealm.commitTransaction();
//    }
//
//    public void createOrUpdateObjectFromJson(Class<? extends RealmObject> clazz, String jsonObject) {
//        mRealm.beginTransaction();
//        mRealm.createOrUpdateObjectFromJson(clazz, jsonObject);
//        mRealm.commitTransaction();
//    }
}



