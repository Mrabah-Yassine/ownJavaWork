package fr.edf.epr2.rgn.transform.domain.read.factory;

import java.util.EnumMap;
import java.util.Map;

public class ReportStylePerType {

    private ReportStylePerType(){}

    static EnumMap<ReportType, ReportStyle> reportStyle = new EnumMap<>(ReportType.class);

    public static Map<ReportType, ReportStyle> getReportStyle() {
        reportStyle.put(ReportType.ROOT_ARTICLES, ReportStyle.ArticleReport);
        reportStyle.put(ReportType.ROOT_COMPOSANTS_MECA, ReportStyle.ComposantReport);
        reportStyle.put(ReportType.ROOT_ROOM, ReportStyle.RoomReport);
        reportStyle.put(ReportType.ROOT_COMPOSANTS_ELEC, ReportStyle.EquipmentListReport);
        reportStyle.put(ReportType.ROOT_CONTRACT, ReportStyle.CtbsComposantReport);
        reportStyle.put(ReportType.ROOT_CONSUMER, ReportStyle.ConsumerListReport);
        reportStyle.put(ReportType.ROOT_CABLE, ReportStyle.CableListReport);
        return reportStyle;
    }
}
