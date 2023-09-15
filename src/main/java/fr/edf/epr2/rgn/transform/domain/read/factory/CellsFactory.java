package fr.edf.epr2.rgn.transform.domain.read.factory;

import fr.edf.epr2.rgn.transform.domain.read.entity.impl.AttributeCell;
import fr.edf.epr2.rgn.transform.domain.read.entity.impl.HeaderCell;
import fr.edf.epr2.rgn.transform.domain.read.entity.itf.ICell;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class CellsFactory {

    private CellsFactory() {
    }

    public static ICell createCellWithProperties(String cellType, String cellName, XSSFColor color) {
        ICell cell = null;
        if (cellType.equalsIgnoreCase("headers")) {
            cell = new HeaderCell();
        } else if (cellType.equalsIgnoreCase("attributes")) {
            cell = new AttributeCell();
        }
        if (cell == null) {
            throw new UnsupportedOperationException(String.format("Unable to create cell object with type %s and name %s", cellType, cellName));
        }
        cell.setCellName(cellName);
        cell.setCellColor(color);
        cell.setCellType(cellType);
        return cell;
    }
}
