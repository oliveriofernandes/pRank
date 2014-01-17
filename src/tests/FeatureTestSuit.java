package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  BinaryPerceptronTest.class,
  MulticlassPerceptronTest.class,
  PairWiseTestCRS.class,
  MTest.class,
  CSRTest.class
})

public class FeatureTestSuit {
	// the class remains empty,
   // used only as a holder for the above annotations
}
