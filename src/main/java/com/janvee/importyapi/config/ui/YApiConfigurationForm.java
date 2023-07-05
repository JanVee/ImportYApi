package com.janvee.importyapi.config.ui;

import com.intellij.openapi.actionSystem.ActionToolbarPosition;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.DoubleClickListener;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBList;
import com.intellij.uiDesigner.core.GridConstraints;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Enumeration;

/**
 * 配置面板
 */
public class YApiConfigurationForm {
    private JTextField urlField;
    private JTextField tokenField;
    private JPanel panel;
    private JTextField localField;
    public JPanel getPanel() {
        return panel;
    }
    public JTextField getUrlField() {
        return urlField;
    }
    public JTextField getLocalField() {
        return localField;
    }
    public JTextField getTokenField() {
        return tokenField;
    }

    private void createUIComponents() {
    }

    public void init() {
    }
}
