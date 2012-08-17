/*
 * Copyright 2004-2011 H2 Group. Multiple-Licensed under the H2 License, Version
 * 1.0, and under the Eclipse Public License, Version 1.0
 * (http://h2database.com/html/license.html). Initial Developer: H2 Group
 */
package org.h2.test.store;

import org.h2.dev.store.btree.BtreeMap;
import org.h2.dev.store.btree.BtreeMapStore;
import org.h2.dev.store.btree.DataType;
import org.h2.dev.store.btree.MapFactory;
import org.h2.dev.store.btree.StringType;

/**
 * A data type factory.
 */
public class TestMapFactory implements MapFactory {

    @Override
    public <K, V> BtreeMap<K, V> buildMap(String mapType, BtreeMapStore store,
            int id, String name, DataType keyType, DataType valueType,
            long createVersion) {
        if (mapType.equals("s")) {
            return new SequenceMap<K, V>(store, id, name, keyType, valueType, createVersion);
        }
        throw new RuntimeException("Unsupported map type " + mapType);
    }

    @Override
    public DataType buildDataType(String s) {
        if (s.length() == 0) {
            return new StringType();
        }
        switch (s.charAt(0)) {
        case 'i':
            return new IntegerType();
        case 'r':
            return RowType.fromString(s, this);
        }
        throw new RuntimeException("Unknown data type " + s);
    }

    @Override
    public String getDataType(Class<?> objectClass) {
        if (objectClass == Integer.class) {
            return "i";
        }
        throw new RuntimeException("Unsupported object class " + objectClass.toString());
    }

}