package xyz.eventstreamer.eventstreamer.ui.main;

import xyz.eventstreamer.eventstreamer.commons.Animation;

public interface MainContract {

    interface View {

        void openRegister(@Animation.AnimationType int animationType);
        void openLogin(@Animation.AnimationType int animationType);
        void openProfile(@Animation.AnimationType int animationType);
        void openDashboard(@Animation.AnimationType int animationType);
        void openFindEvent(@Animation.AnimationType int animationType);
        void openAboutEvent(@Animation.AnimationType int animationType, String eventId);
        void openAddEvent(@Animation.AnimationType int animationType);
        void openMap(@Animation.AnimationType int animationType);

    }
}
