package org.flymine.objectstore.query;

/*
 * Copyright (C) 2002-2003 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.util.List;
import java.util.Set;

import org.flymine.objectstore.ObjectStore;
import org.flymine.objectstore.ObjectStoreException;

/**
 * This class is equivalent to a Result object with ResultRows consisting only of single items
 *
 * @author Mark Woodbridge
 * @author Richard Smith
 */
public class SingletonResults extends Results implements Set
{
    /**
     * Constructor for a SingletonResults object
     *
     * @param q the Query that produces this Results
     * @param os the ObjectStore that can be used to get results rows from
     * @throws IllegalArgumentException if q does not return a single column of type QueryClass
     */
     public SingletonResults(Query q, ObjectStore os) {
         super(q, os);

         // Test that this Query returns a single column of type QueryClass
         if (q.getSelect().size() != 1) {
             throw new IllegalArgumentException("Query must return a single column");
         }

         if (!(q.getSelect().get(0) instanceof QueryClass)) {
             throw new IllegalArgumentException("Query must select a QueryClass");
         }
     }

    /**
     * @see Results#range
     */
    public List range(int start, int end) throws ObjectStoreException {
        List rows = super.range(start, end);
        for (int i = 0; i < rows.size(); i++) {
            rows.set(i, ((List) rows.get(i)).get(0));
        }
        return rows;
    }
}
