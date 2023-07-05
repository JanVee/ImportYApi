package com.janvee.importyapi.config;

import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.janvee.importyapi.config.impl.ProjectConfigReader;
import com.janvee.importyapi.config.ui.YApiConfigurationForm;
import com.janvee.importyapi.constant.YApiConstants;
import com.janvee.importyapi.content.YApiProjectProperty;
import com.janvee.importyapi.content.YApiPropertyConvertHolder;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class YApiSearchableConfigurable implements SearchableConfigurable {
    private YApiConfigurationForm yApiConfigurationForm;

    private YApiProjectProperty yApiProjectProperty;

    private final YApiProjectPersistentState persistent;

    private final Project project;

    YApiSearchableConfigurable(Project project) {
        this.project = project;
        this.persistent = YApiProjectPersistentState.getInstance(project);
    }

    @NotNull
    @Override
    public String getId() {
        return "Import_To_YApi";
    }

    @Nls
    @Override
    public String getDisplayName() {
        return YApiConstants.name;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (yApiConfigurationForm == null) {
            yApiConfigurationForm = new YApiConfigurationForm();
            yApiConfigurationForm.init();
        }
        return yApiConfigurationForm.getPanel();
    }

    @Override
    public boolean isModified() {
        this.yApiProjectProperty = new YApiProjectProperty(
                yApiConfigurationForm.getUrlField().getText(),
                yApiConfigurationForm.getTokenField().getText(),
                yApiConfigurationForm.getLocalField().getText());
        return !this.yApiProjectProperty
                .equals(ProjectConfigReader.read(this.project));
    }

    @Override
    public void apply() {
        persistent.loadState(
                YApiPropertyConvertHolder.getConvert().serialize(this.yApiProjectProperty));
    }

    @Override
    public void reset() {
        this.loadValue();
    }

    private void loadValue() {
        YApiProjectProperty property = ProjectConfigReader.read(this.project);
        String url = property.getUrl();
        if (StringUtils.isNotBlank(url)) {
            yApiConfigurationForm.getUrlField().setText(url);
        }
        String localUrl = property.getLocalUrl();
        if (StringUtils.isNotBlank(localUrl)) {
            yApiConfigurationForm.getLocalField().setText(localUrl);
        }

        String token = property.getToken();
        if (StringUtils.isNotBlank(token)) {
            yApiConfigurationForm.getTokenField().setText(token);
        }
    }
}
