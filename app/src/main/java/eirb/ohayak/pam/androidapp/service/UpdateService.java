package eirb.ohayak.pam.androidapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UpdateService extends IntentService {
    private static final String TAG = "UpdateService";

    private static final String ACTION_UPDATE = "eirb.ohayak.pam.androidapp.service.action.UPDATE";


    // TODO: Rename parameters
    private static final String TOUR_ID = "eirb.ohayak.pam.androidapp.service.extra.TOUR";

    public UpdateService() {
        super("UpdateService");
    }


    // TODO: Customize helper method
    public static void startActionUpdate(Context context, Double tourId) {
        Intent intent = new Intent(context, UpdateService.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(TOUR_ID, tourId);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE.equals(action)) {
                final Double tourId = intent.getDoubleExtra(TOUR_ID, 0);
                handleActionUpdate(tourId);
            }
        }
    }

    private void handleActionUpdate(Double tourId) {
        Log.d(TAG, "updating tour: "+tourId);



    }
}
