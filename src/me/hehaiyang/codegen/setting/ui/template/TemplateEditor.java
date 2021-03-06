package me.hehaiyang.codegen.setting.ui.template;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.util.ui.JBUI;
import com.yourkit.util.Strings;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.hehaiyang.codegen.model.CodeTemplate;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Desc: 模版配置
 * Mail: hehaiyangwork@qq.com
 * Date: 2017/3/17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TemplateEditor extends JPanel {

    private final JPanel textPanel = new JPanel(new GridLayout(3,2));
    private final JTextField id = new JTextField();
    private final JTextField display = new JTextField();
    private final JTextField extension = new JTextField();
    private final JTextField filename = new JTextField();
    private Editor editor;

    public TemplateEditor(){
        setLayout(new BorderLayout());
        setPreferredSize(JBUI.size(300, 100));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        init();
    }

    private void init(){
        textPanel.setPreferredSize(new Dimension(0, 70));
        display.setSize(new Dimension(200, 20));
        extension.setSize(new Dimension(80,20));
        filename.setSize(new Dimension(200,20));

        textPanel.add(new JLabel("Name:"));
        textPanel.add(display);
        textPanel.add(new JLabel("Filename:"));
        textPanel.add(filename);
        textPanel.add(new JLabel("Extension:"));
        textPanel.add(extension);

        this.add(textPanel, BorderLayout.NORTH);

        editor = emptyEditor();
        this.add(editor.getComponent(), BorderLayout.CENTER);
    }

    public void refresh(@NotNull CodeTemplate codeTemplate) {
        id.setText(codeTemplate.getId());
        display.setText(codeTemplate.getDisplay());
        extension.setText(codeTemplate.getExtension());
        filename.setText(codeTemplate.getFilename());

        editor = createEditor(codeTemplate.getTemplate(), codeTemplate.getExtension());

        this.removeAll();
        this.add(textPanel, BorderLayout.NORTH);
        this.add(editor.getComponent(), BorderLayout.CENTER);
    }

    private Editor emptyEditor(){
        return createEditor(null, null);
    }

    /**
     * 创建编辑器
     * @param template
     * @param extension
     * @return
     */
    private Editor createEditor(String template, String extension) {
        template = Strings.isNullOrEmpty(template) ? "" : template;
        extension = Strings.isNullOrEmpty(extension) ? "vm" : extension;
        EditorFactory factory = EditorFactory.getInstance();
        Document velocityTemplate = factory.createDocument(template);
        Editor editor = factory.createEditor(velocityTemplate, null, FileTypeManager.getInstance()
                .getFileTypeByExtension(extension), false);
        EditorSettings editorSettings = editor.getSettings();
        editorSettings.setLineNumbersShown(true);
        return editor;
    }

    public CodeTemplate getCodeTemplate(){
        CodeTemplate codeTemplate = new CodeTemplate();
        codeTemplate.setId(id.getText());
        codeTemplate.setDisplay(display.getText());
        codeTemplate.setExtension(extension.getText());
        codeTemplate.setFilename(filename.getText());
        codeTemplate.setTemplate(editor.getDocument().getText());
        return codeTemplate;
    }

}
