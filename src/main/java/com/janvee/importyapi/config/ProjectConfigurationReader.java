package com.janvee.importyapi.config;

import com.intellij.openapi.project.Project;

public interface ProjectConfigurationReader<T> {

    T read(Project project);
}
