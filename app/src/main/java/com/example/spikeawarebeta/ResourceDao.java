package com.example.spikeawarebeta;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface ResourceDao {
    // For the User: Only show approved items
    @Query("SELECT * FROM resources WHERE isApproved = 1")
    LiveData<List<ResourceItem>> getAllApprovedResources();

    // Search functionality (User Requirement)
    @Query("SELECT * FROM resources WHERE isApproved = 1 AND (title LIKE '%' || :query || '%' OR desc LIKE '%' || :query || '%')")
    LiveData<List<ResourceItem>> searchResources(String query);

    // For the Admin: Show unapproved items (Moderation Requirement)
    @Query("SELECT * FROM resources WHERE isApproved = 0")
    LiveData<List<ResourceItem>> getPendingResources();

    @Insert
    void insert(ResourceItem item); // For "Suggest a Resource"

    @Update
    void update(ResourceItem item); // For "Approve"

    @Delete
    void delete(ResourceItem item); // For "Reject"
}
