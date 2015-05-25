package fr.todooz.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TagCloudTest {

    @Test
    public void add() {
        TagCloud tagCloud = new TagCloud();
        tagCloud.add( "java" );

        assertEquals( 1, tagCloud.size() );
    }

    @Test
    public void addMultiple() {
        TagCloud tagCloud = new TagCloud();
        tagCloud.add( "java", "ruby", "python" );

        assertEquals( 3, tagCloud.size() );
    }

    @Test
    public void addEmpty() {
        TagCloud tagCloud = new TagCloud();
        tagCloud.add();

        assertEquals( 0, tagCloud.size() );
    }

    @Test
    public void addNull() {
        TagCloud tagCloud = new TagCloud();
        tagCloud.add( (String[]) null );
        assertEquals( 0, tagCloud.size() );
    }

    @Test
    public void contains() {
        TagCloud tagCloud = new TagCloud();

        tagCloud.add( "java" );

        assertTrue( tagCloud.contains( "java" ) );
    }

    @Test
    public void size() {
        TagCloud tagCloud = new TagCloud();

        tagCloud.add( "java", "java", "python", "python", "", null );

        assertEquals( 2, tagCloud.size() );
    }

}
