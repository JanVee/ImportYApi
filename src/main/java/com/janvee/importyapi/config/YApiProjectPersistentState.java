package com.janvee.importyapi.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.project.Project;
import com.janvee.importyapi.content.YApiProjectProperty;
import com.janvee.importyapi.content.YApiPropertyConvertHolder;
import com.janvee.importyapi.content.YApiPropertyXmlConvert;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

@State(name = "ProjectImportToYApi")
public class YApiProjectPersistentState implements PersistentStateComponent<Element> {

    private final YApiPropertyXmlConvert<YApiProjectProperty> convert = YApiPropertyConvertHolder
            .getConvert();

    private YApiProjectProperty yApiProjectProperty;

    YApiProjectPersistentState() {
    }

    public static YApiProjectPersistentState getInstance(Project project) {
        return ServiceManager.getService(project, YApiProjectPersistentState.class);
    }

    @Override
    public Element getState() {
        return yApiProjectProperty == null ? null : this.convert.serialize(yApiProjectProperty);
    }

    @Override
    public void loadState(@NotNull Element element) {
        this.yApiProjectProperty = this.convert.deserialize(element);
    }


}
