package fr.edf.epr2.rgn.transform.domain.read.entity.itf;

import org.apache.poi.xssf.usermodel.XSSFColor;

public interface ICell {

    String getCellType();

    void setCellType(String cellType);

    String getCellName();

    void setCellName(String cellName);

    XSSFColor getCellColor();

    void setCellColor(XSSFColor cellColor);
}
