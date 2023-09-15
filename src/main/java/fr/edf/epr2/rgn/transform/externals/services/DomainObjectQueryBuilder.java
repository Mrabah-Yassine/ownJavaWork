package fr.edf.epr2.rgn.transform.externals.services;

import matrix.util.StringList;

public class DomainObjectQueryBuilder{

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

    private DomainObjectQueryBuilder(Builder builder) {
        this.relName = builder.relName;
        this.objectType = builder.objectType;
        this.busSelector = builder.busSelector;
        this.relSelector = builder.relSelector;
        this.isToDirectionSearch = builder.isToDirectionSearch;
        this.isFromDirectionSearch = builder.isFromDirectionSearch;
        this.level = builder.level;
        this.busWhereClause = builder.busWhereClause;
        this.relWhereClause = builder.relWhereClause;
        this.nbObjectsPerLevel = builder.nbObjectsPerLevel;
    }


    public static class Builder{

        private String relName;
        private String objectType;
        private StringList busSelector;
        private StringList relSelector;
        private boolean isToDirectionSearch;
        private boolean isFromDirectionSearch;
        private short level;
        private String busWhereClause;
        private String relWhereClause;
        private short nbObjectsPerLevel;

        public DomainObjectQueryBuilder build(){
            return new DomainObjectQueryBuilder(this);
        }


        public Builder withRelName(String relName){
            this.relName = relName;
            return this;
        }

        public Builder withObjectType(String objectType){
            this.objectType = objectType;
            return this;
        }

        public Builder withBusSelector(StringList busSelector){
            this.busSelector = busSelector;
            return this;
        }

        public Builder withRelSelector(StringList relSelector){
            this.relSelector = relSelector;
            return this;
        }


        public Builder withToDirection(boolean isToDirectionSearch){
            this.isToDirectionSearch = isToDirectionSearch;
            return this;
        }

        public Builder withFromDirection(boolean isFromDirectionSearch){
            this.isFromDirectionSearch = isFromDirectionSearch;
            return this;
        }

        public Builder withLevel(short level){
            this.level = level;
            return this;
        }

        public Builder withBusWhereClause(String busWhereClause){
            this.busWhereClause = busWhereClause;
            return this;
        }

        public Builder withRelWhereClause(String relWhereClause){
            this.relWhereClause = relWhereClause;
            return this;
        }

        public Builder withNumberOfObjectsPerLevel(short nbObjectsPerLevel){
            this.nbObjectsPerLevel = nbObjectsPerLevel;
            return this;
        }


    }

    @Override
    public String toString() {
        return "DomainObjectQueryBuilder{" +
                "relName='" + relName + '\'' +
                ", objectType='" + objectType + '\'' +
                ", isToDirectionSearch='" + isToDirectionSearch + '\'' +
                ", isFromDirectionSearch=" + isFromDirectionSearch +
                ", level='" + level + '\'' +
                ", busWhereClause='" + busWhereClause + '\'' +
                ", relWhereClause='" + relWhereClause + '\'' +
                ", nbObjectsPerLevel='" + nbObjectsPerLevel + '\'' +
                '}';
    }
}
