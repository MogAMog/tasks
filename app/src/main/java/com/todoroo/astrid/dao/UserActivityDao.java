package com.todoroo.astrid.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.todoroo.andlib.utility.DateUtilities;
import com.todoroo.astrid.data.RemoteModel;
import com.todoroo.astrid.data.UserActivity;
import com.todoroo.astrid.helper.UUIDHelper;

import java.util.List;

@Dao
public abstract class UserActivityDao {

    @Insert
    public abstract void insert(UserActivity userActivity);

    @Query("SELECT * FROM userActivity WHERE `action` = 'task_comment' AND target_id = :taskUuid AND deleted_at = 0 ORDER BY created_at DESC ")
    public abstract List<UserActivity> getCommentsForTask(String taskUuid);

    public void createNew(UserActivity item) {
        if (item.getCreated() == null || item.getCreated() == 0L) {
            item.setCreated(DateUtilities.now());
        }
        if (RemoteModel.isUuidEmpty(item.getRemoteId())) {
            item.setRemoteId(UUIDHelper.newUUID());
        }
        insert(item);
    }
}
