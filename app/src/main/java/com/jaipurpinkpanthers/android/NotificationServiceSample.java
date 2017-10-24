package com.jaipurpinkpanthers.android;

/**
 * Created by oem on 24/9/17.
 */
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.MainThread;
import android.util.Log;

import com.pushwoosh.Pushwoosh;
import com.pushwoosh.notification.NotificationServiceExtension;
import com.pushwoosh.notification.PushMessage;

public class NotificationServiceSample extends NotificationServiceExtension {
    Context context;
    @Override
    public boolean onMessageReceived(final PushMessage message) {
        Log.d(MainActivity.LTAG, "NotificationService.onMessageReceived: " + message.toJson().toString());

        // automatic foreground push handling
        if (isAppOnForeground()) {
            Handler mainHandler = new Handler(getApplicationContext().getMainLooper());
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    handlePush(message);
                }
            });

            // this indicates that notification should not be displayed
            return true;
        }

        return false;
    }

    @Override
    protected void startActivityForPushMessage(PushMessage message) {
        super.startActivityForPushMessage(message);

        // TODO: start custom activity if necessary
        // start your activity instead:
        Intent launchIntent  = new Intent(getApplicationContext(), MainActivity.class);
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        // (Optional) pass notification data to Activity
        launchIntent.putExtra(Pushwoosh.PUSH_RECEIVE_EVENT, message.toJson().toString());

        context.startActivity(launchIntent);

        handlePush(message);
    }

    @MainThread
    private void handlePush(PushMessage message) {
        Log.d(MainActivity.LTAG, "NotificationService.handlePush: " + message.toJson().toString());
        // TODO: handle push message
    }
}