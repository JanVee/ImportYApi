package com.janvee.importyapi.util;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.janvee.importyapi.constant.YApiConstants;

public class NotificationUtils {

    public static Notification createNotification(String subTitle, String content, NotificationType notificationType) {
        return new Notification(YApiConstants.name, YApiConstants.name, content, notificationType).setSubtitle(subTitle);
    }

}
