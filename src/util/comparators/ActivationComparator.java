package util.comparators;

import java.util.Comparator;
import java.util.Map;
/** This class enable comparisons among examples (each key value of a map)  
 * 
 */
public class ActivationComparator implements Comparator<Integer> {

    Map<Integer, Double> base;
    public ActivationComparator (Map<Integer, Double> base) {
        this.base = base;
    }

	@Override
	public int compare(Integer o1, Integer o2) {
		if (base.get(o1) < base.get(o2)) 
            return 1;
        else 
        	return -1;
	}
}