// UserDao.java
package com.melekkaraarslan.cookie_clicker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM user WHERE firstName LIKE :firstName AND lastName LIKE :lastName")
    LiveData<List<User>> getUserByFullName(String firstName, String lastName);
}
