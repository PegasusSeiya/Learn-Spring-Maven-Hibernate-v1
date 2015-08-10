package fr.todooz.util;

import java.util.ArrayList;
import java.util.List;

public class TagCloud {

    private List<String> tags = new ArrayList<String>();

    public List<String> getTags() {
        return tags;
    }

    public void setTags( List<String> tags ) {
        this.tags = tags;
    }

    public void add( String tag ) {
        tags.add( tag );
    }

    public int size() {
        List<String> tagsCopy = new ArrayList<String>( tags );
        while ( tagsCopy.contains( "" ) ) {
            tagsCopy.remove( "" );
        }
        while ( tagsCopy.contains( null ) ) {
            tagsCopy.remove( null );
        }

        return countSansDoublon( tagsCopy );
    }

    public void add( String... tagArray ) {
        if ( tagArray != null ) {
            for ( String tag : tagArray ) {
                tags.add( tag );
            }
        }
    }

    public Boolean contains( String tag ) {
        return tags.contains( tag );
    }

    /**
     * Count items in list without doublon.
     * 
     * @param list
     * @return
     */
    public int countSansDoublon( List<String> list ) {

        return newListSansDoublon( list ).size();
    }

    /**
     * Remove all doublons of tag in the list.
     * 
     * @param list
     * @param tag
     */
    public List<String> newListSansDoublon( List<String> list ) {
        List<String> listSansDoublon = new ArrayList<String>();
        for ( String item : list ) {
            if ( !listSansDoublon.contains( item ) ) {
                listSansDoublon.add( item );
            }
        }
        return listSansDoublon;
    }

}
