package com.yichen.core.param.file;

import com.yichen.request.AbstractParam;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


/**
 * @author dengbojing
 */
@Setter
@Getter
public class FileParam extends AbstractParam {
    private String fileId;

    @NotNull(message = "文件内容不能为空")
    @Length(min = 1, message = "文件内容不能为空")
    private String fileContent;

    @NotNull(message = "文件名称不能为空")
    @Length(min = 1, message = "文件名称不能为空")
    private String fileName;
}
