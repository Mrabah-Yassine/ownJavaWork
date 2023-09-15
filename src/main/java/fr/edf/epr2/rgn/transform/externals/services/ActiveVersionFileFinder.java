package fr.edf.epr2.rgn.transform.externals.services;

import com.matrixone.apps.domain.DomainObject;

public class ActiveVersionFileFinder {

    private DomainObject sourceObjectDomainObject;

    public ActiveVersionFileFinder(DomainObject sourceObjectDomainObject) {
        this.sourceObjectDomainObject = sourceObjectDomainObject;
    }
}
