package com.janvee.importyapi.config.impl;

import com.intellij.notification.NotificationType;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.janvee.importyapi.config.ProjectConfigurationReader;
import com.janvee.importyapi.config.YApiProjectPersistentState;
import com.janvee.importyapi.content.YApiProjectProperty;
import com.janvee.importyapi.content.YApiPropertyConvertHolder;
import com.janvee.importyapi.util.NotificationUtils;
import org.jdom.Element;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ProjectConfigReader {

    private ProjectConfigReader() {
    }

    private final static ProjectConfigurationReader<YApiProjectProperty> reader = (project -> {
        YApiProjectPersistentState projectState = ServiceManager
                .getService(project, YApiProjectPersistentState.class);
        Element element = projectState.getState();
        if (element == null) {
            YApiProjectProperty property = new YApiProjectProperty();
            try {
                String projectConfig = new String(
                        Objects.requireNonNull(project.getProjectFile()).contentsToByteArray(),
                        StandardCharsets.UTF_8);
                if (projectConfig.contains("yapi")) {
                    NotificationUtils.createNotification("配置信息更新",
                                    "读取到旧版本的配置信息，配置信息已经更新", NotificationType.INFORMATION)
                            .notify(project);
                    property.setToken(projectConfig.split("projectToken\">")[1].split("</")[0]);
                    property.setUrl(projectConfig.split("yapiUrl\">")[1].split("</")[0]);
                    property.setLocalUrl(projectConfig.split("localUrl\">")[1].split("</")[0]);
                }
            } catch (Exception ignored) {
            }
            projectState.loadState(YApiPropertyConvertHolder.getConvert().serialize(property));
            return property;
        } else {
            return YApiPropertyConvertHolder.getConvert().deserialize(element);
        }
    });

    public static YApiProjectProperty read(Project project) {
        return reader.read(project);
    }

}
