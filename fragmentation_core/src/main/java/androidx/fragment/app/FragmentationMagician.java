package androidx.fragment.app;


import android.util.Log;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by YoKey on 16/1/22.
 */
public class FragmentationMagician {

    public static boolean isStateSaved(FragmentManager fragmentManager) {
        if (!(fragmentManager instanceof FragmentManagerImpl))
            return false;
        try {
            FragmentManagerImpl fragmentManagerImpl = (FragmentManagerImpl) fragmentManager;
            return fragmentManagerImpl.isStateSaved();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Like {@link FragmentManager#popBackStack()}} but allows the commit to be executed after an
     * activity's state is saved.  This is dangerous because the action can
     * be lost if the activity needs to later be restored from its state, so
     * this should only be used for cases where it is okay for the UI state
     * to change unexpectedly on the user.
     */
    public static void popBackStackAllowingStateLoss(final FragmentManager fragmentManager) {
        FragmentationMagician.hookStateSaved(fragmentManager, new Runnable() {
            @Override
            public void run() {
                fragmentManager.popBackStack();
            }
        });
    }

    /**
     * Like {@link FragmentManager#popBackStackImmediate()}} but allows the commit to be executed after an
     * activity's state is saved.
     */
    public static void popBackStackImmediateAllowingStateLoss(final FragmentManager fragmentManager) {
        FragmentationMagician.hookStateSaved(fragmentManager, new Runnable() {
            @Override
            public void run() {
                fragmentManager.popBackStackImmediate();
            }
        });
    }

    /**
     * Like {@link FragmentManager#popBackStackImmediate(String, int)}} but allows the commit to be executed after an
     * activity's state is saved.
     */
    public static void popBackStackAllowingStateLoss(final FragmentManager fragmentManager, final String name, final int flags) {
        FragmentationMagician.hookStateSaved(fragmentManager, new Runnable() {
            @Override
            public void run() {
                fragmentManager.popBackStack(name, flags);
            }
        });
    }

    /**
     * Like {@link FragmentManager#executePendingTransactions()} but allows the commit to be executed after an
     * activity's state is saved.
     */
    public static void executePendingTransactionsAllowingStateLoss(final FragmentManager fragmentManager) {
        FragmentationMagician.hookStateSaved(fragmentManager, new Runnable() {
            @Override
            public void run() {
                fragmentManager.executePendingTransactions();
            }
        });
    }

    public static List<Fragment> getActiveFragments(FragmentManager fragmentManager) {
        return fragmentManager.getFragments();
    }

    private static void hookStateSaved(FragmentManager fragmentManager, Runnable runnable) {
        if (!(fragmentManager instanceof FragmentManager)) return; // No need for FragmentManagerImpl

        if (isStateSaved(fragmentManager)) {
            Boolean tempStateSaved = getFieldValue(fragmentManager, "mStateSaved");
            Boolean tempStopped = getFieldValue(fragmentManager, "mStopped");

            setFieldValue(fragmentManager, "mStateSaved", false);
            setFieldValue(fragmentManager, "mStopped", false);

            runnable.run();

            setFieldValue(fragmentManager, "mStopped", tempStopped);
            setFieldValue(fragmentManager, "mStateSaved", tempStateSaved);
        } else {
            runnable.run();
        }
    }

    /**
     * Safely get a private field value using reflection.
     */
    private static Boolean getFieldValue(FragmentManager fragmentManager, String fieldName) {
        try {
            Field field = FragmentManager.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (Boolean) field.get(fragmentManager);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Log.e("FragmentationMagician", "Error accessing " + fieldName, e);
            return false; // Default to false if inaccessible
        }
    }

    /**
     * Safely set a private field value using reflection.
     */
    private static void setFieldValue(FragmentManager fragmentManager, String fieldName, Boolean value) {
        try {
            Field field = FragmentManager.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(fragmentManager, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Log.e("FragmentationMagician", "Error setting " + fieldName, e);
        }
    }
}