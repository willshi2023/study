package org.interview.jdk.compare;

import java.util.Comparator;

public class DomainComparator implements Comparator<Domain> {

	@Override
	public int compare(Domain domain1, Domain domain2) {
		if (domain1.getStr().compareTo(domain2.getStr()) > 0)
			return 1;
		else if (domain1.getStr().compareTo(domain2.getStr()) == 0)
			return 0;
		else
			return -1;
	}

	public static void main(String[] args)
	{
	    Domain d1 = new Domain("c");
	    Domain d2 = new Domain("c");
	    Domain d3 = new Domain("b");
	    Domain d4 = new Domain("d");
	    DomainComparator dc = new DomainComparator();
	    System.out.println(dc.compare(d1, d2));
	    System.out.println(dc.compare(d1, d3));
	    System.out.println(dc.compare(d1, d4));
	}
}
