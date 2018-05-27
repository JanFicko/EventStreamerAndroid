package xyz.eventstreamer.eventstreamer.ui.main;

import xyz.eventstreamer.eventstreamer.commons.Animation;

public interface MainContract {

    interface View {

        void openRegister(@Animation.AnimationType int animationType);
        void openLogin(@Animation.AnimationType int animationType);
        void openDashboard(@Animation.AnimationType int animationType);
        void openFindEvent(@Animation.AnimationType int animationType);
        void openAboutEvent(@Animation.AnimationType int animationType);
        void openAddEvent(@Animation.AnimationType int animationType);

    }
}
