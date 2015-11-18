package org.nyjsl.swallow.views;

import android.content.Context;

/**
 * Base MVP View interface
 * Activity and Fragment should implement this interface
 */
public interface BaseView {

    Context getContext();

    void showD();

    void disD();

    void showT(int id);

    void showD(int id);

}
