package nl.stefandv.level_4_assignment;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insert(Item product);

    @Delete
    void delete(Item product);

    @Delete
    void delete(List<Item> products);

    @Query("SELECT * from item_table")
    List<Item> getAllItems();

}
