package com.markdevelopers.rakshak.assignment;

import com.markdevelopers.rakshak.common.BaseContract;
import com.markdevelopers.rakshak.data.remote.models.AssignmentWrapper;
import com.markdevelopers.rakshak.data.remote.models.UserResponse;

import okhttp3.RequestBody;

/**
 * Created by Archish on 1/29/2017.
 */

public interface AssignmentContract {
    interface AssignmentView extends BaseContract.BaseView{
        void onAssign(AssignmentWrapper assignmentWrapper);
        void onSuccess(UserResponse userResponse);
    }
    interface AssignmentPresenter{
        void fetchAssignments(String accessToken);
        void onSendPost(String accessToken, String title, String description, RequestBody postimage,int did);
        void onSendPostNoImage(String accessToken, String title, String description,int did);
    }
}
