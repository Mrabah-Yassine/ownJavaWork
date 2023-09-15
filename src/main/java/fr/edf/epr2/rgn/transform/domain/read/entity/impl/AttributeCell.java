package fr.edf.epr2.rgn.transform.domain.read.entity.impl;

import fr.edf.epr2.rgn.transform.domain.read.entity.itf.ICell;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class AttributeCell implements ICell {

    private String cellType;
    private String cellName;
    private XSSFColor cellColor;

    @Override
    public String getCellType() {
        return cellType;
    }

    @Override
    public void setCellType(String cellType) {
        this.cellType = cellType;
    }

    @Override
    public String getCellName() {
        return cellName;
    }

    @Override
    public void setCellName(String cellName) {
        this.cellName = cellName;
    }

    @Override
    public XSSFColor getCellColor() {
        return cellColor;
    }

    @Override
    public void setCellColor(XSSFColor cellColor) {
        this.cellColor = cellColor;
    }
}
