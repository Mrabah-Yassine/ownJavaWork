package fr.edf.epr2.rgn.transform.externals.services;

import matrix.util.StringList;

public class SearchEngine implements ISearchEngine{

    private final String relName;
    private final String objectType;
    private final StringList busSelector;
    private final StringList relSelector;
    private final boolean isToDirectionSearch;
    private final boolean isFromDirectionSearch;
    private final short level;
    private final String busWhereClause;
    private final String relWhereClause;
    private final short nbObjectsPerLevel;

    public SearchEngine(String relName, String objectType, StringList busSelector, StringList relSelector,
                        boolean isToDirectionSearch, boolean isFromDirectionSearch, short level, String busWhereClause,
                        String relWhereClause, short nbObjectsPerLevel) {
        this.relName = relName;
        this.objectType = objectType;
        this.busSelector = busSelector;
        this.relSelector = relSelector;
        this.isToDirectionSearch = isToDirectionSearch;
        this.isFromDirectionSearch = isFromDirectionSearch;
        this.level = level;
        this.busWhereClause = busWhereClause;
        this.relWhereClause = relWhereClause;
        this.nbObjectsPerLevel = nbObjectsPerLevel;
    }

    @Override
    public void launchSearch() {

        DomainObjectQueryBuilder query = new DomainObjectQueryBuilder.Builder()
                .withRelName(this.relName)
                .withObjectType(this.objectType)
                .withBusSelector(this.busSelector)
                .withRelSelector(this.relSelector)
                .withToDirection(this.isToDirectionSearch)
                .withFromDirection(this.isFromDirectionSearch)
                .withLevel(this.level)
                .withBusWhereClause(this.busWhereClause)
                .withRelWhereClause(this.relWhereClause)
                .withNumberOfObjectsPerLevel(this.nbObjectsPerLevel)
                .build();
        ISearchQuery iQuery;
                iQuery.execute();
    }
}
