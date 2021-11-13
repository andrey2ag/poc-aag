package com.uvita.andrey.modules.repository.local.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

public abstract class BaseDao <T> {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        public abstract void insert(T entity);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        public abstract long insertAndGetId(T entity);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        public abstract void insertAll(List<T> list);

        @Update
        public abstract void update(T object);

        @Delete
        public abstract void delete(T object);

        @Delete
        public abstract void delete(List<T> list);
}
