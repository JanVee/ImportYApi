package com.janvee.importyapi;

import com.intellij.notification.NotificationListener;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.janvee.importyapi.config.impl.ProjectConfigReader;
import com.janvee.importyapi.content.YApiProjectProperty;
import com.janvee.importyapi.model.YApiResponse;
import com.janvee.importyapi.tcf.Swagger;
import com.janvee.importyapi.util.NotificationUtils;
import org.apache.commons.lang3.StringUtils;

public class ImportYApiAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        Project project = e.getData(CommonDataKeys.PROJECT);

        YApiProjectProperty property = ProjectConfigReader.read(project);
        // 项目Token
        String token = property.getToken();
        // 本地域名
        String localUrl = property.getLocalUrl();
        // yApi地址
        String yapiUrl = property.getUrl();
        // 配置校验
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(yapiUrl) || StringUtils.isEmpty(localUrl)) {
            NotificationUtils.createNotification("配置信息异常", "请检查配置参数是否正常",
                    NotificationType.ERROR).notify(project);
            return;
        }

        try {
            NotificationUtils.createNotification("开始上传，执行中...",
                            "正在同步接口数据到YApi，等您稍等！",
                            NotificationType.INFORMATION).setListener(new NotificationListener.UrlOpeningListener(false))
                    .notify(project);

            // 上传
            YApiResponse yapiResponse = new Swagger().getSwaggerJson(property);
            if (yapiResponse.getErrcode() != 0) {
                NotificationUtils.createNotification("上传失败",
                        "api上传失败原因:" + yapiResponse.getErrmsg(),
                        NotificationType.ERROR).notify(project);
            }

            // 获取项目ID
            int projectId = new Swagger().getProjectId(property);
            String url = yapiUrl + "/project/" + projectId + "/interface/api";
            NotificationUtils.createNotification("上传成功",
                            "<p>接口文档地址:  <a href=\"" + url + "\">" + url
                                    + "</a></p>",
                            NotificationType.INFORMATION).setListener(new NotificationListener.UrlOpeningListener(false))
                    .notify(project);

        } catch (Exception e1) {
            NotificationUtils.createNotification("上传失败", "api上传失败原因:" + e1,
                            NotificationType.ERROR)
                    .notify(project);
            return;
        }
    }
}
