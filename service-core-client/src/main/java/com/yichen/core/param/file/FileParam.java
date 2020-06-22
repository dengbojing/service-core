package com.yichen.core.param.file;

import com.yichen.request.AbstractParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dengbojing
 */
@Setter
@Getter
public class FileParam extends AbstractParam {
    private String fileId;

    private String fileContent;

    private String fileName;
}
