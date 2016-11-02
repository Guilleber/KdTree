package kdtree;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

public class KdTreeTest {

	ArrayList<Point2i> grid() {
		ArrayList<Point2i> v_tree_points = new ArrayList<Point2i>();
		for(int i=0; i<10; ++i) {
			for(int j=0; j<10; ++j) {
				v_tree_points.add(new Point2i(10*i,10*j));
			}
		}
		return v_tree_points;
	}
	
	@Test
	public void testSimple() {

		// Init vector of points

		ArrayList<Point2i> v_tree_points = new ArrayList<Point2i>();
		
		// Create kd-tree
		
		KdTree<Point2i> tree = new KdTree<Point2i>(2);

		assertFalse("tree does not contain any points", tree.contains(new Point2i(0,0)));
		assertEquals("tree is dimension 2", tree.dimension(), 2);
		assertEquals("tree does not contain any points", tree.nb_points(), 0);

		tree.insert(new Point2i(0,0));

		assertTrue("(0,0) is in tree", tree.contains(new Point2i(0,0)));
		assertFalse("(1,1) should not be in tree", tree.contains(new Point2i(1,1)));
		assertEquals("tree contains (0,0)", tree.nb_points(), 1);

		tree.insert(new Point2i(1,1));

		assertTrue("(1,1) is in tree", tree.contains(new Point2i(1,1)));
		assertFalse("(1,0) should not be in tree", tree.contains(new Point2i(1,0)));
		assertEquals("tree contains (0,0) and (1,1)", tree.nb_points(), 2);

	}
	
	@Test
	public void testNearestNeighbor() {	
		// Init vector of points

		ArrayList<Point2i> v_tree_points = grid();

		// Create kd-tree
		
		KdTree<Point2i> tree = new KdTree<Point2i>(2, v_tree_points, Integer.MAX_VALUE);

		// Create a vector of query Point
		ArrayList<Point2i> v_query_points = new ArrayList<Point2i>();
		v_query_points.add(new Point2i(0,0));
		v_query_points.add(new Point2i(100,100));
		v_query_points.add(new Point2i(200,200));
		v_query_points.add(new Point2i(25,150));
		v_query_points.add(new Point2i(25,55));
		v_query_points.add(new Point2i(33,25));
		
		// Compare result of linear search with kdtree search

		for (Point2i p : v_query_points) {

			//TODO: move that in linear search class
			float l_min = Float.MAX_VALUE;
	        for (Point2i pi: v_tree_points) {
	        	float sqr_dist = pi.sqrDist(p);
	        	if(sqr_dist < l_min) {
	        		l_min = sqr_dist;
	        	}
	        }	        	
	        
	        Point2i np = tree.getNN(p);
	        float t_min = p.sqrDist(np);
		    assertTrue(t_min==l_min);
		}
	}
	
}
